package dev.teamcitrus.betterfarms.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BetterFarmsConfig {
    public static final ModConfigSpec GENERAL_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;

    //General Config
    public static ModConfigSpec.IntValue animalHappinessMin;

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
        builder.pop();
    }

    private static void setClientConfig(ModConfigSpec.Builder builder) {
        builder.push("QOL Features");
        animalBirthAlert = builder.comment("Should an alert be displayed when your animals give birth")
                .define("animalBirthAlert", true);
        builder.pop();
    }
}
