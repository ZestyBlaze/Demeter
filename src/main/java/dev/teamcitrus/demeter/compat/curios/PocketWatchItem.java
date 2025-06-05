package dev.teamcitrus.demeter.compat.curios;

import dev.teamcitrus.demeter.config.DemeterConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class PocketWatchItem extends DemeterCuriosItem {
    public PocketWatchItem() {
        super("pocket_watch");
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        if (!DemeterConfig.requireClockItemForTime.get()) tooltipAdder.accept(Component.translatable("item.demeter.pocket_watch.error").withStyle(ChatFormatting.RED));
    }
}
