package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.util.QualityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.IShearable;
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
    private List<ItemStack> betterFarms$onSheared(List<ItemStack> original, Player player, ItemStack item, Level level, BlockPos pos) {
        List<ItemStack> newList = new ArrayList<>();
        original.forEach(stack -> {
            QualityUtil.randomiseQuality(stack);
            newList.add(stack);
        });
        return newList;
    }
}
