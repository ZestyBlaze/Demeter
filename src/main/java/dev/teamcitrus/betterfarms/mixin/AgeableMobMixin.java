package dev.teamcitrus.betterfarms.mixin;

import dev.teamcitrus.betterfarms.data.BFStatsManager;
import net.minecraft.world.entity.AgeableMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AgeableMob.class)
public class AgeableMobMixin {
    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/AgeableMob;setAge(I)V"
            )
    )
    private void betterFarms$aiStep(AgeableMob instance, int pAge) {
        if (BFStatsManager.getStats(instance).daysToGrowUp() == 0) instance.setAge(++pAge);
    }
}
