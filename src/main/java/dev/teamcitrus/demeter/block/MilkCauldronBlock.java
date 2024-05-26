package dev.teamcitrus.demeter.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class MilkCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<MilkCauldronBlock> CODEC = simpleCodec(MilkCauldronBlock::new);

    public MilkCauldronBlock(Properties pProperties) {
        super(pProperties, CauldronInteraction.EMPTY);
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return 3;
    }
}
