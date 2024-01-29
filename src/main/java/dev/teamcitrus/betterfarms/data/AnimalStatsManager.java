package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AnimalStatsManager(int getDaysPregnant, int maxChildren) {
    public static final Codec<AnimalStatsManager> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("getDaysPregnant", 0).forGetter(a -> a.getDaysPregnant),
            Codec.INT.optionalFieldOf("maxChildren", 1).forGetter(a -> a.maxChildren)
    ).apply(func, AnimalStatsManager::new));

    /**
     * {@return how many days the animal will be pregnant for before giving birth}
     */
    public int getDaysPregnant() {
        return getDaysPregnant;
    }

    public int getMaxChildren() {
        return maxChildren;
    }
}
