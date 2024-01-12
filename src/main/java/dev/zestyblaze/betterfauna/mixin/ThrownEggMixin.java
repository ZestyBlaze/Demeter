package dev.zestyblaze.betterfauna.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.ThrownEgg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ThrownEgg.class)
public class ThrownEggMixin {
    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I", ordinal = 0))
    private int betterFauna$noChickenSpawn(RandomSource instance, int i) {
        return 1;
    }
}
