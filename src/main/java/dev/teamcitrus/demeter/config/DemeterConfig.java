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
    public static ModConfigSpec.IntValue pregnancyDownPeriod;

    // Crop Config
    public static ModConfigSpec.BooleanValue cropsWilt;
    public static ModConfigSpec.IntValue daysToWilt;

    //Love Config
    public static ModConfigSpec.IntValue spawnLoveValue;
    public static ModConfigSpec.IntValue pettingLoveValue;
    public static ModConfigSpec.IntValue brushingLoveValue;
    public static ModConfigSpec.IntValue feedingLoveValue;

    //Farmland Config
    public static ModConfigSpec.IntValue morningDirtChance;
    public static ModConfigSpec.BooleanValue waterIrrigationEnabled;

    //Enchantment Config
    public static ModConfigSpec.IntValue comfortBonusPerLevel;
    public static ModConfigSpec.EnumValue<SpiteEffect> spiteEffect;
    public static ModConfigSpec.IntValue loveLossPerSpiteLevel;

    //Quality Config
    public static ModConfigSpec.IntValue copperQualityChance;
    public static ModConfigSpec.IntValue ironQualityChance;
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
        builder.push("Animals Config");
        builder.comment("Configs that apply to the mod at large and apply to ALL animals");
        animalsDieOfHunger = builder.comment("Will animals die of hunger after a certain number of days of not being fed?")
                .define("animalsDieOfHunger", true);
        daysBeforeAnimalDie = builder.comment("The number of days that have to pass without feeding an animal before they die")
                .defineInRange("daysNoFood", 14, 1, Integer.MAX_VALUE);
        pregnancyDownPeriod = builder.comment("The number of days that must pass before an animal can get pregnant again")
                .defineInRange("pregnancyDownPeriod", 12, 1, Integer.MAX_VALUE);
        builder.pop();
        builder.push("Crops Config");
        cropsWilt = builder.comment("Will crops wilt after a number of days without being harvested")
                        .define("cropsWilt", true);
        daysToWilt = builder.comment("How many days it takes for crops to wilt once reaching maturity")
                        .defineInRange("daysToWilt", 3, 1, Integer.MAX_VALUE);
        builder.pop();
        builder.push("Love Config");
        builder.comment("Configs that affect how love works with animals");
        spawnLoveValue = builder.comment("The amount of love that animals will spawn with as default")
                        .defineInRange("spawnLoveValue", 90, 0, 100);
        animalHappinessMin = builder.comment("The minimum value of happiness required for an animal to be considered 'happy'")
                .defineInRange("animalHappinessMin", 65, 0, 100);
        pettingLoveValue = builder.comment("The amount of love that animals will gain from petting them")
                        .defineInRange("pettingLoveValue", 5, 0, 100);
        brushingLoveValue = builder.comment("The amount of love that animals will gain from brushing them")
                        .defineInRange("brushingLoveValue", 8, 0, 100);
        feedingLoveValue = builder.comment("The amount of love that animals will gain from feeding them")
                        .defineInRange("feedingLoveValue", 5, 0, 100);
        builder.pop();
        builder.push("Farmland Config");
        morningDirtChance = builder.comment("The percent chance for dry farmland to be turned back to dirt in the morning")
                        .defineInRange("morningDirtChance", 75, 0, 100);
        waterIrrigationEnabled = builder.comment("Whether vanillas feature of water hydrating farmland is enabled")
                        .define("waterIrrigationEnabled", false);
        builder.pop();
        builder.push("Enchantment Config");
        builder.comment("Configs that control how enchantments work within Demeter");
        comfortBonusPerLevel = builder.comment("The bonus amount of love the animal gains per level of the enchantment")
                        .defineInRange("comfortBonusPerLevel", 4, 0, 100);
        spiteEffect = builder.comment("Defines which effect the Spite curse will take when on a brush")
                        .defineEnum("spiteEffect", SpiteEffect.INVERT);
        loveLossPerSpiteLevel = builder.comment("The amount of total love loss when brushing per level of Spite")
                        .defineInRange("loveLossPerSpiteLevel", 4, 0, 100);
        builder.pop();
        builder.push("Quality Config");
        builder.comment("Quality works on a percent chance. Example: 60 = 60% chance. 0 to disable");
        copperQualityChance = builder.comment("The chance for copper quality items be dropped")
                .defineInRange("copperQualityChance", 25, 0, 100);
        ironQualityChance = builder.comment("The chance for iron quality items be dropped")
                .defineInRange("ironQualityChance", 10, 0, 100);
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
