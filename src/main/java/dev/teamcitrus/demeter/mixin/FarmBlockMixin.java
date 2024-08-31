package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.FarmBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
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
        return false;
    }
}
