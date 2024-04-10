package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.betterfarms.data.AnimalStats;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {
    @Shadow @Final protected PathfinderMob mob;

    @ModifyReturnValue(
            method = "shouldFollow",
            at = @At("RETURN")
    )
    private boolean betterFarms$shouldFollow(boolean original, LivingEntity entity) {
        if (!BFStatsManager.newMap.containsKey(mob.getType())) return original;
        AnimalStats stats = BFStatsManager.getStats(mob);
        if (stats.breedingItems().isEmpty()) return original;
        for (Ingredient ingredient : stats.breedingItems().get()) {
            if (ingredient.test(entity.getMainHandItem()) || ingredient.test(entity.getOffhandItem())) {
                return true;
            }
        }
        return false;
    }
}
