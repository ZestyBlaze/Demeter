package dev.teamcitrus.demeter.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DeadCropBlock extends Block {
    public DeadCropBlock() {
        super(Properties.of().sound(SoundType.CROP).instabreak().noCollission());
    }
}
