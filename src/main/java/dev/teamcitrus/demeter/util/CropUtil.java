package dev.teamcitrus.demeter.util;

import dev.teamcitrus.demeter.attachment.chunk.CropsInChunkAttachment;
import dev.teamcitrus.demeter.datamaps.CropData;
import dev.teamcitrus.demeter.duck.CropBlockData;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.registry.DataMapRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("deprecation")
public class CropUtil {
    public static CropsInChunkAttachment getCropData(ChunkAccess chunkAccess) {
        return chunkAccess.getData(AttachmentRegistry.CROPS_IN_CHUNK);
    }

    public static Map<BlockPos, CropBlockData> getCropsInChunk(ChunkAccess chunkAccess) {
        return chunkAccess.getData(AttachmentRegistry.CROPS_IN_CHUNK).locations();
    }

    @Nullable
    public static CropData getCropData(CropBlock block) {
        return block.builtInRegistryHolder().getData(DataMapRegistry.CROP_DATA);
    }
}
