package dev.teamcitrus.demeter.util;

import dev.teamcitrus.citruslib.reload.DynamicHolder;
import dev.teamcitrus.demeter.attachment.CropAttachment;
import dev.teamcitrus.demeter.data.crops.CropRegistry;
import dev.teamcitrus.demeter.data.crops.ICrop;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.HashMap;
import java.util.Map;

public class CropUtil {
    public static CropAttachment getCropData(ChunkAccess chunkAccess) {
        return chunkAccess.getData(AttachmentRegistry.CROP);
    }

    public static Map<BlockPos, Integer> getCropsInChunk(ChunkAccess chunkAccess) {
        return chunkAccess.getData(AttachmentRegistry.CROP).getLocations();
    }

    public static DynamicHolder<ICrop> getCropInfo(CropBlock block) {
        return CropRegistry.INSTANCE.holder(BuiltInRegistries.BLOCK.getKey(block));
    }

    public static Map<IntegerProperty, Integer> getAgeData(BlockState state) {
        Map<IntegerProperty, Integer> map = new HashMap<>();
        if (state.getBlock() instanceof CropBlock ) {
            if (state.hasProperty(BlockStateProperties.AGE_1)) {
                map.put(BlockStateProperties.AGE_1, BlockStateProperties.MAX_AGE_1);
            }
            if (state.hasProperty(BlockStateProperties.AGE_2)) {
                map.put(BlockStateProperties.AGE_2, BlockStateProperties.MAX_AGE_2);
            }
            if (state.hasProperty(BlockStateProperties.AGE_3)) {
                map.put(BlockStateProperties.AGE_3, BlockStateProperties.MAX_AGE_3);
            }
            if (state.hasProperty(BlockStateProperties.AGE_4)) {
                map.put(BlockStateProperties.AGE_4, BlockStateProperties.MAX_AGE_4);
            }
            if (state.hasProperty(BlockStateProperties.AGE_5)) {
                map.put(BlockStateProperties.AGE_5, BlockStateProperties.MAX_AGE_5);
            }
            if (state.hasProperty(BlockStateProperties.AGE_7)) {
                map.put(BlockStateProperties.AGE_7, BlockStateProperties.MAX_AGE_7);
            }
            if (state.hasProperty(BlockStateProperties.AGE_15)) {
                map.put(BlockStateProperties.AGE_15, BlockStateProperties.MAX_AGE_15);
            }
            if (state.hasProperty(BlockStateProperties.AGE_25)) {
                map.put(BlockStateProperties.AGE_25, BlockStateProperties.MAX_AGE_25);
            }
        }
        return map;
    }
}
