package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import dev.teamcitrus.betterfarms.data.BFStatsListener;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
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
    private void betterFauna$handleMultipleOffspring(Animal instance, ServerLevel serverLevel, Animal animal, Operation<Void> original) {
        if (BFStatsListener.newMap.containsKey(instance.getType())) {
            if (instance.getData(AttachmentRegistry.ANIMAL).getGender().equals(AnimalAttachment.Gender.FEMALE)) {
                BFStatsListener.getManager(instance).setPregnant(true, animal);
            } else {
                BFStatsListener.getManager(animal).setPregnant(true, animal);
            }
            instance.resetLove();
            animal.resetLove();
        } else {
            original.call(instance, serverLevel, animal);
        }
    }
}
