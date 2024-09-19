package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.datagen.provider.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import dev.teamcitrus.demeter.util.QualityUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Demeter.MODID)
public class ItemEvents {
    @SubscribeEvent
        if (event.getItemStack().getItem() == Items.MILK_BUCKET) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void itemFished(ItemFishedEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            NonNullList<ItemStack> items = event.getDrops();
            items.forEach(QualityUtil::randomiseQuality);
        }
    }

    @SubscribeEvent
    public static void changeTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().is(DemeterItemTagsProvider.QUALITY_PRODUCTS)) {
            ItemStack itemStack = event.getItemStack();
            if (QualityUtil.getQuality(itemStack).equals(Quality.NONE)) return;
            Quality quality = QualityUtil.getQuality(itemStack);
            event.getToolTip().add(Component.translatable("item.demeter.quality_tooltip", quality.getQualityTooltip()).withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
