package dev.teamcitrus.demeter.block.experimental.tile;

import dev.teamcitrus.demeter.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CounterTileEntity extends BlockEntity {
    public CounterTileEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.COUNTER.get(), pos, blockState);
    }

    public void activate() {

    }
}
