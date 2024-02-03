package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Cow.class, Goat.class})
public class CowGoatMixin {
    @ModifyExpressionValue(
            method = "mobInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean betterFarms$stopVanillaMilking(boolean original) {
        return false;
    }
}
