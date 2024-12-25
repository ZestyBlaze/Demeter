package dev.teamcitrus.demeter.item;

import dev.teamcitrus.demeter.consumable.ClearRandomNegativeEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;

import java.util.List;

public class MilkBottleItem extends Item {
    public static final Consumable MILK_BOTTLE = Consumable.builder().consumeSeconds(1.6F)
            .animation(ItemUseAnimation.DRINK).sound(SoundEvents.GENERIC_DRINK)
            .hasConsumeParticles(false).onConsume(ClearRandomNegativeEffect.INSTANCE).build();

    public MilkBottleItem() {
        super(new Properties().craftRemainder(Items.GLASS_BOTTLE).component(DataComponents.CONSUMABLE, MILK_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE).stacksTo(16));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.demeter.milk_bottle.desc").withStyle(ChatFormatting.DARK_GRAY));
    }
}
