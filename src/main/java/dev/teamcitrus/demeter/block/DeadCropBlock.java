package dev.teamcitrus.demeter.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class DeadCropBlock extends BushBlock {
    public DeadCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(DeadCropBlock::new);
    }
}
