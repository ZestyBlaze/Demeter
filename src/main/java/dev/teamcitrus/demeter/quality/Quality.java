package dev.teamcitrus.demeter.quality;

import com.mojang.serialization.Codec;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.Locale;

public enum Quality {
    NONE(Style.EMPTY),
    COPPER(Style.EMPTY.withColor(0xFF8C00)),
    IRON(Style.EMPTY.withColor(ChatFormatting.GRAY)),
    GOLD(Style.EMPTY.withColor(ChatFormatting.GOLD)),
    DIAMOND(Style.EMPTY.withColor(ChatFormatting.AQUA)),
    NETHERITE(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY));

    public static final Codec<Quality> CODEC = CitrusCodecs.enumCodec(Quality.class);

    private final Style style;

    Quality(Style style) {
        this.style = style;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Style getStyle() {
        return style;
    }

    public Component getQualityTooltip() {
        return Component.translatable("item.demeter.quality_tooltip." + getName()).withStyle(getStyle());
    }
}
