package dev.teamcitrus.demeter.util;

import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.component.QualityLevelComponent;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.providers.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class QualityUtil {
    private static final Random random = new Random();

    public static void randomiseQuality(ItemStack stack) {
        if (!stack.is(DemeterItemTagsProvider.QUALITY_PRODUCTS)) return;
        int value = random.nextInt(100);
        if (value <= DemeterConfig.netheriteQualityChance.get()) {
            writeQuality(stack, QualityLevel.NETHERITE);
        }
        if (value <= DemeterConfig.ironQualityChance.get() && value > DemeterConfig.netheriteQualityChance.get()) {
            writeQuality(stack, QualityLevel.IRON);
        }
        if (value <= DemeterConfig.copperQualityChance.get() && value > DemeterConfig.ironQualityChance.get()) {
            writeQuality(stack, QualityLevel.COPPER);
        }
    }

    public static QualityLevel getQuality(ItemStack stack) {
        return stack.get(ComponentRegistry.QUALITY_LEVEL).level();
    }

    public static void writeQuality(ItemStack stack, QualityLevel quality) {
        stack.set(ComponentRegistry.QUALITY_LEVEL.get(), new QualityLevelComponent(quality));
        overwriteFoodProperties(stack, quality);
    }

    public static void overwriteFoodProperties(ItemStack stack, QualityLevel qualityLevel) {
        if (stack.has(DataComponents.FOOD)) {
            FoodProperties originalProperties = stack.get(DataComponents.FOOD);
            FoodProperties newProperties = getFoodProperties(qualityLevel, originalProperties);
            stack.set(DataComponents.FOOD, newProperties);
        }
    }

    public static FoodProperties getFoodProperties(QualityLevel level, FoodProperties originalProperties) {
        float originalSat = calculateOriginalModifier(originalProperties.saturation(), originalProperties.nutrition());
        return switch (level) {
            case COPPER -> new FoodProperties.Builder().nutrition(originalProperties.nutrition() + 1)
                    .saturationModifier(originalSat + 0.1f).build();
            case IRON -> new FoodProperties.Builder().nutrition(originalProperties.nutrition() + 2)
                    .saturationModifier(originalSat + 0.2f).build();
            case NETHERITE -> new FoodProperties.Builder().nutrition(originalProperties.nutrition() + 3)
                    .saturationModifier(originalSat + 0.4f).build();
        };
    }

    public static int getNutrition(QualityLevel level, int original) {
        return original;
    }

    public static float getSaturation(QualityLevel level, float original) {
        return switch (level) {
            case COPPER -> original + 0.1f;
            case IRON -> original + 0.2f;
            case NETHERITE -> original + 0.4f;
        };
    }

    public static float calculateOriginalModifier(float saturation, int foodLevel) {
        return saturation / 2.0f / foodLevel;
    }
}
