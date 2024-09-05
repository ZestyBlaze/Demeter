package dev.teamcitrus.demeter.event;

import com.google.common.collect.Lists;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.registry.PoiTypeRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

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
            //Demeter.LOGGER.error(1);
            if (level.shouldTickBlocksAt(chunkpos.toLong())) {
                //Demeter.LOGGER.error(2);
                Stream<PoiRecord> record = level.getPoiManager().getInChunk(
                        holder -> holder.is(PoiTypeRegistry.FARMLAND),
                        chunkpos,
                        PoiManager.Occupancy.ANY
                );
                record.forEach(poiRecord -> {
                    Demeter.LOGGER.error("5");
                    Demeter.LOGGER.error(poiRecord.getPoiType());
                    Demeter.LOGGER.error(poiRecord.getPos());
                });
            }
        }
    }
}
