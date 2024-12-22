package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import dev.teamcitrus.demeter.util.QualityUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IItemStackExtension.class)
public interface IItemStackExtensionMixin {
    @Shadow ItemStack self();

    @ModifyReturnValue(method = "getFoodProperties", at = @At("RETURN"))
    private FoodProperties demeter$getFoodProperties(FoodProperties original) {
        if (self().has(DataComponents.FOOD) && self().has(ComponentRegistry.QUALITY_LEVEL)) {
            return QualityUtil.getFoodProperties(self().get(ComponentRegistry.QUALITY_LEVEL).level(), self().get(DataComponents.FOOD));
        }
        return original;
    }
}
