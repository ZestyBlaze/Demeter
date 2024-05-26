package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.citruslib.block.WoodSet;

public class WoodSetRegistry {
    public static final WoodSet MAPLE = WoodSet.registerSetBlocks(BlockRegistry.BLOCKS, "maple");

    public static void init() {
        WoodSet.registerSetItems(ItemRegistry.ITEMS, MAPLE);
    }
}
