package dev.teamcitrus.betterfarms.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BetterFarmsConfig {
    public static final ModConfigSpec GENERAL_SPEC;

    public static ModConfigSpec.IntValue animalHappinessMin;
    public static ModConfigSpec.BooleanValue pigsMultipleChildren;
    public static ModConfigSpec.IntValue pigsDaysPregnant;
    public static ModConfigSpec.IntValue pigsMultipleChildrenMax;
    public static ModConfigSpec.BooleanValue rabbitsMultipleChildren;
    public static ModConfigSpec.IntValue rabbitsMultipleChildrenMax;

    static {
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("All Animals");
        animalHappinessMin = builder.comment("The minimum value of happiness required for an animal to be considered 'happy'")
                .defineInRange("animalHappinessMin", 65, 0, 100);
        builder.pop();
        builder.push("Pigs");
        pigsMultipleChildren = builder.comment("Can Pigs give birth to multiple piglets?")
                .define("pigsMultipleChildren", true);
        pigsDaysPregnant = builder.comment("The number of days in Minecraft that a Pig will be pregnant before giving birth")
                .defineInRange("pigsDaysPregnant", 4, 1, 99);
        pigsMultipleChildrenMax = builder.comment("The maximum number of offspring a Pig can have in a litter")
                .defineInRange("pigsMultipleChildrenMax", 7, 1, 10);
        builder.pop();
        builder.push("Rabbits");
        rabbitsMultipleChildren = builder.comment("Can Rabbits give birth to multiple piglets?")
                .define("rabbitsMultipleChildren", true);
        rabbitsMultipleChildrenMax = builder.comment("The maximum number of offspring a Rabbit can have in a litter")
                .defineInRange("rabbitsMultipleChildrenMax", 6, 1, 10);
        builder.pop();
    }
}
