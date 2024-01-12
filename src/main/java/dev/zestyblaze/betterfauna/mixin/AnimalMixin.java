package dev.zestyblaze.betterfauna.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Animal.class)
public class AnimalMixin {
    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z", ordinal = 0)
    )
    private boolean betterFauna$checkMateGender(boolean original, Animal otherEntity) {
        return original && otherEntity.getComponent(ComponentRegistry.ANIMAL).getGender() != ((Animal)(Object)this).getComponent(ComponentRegistry.ANIMAL).getGender();
    }
}
