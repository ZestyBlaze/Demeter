package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BreedGoal.class)
public class BreedGoalMixin {
    @WrapOperation(
            method = "breed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;spawnChildFromBreeding(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/Animal;)V")
    )
    private void betterFarms$handlePregnancy(Animal instance, ServerLevel serverLevel, Animal animal, Operation<Void> original) {
        if (AnimalUtil.statsContains(instance)) {
            if (AnimalUtil.getStats(instance).get().daysPregnant() == 0) {
                original.call(instance, serverLevel, animal);
            }

            if (AnimalUtil.getGender(instance).equals(AnimalGenders.FEMALE)) {
                instance.getData(AttachmentRegistry.ANIMAL).setPregnant(instance, true, animal);
            } else {
                animal.getData(AttachmentRegistry.ANIMAL).setPregnant(instance, true, animal);
            }
            instance.resetLove();
            animal.resetLove();
        } else {
            original.call(instance, serverLevel, animal);
        }
    }
}
