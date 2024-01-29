package dev.teamcitrus.betterfarms.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BetterFarmsConfig {
    public static final ModConfigSpec GENERAL_SPEC;

    public static ModConfigSpec.IntValue animalHappinessMin;

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
    }
}
