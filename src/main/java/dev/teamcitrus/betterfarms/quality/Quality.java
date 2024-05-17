package dev.teamcitrus.betterfarms.quality;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

public enum Quality {
    COPPER("copper", Rarity.create("copper", color -> color.withColor(0xFF8C00))),
    IRON("iron", Rarity.create("iron", color -> color.withColor(ChatFormatting.GRAY))),
    GOLD("gold", Rarity.create("gold", color -> color.withColor(ChatFormatting.GOLD))),
    DIAMOND("diamond", Rarity.create("diamond", color -> color.withColor(ChatFormatting.AQUA))),
    NETHERITE("netherite", Rarity.create("netherite", color -> color.withColor(ChatFormatting.DARK_GRAY)));

    private final String name;
    private final Rarity rarity;

    Quality(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Component getQualityTooltip() {
        return Component.translatable("item.betterfarms.quality_tooltip." + getName()).withStyle(getRarity().getStyleModifier());
    }
}
