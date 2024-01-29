package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AnimalStatsManager(int daysPregnant, int maxChildrenPerBirth) {
    public static final Codec<AnimalStatsManager> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(a -> a.daysPregnant),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(a -> a.maxChildrenPerBirth)
    ).apply(func, AnimalStatsManager::new));

    /**
     * {@return how many days the animal will be pregnant for before giving birth}
     */
    public int getDaysPregnant() {
        return daysPregnant;
    }

    /**
     * {@return how many children the animal can have max per birth}
     */
    public int getMaxChildrenPerBirth() {
        return maxChildrenPerBirth;
    }
}
