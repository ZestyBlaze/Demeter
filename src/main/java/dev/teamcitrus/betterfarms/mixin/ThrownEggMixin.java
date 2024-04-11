package dev.teamcitrus.betterfarms.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.ThrownEgg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ThrownEgg.class)
public class ThrownEggMixin {
    @Redirect(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 0
            )
    )
    private int betterFarms$noChickenSpawnFromEgg(RandomSource instance, int i) {
        return 1;
    }
}
