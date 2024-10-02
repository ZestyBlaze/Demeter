package dev.teamcitrus.demeter.util;

import dev.teamcitrus.demeter.attachment.CropAttachment;
import dev.teamcitrus.demeter.data.maps.DemeterDataMaps;
import dev.teamcitrus.demeter.datamaps.CropData;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("deprecation")
public class CropUtil {
    public static CropAttachment getCropData(ChunkAccess chunkAccess) {
        return chunkAccess.getData(AttachmentRegistry.CROP);
    }

    public static Map<BlockPos, Integer> getCropsInChunk(ChunkAccess chunkAccess) {
        return chunkAccess.getData(AttachmentRegistry.CROP).getLocations();
    }

    @Nullable
    public static CropData getCropData(CropBlock block) {
        return block.builtInRegistryHolder().getData(DemeterDataMaps.CROP_DATA);
    }
}
