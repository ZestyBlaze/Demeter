package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @param daysPregnant Codec Variables
 */
public record AnimalStatsManager(int daysPregnant) {
    public static final Codec<AnimalStatsManager> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(a -> a.daysPregnant)
    ).apply(func, AnimalStatsManager::new));

    /**
     * {@return how many days the animal will be pregnant for before giving birth}
     */
    @Override
    public int daysPregnant() {
        return daysPregnant;
    }
}
