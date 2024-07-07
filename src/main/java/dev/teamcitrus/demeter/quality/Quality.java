package dev.teamcitrus.demeter.quality;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Rarity;

public enum Quality {
    COPPER("copper", ChatFormatting.getByCode(0xFF8C00)),
    IRON("iron", ChatFormatting.GRAY),
    GOLD("gold", ChatFormatting.GOLD),
    DIAMOND("diamond", ChatFormatting.AQUA),
    NETHERITE("netherite", ChatFormatting.DARK_GRAY);

    private final String name;
    private final ChatFormatting formatting;

    Quality(String name, ChatFormatting rarity) {
        this.name = name;
        this.formatting = rarity;
    }

    public String getName() {
        return name;
    }

    public ChatFormatting getFormatting() {
        return formatting;
    }

    public Component getQualityTooltip() {
        return Component.translatable("item.demeter.quality_tooltip." + getName()).withStyle(getFormatting());
    }
}
