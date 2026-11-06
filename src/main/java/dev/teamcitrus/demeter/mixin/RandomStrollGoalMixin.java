package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Chicken;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RandomStrollGoal.class)
public class RandomStrollGoalMixin {
    @Shadow
    @Final
    protected PathfinderMob mob;

    @ModifyReturnValue(
            method = "canUse",
            at = @At(
                    "RETURN"
            )
    )
    private boolean demeter$canUse(boolean original) {
        if (mob instanceof Chicken chicken) {
            if (chicken.getData(AttachmentRegistry.CHICKEN).isNesting()) {
                return false;
            }
        }
        return original;
    }
}
