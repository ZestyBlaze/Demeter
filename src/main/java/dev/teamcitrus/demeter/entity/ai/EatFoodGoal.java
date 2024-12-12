package dev.teamcitrus.demeter.entity.ai;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.trough.TroughBlock;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
    TODO: Maybe switch to on {@link dev.teamcitrus.citruslib.event.NewDayEvent}
 **/
public class EatFoodGoal extends Goal {
    protected final PathfinderMob mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected final double speedModifier;
    protected int interval;
    protected boolean forceTrigger;
    private final boolean checkNoActionTime;

    public final TroughBlock.FoodType food;

    public EatFoodGoal(PathfinderMob mob, TroughBlock.FoodType food, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.interval = 9999;
        this.checkNoActionTime = true;
        this.food = food;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasControllingPassenger()) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if (this.checkNoActionTime && this.mob.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }

            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone() && !this.mob.hasControllingPassenger();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Override
    public void tick() {
        mob.getLookControl().setLookAt(this.wantedX + 0.5, this.wantedY + 1, this.wantedZ + 0.5,
                10, mob.getMaxHeadXRot());
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        BlockPos pos = new BlockPos((int) this.wantedX, (int) this.wantedY, (int) this.wantedZ);
        Demeter.LOGGER.error(mob.level().getBlockState(pos));
        if (mob.blockPosition().closerThan(new BlockPos((int) this.wantedX, (int) this.wantedY, (int) this.wantedZ), 1.5)) {
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1200));
        }
    }


    @Nullable
    protected Vec3 getPosition() {
        List<BlockPos> validPositions = allValidBlocksInArea(16, 16);
        if (!validPositions.isEmpty()) {
            return validPositions.get(mob.level().random.nextInt(validPositions.size())).getBottomCenter();
        }
        return null;
    }

    protected List<BlockPos> allValidBlocksInArea(int verticalRange, int horizontalSearchRange) {
        List<BlockPos> validPos = new ArrayList<>();
        BlockPos blockpos = this.mob.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int k = 0; k >= -verticalRange; k--) {
            for (int l = 0; l < horizontalSearchRange; ++l) {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutable.setWithOffset(blockpos, i1, k, j1);
                        if (this.isValidTarget(this.mob.level(), blockpos$mutable)) {
                            validPos.add(blockpos$mutable);
                        }
                    }
                }
            }
        }
        return validPos;
    }

    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos).is(BlockRegistry.TROUGH.get()) && world.getBlockState(pos).getValue(TroughBlock.FOOD_LEVEL) >= 1
                && world.getBlockState(pos).getValue(TroughBlock.FOOD_TYPE) == food;
    }
}
