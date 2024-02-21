package dev.teamcitrus.betterfarms.api.util;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.api.quality.Quality;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class QualityUtil {
    @Nullable
    public static Quality getQuality(ItemStack stack) {
        return getQualityFromNumber(getQualityNumber(stack));
    }

    @Nullable
    public static Quality getQualityFromNumber(int value) {
        if (value <= 0 || value >= 6) return null;
        return switch (value) {
            case 1 -> Quality.COPPER;
            case 2 -> Quality.IRON;
            case 3 -> Quality.GOLD;
            case 4 -> Quality.DIAMOND;
            case 5 -> Quality.NETHERITE;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    public static int getQualityNumber(ItemStack stack) {
        if (stack.getTag() == null) return 0;
        return stack.getOrCreateTagElement(BetterFarms.MODID).getInt("quality");
    }

    public static void writeQualityToTag(ItemStack stack, Quality quality) {
        stack.getOrCreateTagElement(BetterFarms.MODID).putInt("quality", quality.ordinal() + 1);
    }
}
