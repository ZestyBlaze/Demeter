package dev.teamcitrus.demeter.event;

import com.google.common.collect.Lists;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import dev.teamcitrus.citruslib.reload.DynamicHolder;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.CropAttachment;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.crops.CropInfo;
import dev.teamcitrus.demeter.data.crops.ICrop;
import dev.teamcitrus.demeter.mixin.CropBlockInvoker;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.registry.PoiTypeRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import dev.teamcitrus.demeter.util.CropUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.animal.Animal;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Demeter.MODID)
public class LevelEvents {
    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        ServerLevel level = event.getLevel();

        level.getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.getStats(animal).isBound()).forEach(animal -> {
            animal.getData(AttachmentRegistry.ANIMAL).onNewDay(animal);
        });
        level.getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.getStats(animal).isBound() && (AnimalUtil.getStats(animal).get().milking().isPresent())).forEach(animal -> {
            animal.getData(AttachmentRegistry.MILK).setHasBeenMilked(false);
        });

        ServerChunkCache chunkSource = level.getChunkSource();
        List<ServerChunkCache.ChunkAndHolder> list = Lists.newArrayListWithCapacity(chunkSource.chunkMap.size());

        for (ChunkHolder chunkholder : chunkSource.chunkMap.getChunks()) {
            LevelChunk levelchunk = chunkholder.getTickingChunk();
            if (levelchunk != null) {
                list.add(new ServerChunkCache.ChunkAndHolder(levelchunk, chunkholder));
            }
        }

        for (ServerChunkCache.ChunkAndHolder serverchunkcache$chunkandholder : list) {
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
                    DynamicHolder<ICrop> cropInfo = CropUtil.getCropInfo(block);
                    if (cropInfo.isBound()) {
                        ICrop info = cropInfo.get();
                        int days = info.daysToGrow();

                        CropAttachment data = CropUtil.getCropData(levelchunk);
                        data.incrementDays(pos);

                        if(originalDays < days) {
                            int stage = Mth.floor(block.getMaxAge() * ((float) data.getDays(pos) / days));
                            IntegerProperty property = ((CropBlockInvoker)block).invokeGetAgeProperty();
                            level.setBlockAndUpdate(pos, state.setValue(property, stage));
                        }

                        if (DemeterConfig.cropsWilt.get()) {
                            if (data.getDays(pos) == days + DemeterConfig.daysToWilt.get()) {
                                level.destroyBlock(pos, false);
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
}
