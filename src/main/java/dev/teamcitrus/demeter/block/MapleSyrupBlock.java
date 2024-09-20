package dev.teamcitrus.demeter.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MapleSyrupBlock extends HalfTransparentBlock {
    public MapleSyrupBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext eContext) {
            if (eContext.getEntity() instanceof ExperienceOrb || eContext.getEntity() == null) {
                return Shapes.empty();
            }
        }
        return Shapes.block();
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        if ((state.getBlock() == this && other.getBlock() == Blocks.SLIME_BLOCK) ||
                state.getBlock() == this && other.getBlock() == Blocks.HONEY_BLOCK) {
            return false;
        } else {
            return state.isStickyBlock() || other.isStickyBlock();
        }
    }
}
