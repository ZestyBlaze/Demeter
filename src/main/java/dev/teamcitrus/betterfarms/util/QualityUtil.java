package dev.teamcitrus.betterfarms.util;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.config.BetterFarmsConfig;
import dev.teamcitrus.betterfarms.datagen.provider.BFItemTagProvider;
import dev.teamcitrus.betterfarms.quality.Quality;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class QualityUtil {
    private static final Random random = new Random();

    public static void randomiseQuality(ItemStack stack) {
        if (!stack.is(BFItemTagProvider.QUALITY_PRODUCTS)) return;
        int value = random.nextInt(100);
        if (value <= BetterFarmsConfig.netheriteQualityChance.get()) {
            writeQualityToTag(stack, Quality.NETHERITE);
        }
        if (value <= BetterFarmsConfig.diamondQualityChance.get() && value > BetterFarmsConfig.netheriteQualityChance.get()) {
            writeQualityToTag(stack, Quality.DIAMOND);
        }
        if (value <= BetterFarmsConfig.goldQualityChance.get() && value > BetterFarmsConfig.diamondQualityChance.get()) {
            writeQualityToTag(stack, Quality.GOLD);
        }
        if (value <= BetterFarmsConfig.ironQualityChance.get() && value > BetterFarmsConfig.goldQualityChance.get()) {
            writeQualityToTag(stack, Quality.IRON);
        }
        if (value <= BetterFarmsConfig.copperQualityChance.get() && value > BetterFarmsConfig.ironQualityChance.get()) {
            writeQualityToTag(stack, Quality.COPPER);
        }
    }

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
