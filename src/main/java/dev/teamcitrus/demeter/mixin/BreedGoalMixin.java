package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.teamcitrus.demeter.duck.AnimalSexes;
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
    private void demeter$handlePregnancy(Animal instance, ServerLevel level, Animal mate, Operation<Void> original) {
        if (AnimalUtil.getStats(instance) != null) {
            if (AnimalUtil.getStats(instance).daysPregnant() == 0) {
                original.call(instance, level, mate);
            }

            if (!AnimalUtil.getAnimalData(instance).isPregnant()) {
                if (AnimalUtil.getSex(instance).equals(AnimalSexes.FEMALE)) {
                    instance.getData(AttachmentRegistry.ANIMAL).setPregnant(instance, true, mate);
                }
                instance.resetLove();
            }
        } else {
            original.call(instance, level, mate);
        }
    }
}
