package dev.zestyblaze.betterfauna.entity.ai;

import dev.zestyblaze.betterfauna.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class DropsProductGoal extends MoveToBlockGoal {
    public DropsProductGoal(PathfinderMob mob) {
        super(mob, 1.0d, 16);
    }

    @Override
    public boolean canUse() {
        return mob.getRandom().nextInt(150) == 0 && super.canUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(BlockTags.DIRT);
    }

    @Override
    public void tick() {
        super.tick();

        mob.getLookControl().setLookAt(blockPos.getX() + 0.5d, blockPos.getY() + 1, blockPos.getZ() + 0.5d, 10.0f, mob.getMaxHeadXRot());

        if (!mob.level().isClientSide() && mob.blockPosition().closerThan(blockPos,2.5)) {
           if (mob instanceof Pig) {
               mob.level().addFreshEntity(new ItemEntity(mob.level(), blockPos.getX() + 0.5d, blockPos.getY() + 1, blockPos.getZ() + 0.5d, new ItemStack(ItemRegistry.TRUFFLE)));
               tryTicks = 9999;
           }
        }
    }
}
