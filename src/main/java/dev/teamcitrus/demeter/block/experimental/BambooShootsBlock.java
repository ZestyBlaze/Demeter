package dev.teamcitrus.demeter.block.experimental;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class BambooShootsBlock extends BushBlock {
    public BambooShootsBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<BushBlock> codec() {
        return simpleCodec(BambooShootsBlock::new);
    }
}
