package dev.teamcitrus.demeter.registry;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class BlockFamilyRegistry {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily MAPLE = familyBuilder(BlockRegistry.MAPLE_PLANKS.get())
            .button(BlockRegistry.MAPLE_BUTTON.get())
            .fence(BlockRegistry.MAPLE_FENCE.get())
            .fenceGate(BlockRegistry.MAPLE_FENCE_GATE.get())
            .pressurePlate(BlockRegistry.MAPLE_PRESSURE_PLATE.get())
            .sign(BlockRegistry.MAPLE_SIGN.get(), BlockRegistry.MAPLE_WALL_SIGN.get())
            .slab(BlockRegistry.MAPLE_SLAB.get())
            .stairs(BlockRegistry.MAPLE_STAIRS.get())
            .door(BlockRegistry.MAPLE_DOOR.get())
            .recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
