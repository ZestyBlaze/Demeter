package dev.teamcitrus.demeter.block.experimental;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class BambooShootsBlock extends BushBlock {
    public static final MapCodec<BambooShootsBlock> CODEC = simpleCodec(BambooShootsBlock::new);

    public BambooShootsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
