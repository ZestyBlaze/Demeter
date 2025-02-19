package dev.teamcitrus.demeter.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;

public class SelfFeedGoal extends MoveToBlockGoal {
    protected final Animal mob;

    public SelfFeedGoal(Animal mob, double speedModifier) {
        super(mob, speedModifier, 8);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return this.mob.canFallInLove() && this.mob.getAge() == 0 && super.canUse();
    }

    @Override
    public double acceptedDistance() {
        return 2.0d;
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
