package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
 
@Mixin(Animal.class)
public class AnimalMixin {
    @Unique
    private final Animal demeter$animal = (Animal) (Object) this;

    @ModifyReturnValue(
            method = "canFallInLove",
            at = @At("RETURN")
    )
    private boolean demeter$canFallInLove(boolean original) {
        return original && AnimalUtil.isAnimalHappy(demeter$animal)
                && !AnimalUtil.getAnimalData(demeter$animal).isOnDownPeriod()
                && !AnimalUtil.getAnimalData(demeter$animal).isPregnant();
    }

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z",
                    ordinal = 0
            )
    )
    private boolean demeter$checkMateSex(boolean original, Animal otherEntity) {
        return original && !AnimalUtil.getAnimalData(demeter$animal).isPregnant()
                //&& !AnimalUtil.getAnimalData(demeter$animal).isOnDownPeriod()
                && AnimalUtil.areOppositeSexes(demeter$animal, otherEntity);
    }
}
