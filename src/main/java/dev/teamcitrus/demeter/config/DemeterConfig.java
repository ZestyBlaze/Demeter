package dev.teamcitrus.demeter.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class DemeterConfig {
    public static final ModConfigSpec GENERAL_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;

    //Animal Config
    public static ModConfigSpec.BooleanValue animalsDieOfOldAge;
    public static ModConfigSpec.IntValue animalHappinessMin;
    public static ModConfigSpec.BooleanValue animalsDieOfHunger;
    public static ModConfigSpec.IntValue daysBeforeAnimalDie;
    public static ModConfigSpec.IntValue daysBeforeProductsStop;
    public static ModConfigSpec.IntValue pregnancyDownPeriod;
    public static ModConfigSpec.IntValue woolGrowthDays;

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
    public static ModConfigSpec.EnumValue<ClockType> clockType;
    public static ModConfigSpec.BooleanValue displayClockInHUD;
    public static ModConfigSpec.BooleanValue requireClockItemForTime;
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
        builder.translation("config.demeter.animal_config").push("Animals Config");
        animalsDieOfOldAge = builder.translation("config.demeter.animal_config.old_age")
                .comment("Controls if animals can die of old age after a few years")
                .define("animalsDieOfOldAge", true);
        animalsDieOfHunger = builder.translation("config.demeter.animal_config.hunger")
                .comment("Will animals die of hunger after a certain number of days of not being fed?")
                .define("animalsDieOfHunger", true);
        daysBeforeAnimalDie = builder.translation("config.demeter.animal_config.hunger_days")
                .comment("The number of days that have to pass without feeding an animal before they die")
                .defineInRange("daysNoFood", 14, 1, Integer.MAX_VALUE);
        daysBeforeProductsStop = builder.translation("config.demeter.animal_config.product_days")
                .comment("The number of days before animals will stop producing products without being fed")
                .defineInRange("noProductsNoFood", 3, 1, Integer.MAX_VALUE);
        pregnancyDownPeriod = builder.translation("config.demeter.animal_config.pregnancy_down_period")
                .comment("The number of days that must pass before an animal can get pregnant again")
                .defineInRange("pregnancyDownPeriod", 12, 1, Integer.MAX_VALUE);
        woolGrowthDays = builder.translation("config.demeter.animal_config.wool_growth_days")
                .comment("The number of days it takes for Sheep to grow their wool back")
                .defineInRange("woolGrowthDays", 7, 1, Integer.MAX_VALUE);
        builder.pop();

        builder.translation("config.demeter.crop_config").push("Crops Config");
        cropsWilt = builder.translation("config.demeter.crop_config.crops_wilt")
                .comment("Will crops wilt after a number of days without being harvested")
                .define("cropsWilt", true);
        daysToWilt = builder.translation("config.demeter.crop_config.crop_wilt_days")
                .comment("How many days it takes for crops to wilt once reaching maturity")
                .defineInRange("daysToWilt", 3, 1, Integer.MAX_VALUE);
        builder.pop();

        builder.translation("config.demeter.love_config").push("Love Config");
        spawnLoveValue = builder.translation("config.demeter.love_config.spawn_love_value")
                .comment("The amount of love that animals will spawn with as default")
                .defineInRange("spawnLoveValue", 90, 0, 100);
        animalHappinessMin = builder.translation("config.demeter.love_config.min_happiness")
                .comment("The minimum value of happiness required for an animal to be considered 'happy'")
                .defineInRange("animalHappinessMin", 65, 0, 100);
        pettingLoveValue = builder.translation("config.demeter.love_config.pet_love")
                .comment("The amount of love that animals will gain from petting them")
                .defineInRange("pettingLoveValue", 5, 0, 100);
        brushingLoveValue = builder.translation("config.demeter.love_config.brush_love")
                .comment("The amount of love that animals will gain from brushing them")
                .defineInRange("brushingLoveValue", 8, 0, 100);
        feedingLoveValue = builder.translation("config.demeter.love_config.feed_love")
                .comment("The amount of love that animals will gain from feeding them")
                .defineInRange("feedingLoveValue", 5, 0, 100);
        builder.pop();

        builder.translation("config.demeter.farmland_config").push("Farmland Config");
        morningDirtChance = builder.translation("config.demeter.farmland_config.morning_dirt_chance")
                .comment("The percent chance for dry farmland to be turned back to dirt in the morning")
                .defineInRange("morningDirtChance", 75, 0, 100);
        waterIrrigationEnabled = builder.translation("config.demeter.farmland_config.irrigation_enabled")
                .comment("Whether vanillas feature of water hydrating farmland is enabled")
                .define("waterIrrigationEnabled", false);
        builder.pop();

        builder.translation("config.demeter.enchantment_config").push("Enchantment Config");
        comfortBonusPerLevel = builder.translation("config.demeter.enchantment_config.comfort_bonus")
                .comment("The bonus amount of love the animal gains per level of the enchantment")
                .defineInRange("comfortBonusPerLevel", 4, 0, 100);
        spiteEffect = builder.translation("config.demeter.enchantment_config.spite_effect")
                .comment("Defines which effect the Spite curse will take when on a brush")
                .defineEnum("spiteEffect", SpiteEffect.INVERT);
        loveLossPerSpiteLevel = builder.translation("config.demeter.enchantment_config.spite_loss")
                .comment("The amount of total love loss when brushing per level of Spite")
                .defineInRange("loveLossPerSpiteLevel", 4, 0, 100);
        builder.pop();

        builder.translation("config.demeter.quality_config").push("Quality Config");
        copperQualityChance = builder.translation("config.demeter.quality_config.copper_chance")
                .comment("The chance for copper quality items be dropped")
                .defineInRange("copperQualityChance", 25, 0, 100);
        ironQualityChance = builder.translation("config.demeter.quality_config.iron_chance")
                .comment("The chance for iron quality items be dropped")
                .defineInRange("ironQualityChance", 10, 0, 100);
        netheriteQualityChance = builder.translation("config.demeter.quality_config.netherite_chance")
                .comment("The chance for netherite quality items be dropped")
                .defineInRange("netheriteQualityChance", 1, 0, 100);
        builder.pop();
    }

    private static void setClientConfig(ModConfigSpec.Builder builder) {
        builder.translation("config.demeter.hud_config").push("HUD Options");
        clockType = builder.translation("config.demeter.hud_config.clock_type")
                .comment("Defines what type of clock the HUD will use to display the time")
                .defineEnum("clockType", ClockType.TWENTY_FOUR_HOUR);
        displayClockInHUD = builder.translation("config.demeter.hud_config.display_time_hud")
                .comment("If the time should be shown on the player's HUD (EXPERIMENTAL)")
                .define("displayTimeInHUD", false);
        requireClockItemForTime = builder.translation("config.demeter.hud_config.require_clock_time")
                .comment("If a \"#c:clocks\" item is required in hand for the time to be shown (This works well with the Pocket Watch accessory)")
                .define("requireClockItemForTime", false);
        builder.pop();

        builder.translation("config.demeter.qol_config").push("QOL Features");
        animalBirthAlert = builder.translation("config.demeter.qol_config.birth_alert")
                .comment("Should an alert be displayed when your animals give birth")
                .define("animalBirthAlert", true);
        builder.pop();
    }

    public enum ClockType {
        TWENTY_FOUR_HOUR, TWELVE_HOUR
    }

    public enum SpiteEffect {
        INVERT, HALVE, REDUCE
    }
}
