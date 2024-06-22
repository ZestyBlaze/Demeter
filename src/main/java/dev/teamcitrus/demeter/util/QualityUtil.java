package dev.teamcitrus.demeter.util;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.datagen.provider.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.quality.Quality;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class QualityUtil {
    private static final Random random = new Random();

    public static ItemStack randomiseQuality(ItemStack stack) {
        if (!stack.is(DemeterItemTagsProvider.QUALITY_PRODUCTS)) return stack;
        int value = random.nextInt(100);
        if (value <= DemeterConfig.netheriteQualityChance.get()) {
            writeQualityToTag(stack, Quality.NETHERITE);
        }
        if (value <= DemeterConfig.diamondQualityChance.get() && value > DemeterConfig.netheriteQualityChance.get()) {
            writeQualityToTag(stack, Quality.DIAMOND);
        }
        if (value <= DemeterConfig.goldQualityChance.get() && value > DemeterConfig.diamondQualityChance.get()) {
            writeQualityToTag(stack, Quality.GOLD);
        }
        if (value <= DemeterConfig.ironQualityChance.get() && value > DemeterConfig.goldQualityChance.get()) {
            writeQualityToTag(stack, Quality.IRON);
        }
        if (value <= DemeterConfig.copperQualityChance.get() && value > DemeterConfig.ironQualityChance.get()) {
            writeQualityToTag(stack, Quality.COPPER);
        }
        return stack;
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
        return stack.getOrCreateTagElement(Demeter.MODID).getInt("quality");
    }

    public static void writeQualityToTag(ItemStack stack, Quality quality) {
        stack.getOrCreateTagElement(Demeter.MODID).putInt("quality", quality.ordinal() + 1);
    }
}
