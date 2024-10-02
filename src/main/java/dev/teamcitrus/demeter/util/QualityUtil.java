package dev.teamcitrus.demeter.util;

import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.component.QualityLevelComponent;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.gen.provider.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class QualityUtil {
    private static final Random random = new Random();

    public static ItemStack randomiseQuality(ItemStack stack) {
        if (!stack.is(DemeterItemTagsProvider.QUALITY_PRODUCTS)) return stack;
        int value = random.nextInt(100);
        if (value <= DemeterConfig.netheriteQualityChance.get()) {
            writeQualityToTag(stack, QualityLevel.NETHERITE);
        }
        if (value <= DemeterConfig.ironQualityChance.get() && value > DemeterConfig.netheriteQualityChance.get()) {
            writeQualityToTag(stack, QualityLevel.IRON);
        }
        if (value <= DemeterConfig.copperQualityChance.get() && value > DemeterConfig.ironQualityChance.get()) {
            writeQualityToTag(stack, QualityLevel.COPPER);
        }
        return stack;
    }

    public static QualityLevel getQuality(ItemStack stack) {
        return stack.get(ComponentRegistry.QUALITY_LEVEL).level();
    }

    public static void writeQualityToTag(ItemStack stack, QualityLevel quality) {
        stack.set(ComponentRegistry.QUALITY_LEVEL.get(), new QualityLevelComponent(quality));
    }
}
