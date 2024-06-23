package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.citruslib.reload.DynamicHolder;
import dev.teamcitrus.demeter.data.AnimalStats;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {
    @Shadow
    @Final
    protected PathfinderMob mob;

    @ModifyReturnValue(
            method = "shouldFollow",
            at = @At("RETURN")
    )
    private boolean betterFarms$shouldFollow(boolean original, LivingEntity entity) {
        if (!(mob instanceof Animal animal)) return original;
        if (!AnimalUtil.getStats(animal).isBound()) return original;
        DynamicHolder<AnimalStats> stats = AnimalUtil.getStats(animal);
        if (stats.get().breedingItems().isEmpty()) return original;
        for (Ingredient ingredient : stats.get().breedingItems().get()) {
            if (ingredient.test(entity.getMainHandItem()) || ingredient.test(entity.getOffhandItem())) {
                return true;
            }
        }
        return false;
    }
}
