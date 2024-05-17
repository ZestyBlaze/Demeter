package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.betterfarms.util.QualityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.IShearable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(IShearable.class)
public interface IShearableMixin {
    @ModifyReturnValue(
            method = "onSheared",
            at = @At("RETURN")
    )
    private List<ItemStack> betterFarms$onSheared(List<ItemStack> original, @Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune) {
        List<ItemStack> newList = new ArrayList<>();
        original.forEach(stack -> {
            QualityUtil.randomiseQuality(stack);
            newList.add(stack);
        });
        return newList;
    }
}
