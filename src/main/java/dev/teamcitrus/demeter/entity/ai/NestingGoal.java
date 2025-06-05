package dev.teamcitrus.demeter.entity.ai;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.nest.NestBlockEntity;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.EnumSet;

public class NestingGoal extends Goal {
    private final Animal animal;
    private BlockPos targetPos;

    public NestingGoal(Animal animal) {
        this.animal = animal;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET, Flag.JUMP));
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean canUse() {
        BlockPos pos = this.getPosition();

        if (pos == BlockPos.ZERO) {
            return false;
        } else {
            targetPos = pos;

            return animal.getData(AttachmentRegistry.NESTING).isNesting() && !atDestination();
        }
    }

    @Override
    public void start() {
        Demeter.LOGGER.warn("Goal start");
        animal.getNavigation()
                .moveTo(getPosition().getX(),
                        getPosition().getY() + 0.5f, getPosition().getZ(), 1);
    }

    @Override
    public boolean canContinueToUse() {
        return !animal.getNavigation().isDone() && !animal.hasControllingPassenger();
    }

    @Override
    public void stop() {
        Demeter.LOGGER.warn("end");
        if (atDestination() && animal.level().getBlockEntity(getPosition()) instanceof NestBlockEntity nestBlockEntity) {
            Demeter.LOGGER.warn("end success");
            nestBlockEntity.insertItem(new ItemStack(Items.EGG));
            animal.getData(AttachmentRegistry.NESTING).setNesting(false);
            animal.getData(AttachmentRegistry.NESTING).setTargetPos(BlockPos.ZERO);
            animal.getNavigation().stop();
        }
    }

    private BlockPos getPosition() {
        return animal.getData(AttachmentRegistry.NESTING).getTargetPos();
    }

    private boolean atDestination() {
        return animal.blockPosition().closerThan(targetPos, 1);
    }

    @Override
    public void tick() {
        Demeter.LOGGER.warn("tick");
        if (atDestination()) {
            Demeter.LOGGER.warn("tick pass");
            animal.setPos(getPosition().getX() + 0.5, getPosition().getY() + 0.2, getPosition().getZ() + 0.5);
        }
    }
}
