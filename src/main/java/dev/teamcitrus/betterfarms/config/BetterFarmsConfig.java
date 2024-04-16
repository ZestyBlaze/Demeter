package dev.teamcitrus.betterfarms.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BetterFarmsConfig {
    public static final ModConfigSpec GENERAL_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;

    //General Config
    public static ModConfigSpec.IntValue animalHappinessMin;
    public static ModConfigSpec.IntValue daysBeforeAnimalDie;

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
        daysBeforeAnimalDie = builder.comment("The number of days that have to pass without feeding an animal before they die")
                        .defineInRange("daysNoFood", 14, 1, Integer.MAX_VALUE);
        builder.pop();
        builder.push("Quality Crops");
        copperQualityChance = builder.comment("The chance for copper quality to appear. Example: 60 = 60% chance. 0 to disable").defineInRange("copperQualityChance", 40, 0, 100);
        ironQualityChance = builder.comment("The chance for iron quality to appear. Example: 60 = 60% chance. 0 to disable").defineInRange("ironQualityChance", 20, 0, 100);
        goldQualityChance = builder.comment("The chance for gold quality to appear. Example: 60 = 60% chance. 0 to disable").defineInRange("goldQualityChance", 10, 0, 100);
        diamondQualityChance = builder.comment("The chance for diamond quality to appear. Example: 60 = 60% chance. 0 to disable").defineInRange("diamondQualityChance", 5, 0, 100);
        netheriteQualityChance = builder.comment("The chance for netherite quality to appear. Example: 60 = 60% chance. 0 to disable").defineInRange("netheriteQualityChance", 1, 0, 100);
        builder.pop();
    }

    private static void setClientConfig(ModConfigSpec.Builder builder) {
        builder.push("QOL Features");
        animalBirthAlert = builder.comment("Should an alert be displayed when your animals give birth")
                .define("animalBirthAlert", true);
        builder.pop();
    }
}
