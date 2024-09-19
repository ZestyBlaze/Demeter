package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.config.DemeterConfig;
import net.minecraft.world.level.block.FarmBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @ModifyExpressionValue(
            method = "randomTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
            )
    )
    private Comparable demeter$randomTick(Comparable original) {
        return 0;
    }

    @ModifyReturnValue(
            method = "shouldMaintainFarmland",
            at = @At("RETURN")
    )
    private static boolean demeter$shouldMaintainFarmland(boolean original) {
        return true;
    }

    @ModifyReturnValue(
            method = "isNearWater",
            at = @At("RETURN")
    )
    private static boolean demeter$isNearWater(boolean original) {
        if (DemeterConfig.waterIrrigationEnabled.get()) return original;
        return false;
    }
}
