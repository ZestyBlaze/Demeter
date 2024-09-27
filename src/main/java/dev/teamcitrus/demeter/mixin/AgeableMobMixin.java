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
    private void demeter$aiStep(AgeableMob instance, int pAge) {
        if (!(instance instanceof Animal animal) ||
                AnimalUtil.getStats((Animal) instance) == null ||
                AnimalUtil.getStats(animal).daysToGrowUp() == 0) instance.setAge(++pAge);
    }
}
