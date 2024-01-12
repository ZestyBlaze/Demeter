package dev.zestyblaze.betterfauna.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.zestyblaze.betterfauna.component.animal.AnimalComponent;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
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
        if (ComponentRegistry.PREGNANCY.maybeGet(instance).isPresent()) {
            if (instance.getComponent(ComponentRegistry.ANIMAL).getGender().equals(AnimalComponent.Gender.FEMALE)) {
                instance.getComponent(ComponentRegistry.PREGNANCY).setPregnant(true, animal);
            } else {
                animal.getComponent(ComponentRegistry.PREGNANCY).setPregnant(true, animal);
            }
            instance.resetLove();
            animal.resetLove();
        } else {
            original.call(instance, serverLevel, animal);
        }
    }
}
