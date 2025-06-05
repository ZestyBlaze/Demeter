package dev.teamcitrus.demeter.event;

import com.google.common.collect.Lists;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.CropAttachment;
import dev.teamcitrus.demeter.block.nest.NestBlockEntity;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.datamaps.CropData;
import dev.teamcitrus.demeter.event.internal.NewYearEvent;
import dev.teamcitrus.demeter.mixin.CropBlockInvoker;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.PoiTypeRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import dev.teamcitrus.demeter.util.ChunkAndHolder;
import dev.teamcitrus.demeter.util.CropUtil;
import dev.teamcitrus.demeter.util.TimeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Demeter.MODID)
@SuppressWarnings("deprecation")
public class LevelEvents {
    public static final Map<BlockPos, Chicken> claimedNests = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        ServerLevel level = event.getLevel();

        claimedNests.entrySet().removeIf(entry ->
                !entry.getValue().isAlive() || entry.getValue().isRemoved());

        level.getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.getStats(animal) != null).forEach(animal -> {
            animal.getData(AttachmentRegistry.ANIMAL).onNewDay(animal);
            if (animal instanceof Sheep sheep) {
                sheep.getData(AttachmentRegistry.SHEEP).onNewDay(sheep);
            }
        });

        level.getEntities(EntityTypeTest.forClass(Chicken.class), animal -> true).forEach(chicken -> {
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for (int k = 0; k >= -24; k--) {
                for (int l = 0; l < 24; ++l) {
                    for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                        for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                            blockpos$mutable.setWithOffset(chicken.blockPosition(), i1, k, j1);
                            if (level.getBlockState(blockpos$mutable).is(BlockRegistry.NEST.get()) &&
                                    level.getBlockEntity(blockpos$mutable) instanceof NestBlockEntity nest)
                            {
                                BlockPos immutablePos = blockpos$mutable.immutable();
                                if (nest.getCapability(null).getStackInSlot(0).isEmpty()) {
                                    if (claimNest(immutablePos, chicken)) {
                                        chicken.getData(AttachmentRegistry.NESTING).setNesting(true);
                                        chicken.getData(AttachmentRegistry.NESTING).setTargetPos(immutablePos);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        ServerChunkCache chunkSource = level.getChunkSource();
        List<ChunkAndHolder> list = Lists.newArrayListWithCapacity(chunkSource.chunkMap.size());

        for (ChunkHolder chunkholder : chunkSource.chunkMap.getChunks()) {
            LevelChunk levelchunk = chunkholder.getTickingChunk();
            if (levelchunk != null) {
                list.add(new ChunkAndHolder(levelchunk, chunkholder));
            }
        }

        for (ChunkAndHolder serverchunkcache$chunkandholder : list) {
            LevelChunk levelchunk = serverchunkcache$chunkandholder.chunk();
            ChunkPos chunkpos = levelchunk.getPos();
            if (level.shouldTickBlocksAt(chunkpos.toLong())) {
                Stream<PoiRecord> record = level.getPoiManager().getInChunk(
                        holder -> holder.is(PoiTypeRegistry.FARMLAND),
                        chunkpos,
                        PoiManager.Occupancy.ANY
                );
                List<BlockPos> updatePositions = new ArrayList<>();
                record.forEach(poiRecord -> updatePositions.add(poiRecord.getPos()));
                updatePositions.forEach(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);
                    if (state.hasProperty(BlockStateProperties.MOISTURE) && state.getValue(BlockStateProperties.MOISTURE) != 7) {
                        if (level.random.nextInt(100) < DemeterConfig.morningDirtChance.get()) {
                            FarmBlock.turnToDirt(null, state, level, blockPos);
                        }
                    } else {
                        level.setBlockAndUpdate(blockPos, state.setValue(BlockStateProperties.MOISTURE, 0));
                    }
                });
                updatePositions.clear();

                CropUtil.getCropsInChunk(levelchunk).forEach((pos, originalDays) -> {
                    BlockState state = level.getBlockState(pos);
                    CropBlock block = (CropBlock) state.getBlock();
                    CropData cropData = CropUtil.getCropData(block);
                    if (cropData != null) {
                        int days = cropData.daysToGrow();

                        CropAttachment data = CropUtil.getCropData(levelchunk);
                        data.incrementDays(pos);

                        if(originalDays < days) {
                            int stage = Mth.floor(block.getMaxAge() * ((float) data.getDays(pos) / days));
                            IntegerProperty property = ((CropBlockInvoker)block).invokeGetAgeProperty();
                            level.setBlockAndUpdate(pos, state.setValue(property, stage));
                        }

                        if (DemeterConfig.cropsWilt.get()) {
                            if (data.getDays(pos) == days + DemeterConfig.daysToWilt.get()) {
                                level.setBlockAndUpdate(pos, BlockRegistry.DEAD_CROP.get().defaultBlockState());
                                updatePositions.add(pos);
                            }
                        }
                    }
                });
                updatePositions.forEach(pos -> CropUtil.getCropData(levelchunk).removeLocation(pos));
                updatePositions.clear();
            }
        }
    }

    @SubscribeEvent
    public static void newYearTrigger(NewDayEvent event) {
        if (TimeUtil.getElapsedDays(event.getLevel().getDayTime()) % TimeUtil.YEAR_DAYS == 0) {
            NeoForge.EVENT_BUS.post(new NewYearEvent(event.getLevel()));
        }
    }

    @SubscribeEvent
    public static void newYearEvent(NewYearEvent event) {
        ServerLevel level = event.getLevel();

        level.getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.getStats(animal) != null).forEach(animal -> {
            animal.getData(AttachmentRegistry.ANIMAL).onNewYear(animal);
        });
    }

    public static boolean claimNest(BlockPos pos, Chicken chicken) {
        if (!claimedNests.containsKey(pos)) {
            claimedNests.put(pos, chicken);
            return true;
        }

        return claimedNests.get(pos) == chicken;
    }
}
