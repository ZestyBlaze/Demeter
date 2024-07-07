package dev.teamcitrus.demeter.util;

import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.QualityDataComponent;
import dev.teamcitrus.demeter.datagen.provider.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.quality.Quality;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import net.minecraft.world.item.ItemStack;

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

    public static Quality getQuality(ItemStack stack) {
        if (!stack.has(ComponentRegistry.QUALITY)) return Quality.NONE;
        return stack.get(ComponentRegistry.QUALITY).quality();
    }

    public static void writeQualityToTag(ItemStack stack, Quality quality) {
        stack.set(ComponentRegistry.QUALITY.get(), new QualityDataComponent(quality));
    }
}
