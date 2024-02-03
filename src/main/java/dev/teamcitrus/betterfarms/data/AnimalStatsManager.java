package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AnimalStatsManager(int daysPregnant, int maxChildrenPerBirth, boolean canBeMilked) {
    public static final Codec<AnimalStatsManager> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(a -> a.daysPregnant),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(a -> a.maxChildrenPerBirth),
            Codec.BOOL.optionalFieldOf("canBeMilked", false).forGetter(a -> a.canBeMilked)
    ).apply(func, AnimalStatsManager::new));

    public int getDaysPregnant() {
        return daysPregnant;
    }

    public int getMaxChildrenPerBirth() {
        return maxChildrenPerBirth;
    }

    public boolean getCanBeMilked() {
        return canBeMilked;
    }
}
