package dev.teamcitrus.demeter.mixin;

import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AgeableMob.class)
public class AgeableMobMixin {
    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/AgeableMob;setAge(I)V",
                    ordinal = 0
            )
    )
    private void betterFarms$aiStep(AgeableMob instance, int pAge) {
        // TODO: Entities not in the stats can't grow up
        if (instance instanceof Animal animal &&
                AnimalUtil.getStats(animal).isBound() &&
                AnimalUtil.getStats(animal).get().daysToGrowUp() == 0) instance.setAge(++pAge);
    }
}
