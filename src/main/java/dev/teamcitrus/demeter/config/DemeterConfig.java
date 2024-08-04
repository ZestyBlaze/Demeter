package dev.teamcitrus.demeter.config;

import dev.teamcitrus.demeter.config.enums.SpiteEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class DemeterConfig {
    public static final ModConfigSpec GENERAL_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;

    //General Config
    public static ModConfigSpec.IntValue animalHappinessMin;
    public static ModConfigSpec.BooleanValue animalsDieOfHunger;
    public static ModConfigSpec.IntValue daysBeforeAnimalDie;

    //Love Config
    public static ModConfigSpec.IntValue spawnLoveValue;
    public static ModConfigSpec.IntValue pettingLoveValue;
    public static ModConfigSpec.IntValue brushingLoveValue;
    public static ModConfigSpec.IntValue feedingLoveValue;

    //Enchantment Config
    public static ModConfigSpec.IntValue comfortBonusPerLevel;
    public static ModConfigSpec.EnumValue<SpiteEffect> spiteEffect;
    public static ModConfigSpec.IntValue loveLossPerSpiteLevel;

    //Quality Config
    public static ModConfigSpec.IntValue copperQualityChance;
    public static ModConfigSpec.IntValue ironQualityChance;
    public static ModConfigSpec.IntValue goldQualityChance;
    public static ModConfigSpec.IntValue diamondQualityChance;
    public static ModConfigSpec.IntValue netheriteQualityChance;

    //Client Config
    public static ModConfigSpec.BooleanValue animalBirthAlert;

    static {
        ModConfigSpec.Builder clientBuilder = new ModConfigSpec.Builder();
        ModConfigSpec.Builder generalBuilder = new ModConfigSpec.Builder();
        setClientConfig(clientBuilder);
        setupConfig(generalBuilder);
        CLIENT_SPEC = clientBuilder.build();
        GENERAL_SPEC = generalBuilder.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("All Animals");
        animalHappinessMin = builder.comment("The minimum value of happiness required for an animal to be considered 'happy'")
                .defineInRange("animalHappinessMin", 65, 0, 100);
        animalsDieOfHunger = builder.comment("Will animals die of hunger after a certain number of days of not being fed?")
                .define("animalsDieOfHunger", true);
        daysBeforeAnimalDie = builder.comment("The number of days that have to pass without feeding an animal before they die")
                .defineInRange("daysNoFood", 14, 1, Integer.MAX_VALUE);
        builder.pop();
        builder.push("Love");
        spawnLoveValue = builder.comment("The amount of love that animals will spawn with as default")
                        .defineInRange("spawnLoveValue", 0, 0, 100);
        pettingLoveValue = builder.comment("The amount of love that animals will gain from petting them")
                        .defineInRange("pettingLoveValue", 5, 0, 100);
        brushingLoveValue = builder.comment("The amount of love that animals will gain from brushing them")
                        .defineInRange("brushingLoveValue", 8, 0, 100);
        feedingLoveValue = builder.comment("The amount of love that animals will gain from feeding them")
                        .defineInRange("feedingLoveValue", 5, 0, 100);
        builder.pop();
        builder.push("Enchantments");
        comfortBonusPerLevel = builder.comment("The bonus amount of love the animal gains per level of the enchantment")
                        .defineInRange("comfortBonusPerLevel", 4, 0, 100);
        spiteEffect = builder.comment("Defines which effect the Spite curse will take when on a brush")
                        .defineEnum("spiteEffect", SpiteEffect.INVERT);
        loveLossPerSpiteLevel = builder.comment("The amount of total love loss when brushing per level of Spite")
                        .defineInRange("loveLossPerSpiteLevel", 4, 0, 100);
        builder.pop();
        builder.push("Quality");
        builder.comment("Quality works on a percent chance. Example: 60 = 60% chance. 0 to disable");
        copperQualityChance = builder.comment("The chance for copper quality items be dropped")
                .defineInRange("copperQualityChance", 40, 0, 100);
        ironQualityChance = builder.comment("The chance for iron quality items be dropped")
                .defineInRange("ironQualityChance", 20, 0, 100);
        goldQualityChance = builder.comment("The chance for gold quality items be dropped")
                .defineInRange("goldQualityChance", 10, 0, 100);
        diamondQualityChance = builder.comment("The chance for diamond quality items be dropped")
                .defineInRange("diamondQualityChance", 5, 0, 100);
        netheriteQualityChance = builder.comment("The chance for netherite quality items be dropped")
                .defineInRange("netheriteQualityChance", 1, 0, 100);
        builder.pop();
    }

    private static void setClientConfig(ModConfigSpec.Builder builder) {
        builder.push("QOL Features");
        animalBirthAlert = builder.comment("Should an alert be displayed when your animals give birth")
                .define("animalBirthAlert", true);
        builder.pop();
    }
}
