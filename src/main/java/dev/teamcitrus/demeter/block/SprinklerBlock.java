package dev.teamcitrus.demeter.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SprinklerBlock extends Block {
    public SprinklerBlock() {
        super(Properties.ofFullCopy(Blocks.IRON_BARS));
    }
}
