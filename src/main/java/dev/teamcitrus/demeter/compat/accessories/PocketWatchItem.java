package dev.teamcitrus.demeter.compat.accessories;

import dev.teamcitrus.demeter.config.DemeterConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PocketWatchItem extends DemeterAccessoryItem {
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!DemeterConfig.requireClockItemForTime.get()) tooltipComponents.add(Component.translatable("item.demeter.pocket_watch.error").withStyle(ChatFormatting.RED));
    }
}
