package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.util.AnimalUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

public class AnimalStatsManager {
    // Codec Variables
    private final int daysPregnant;

    // Pregnancy Variables
    private boolean isPregnant = false;
    private int daysLeftUntilBirth;
    private Animal otherParent;

    public static final Codec<AnimalStatsManager> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(a -> a.daysPregnant)
    ).apply(func, AnimalStatsManager::new));

    public AnimalStatsManager(int daysPregnant) {
        this.daysPregnant = daysPregnant;
    }

    /**
     * A method that runs at the start of the new day to handle certain factors
     * @param self The animal this is being fired from
     */
    public void onNewDay(Animal self) {
        if (isPregnant) {
            BetterFarms.LOGGER.warn("Animal Pregnant on New Day");
            daysLeftUntilBirth--;
            if (daysLeftUntilBirth <= 0) {
                AnimalUtil.handleBirth(self, (ServerLevel)self.level(), otherParent);
                setPregnant(false, null);
            }
        }
    }

    /**
     * A method to set if an animal is pregnant or not
     * @param value Whether an animal is becoming pregnant or not
     * @param otherParent The other parents other than the self, can be null
     */
    public void setPregnant(boolean value, @Nullable Animal otherParent) {
        this.isPregnant = value;
        this.otherParent = otherParent;
        if (value) {
            this.daysLeftUntilBirth = getDaysPregnant();
        }
    }

    /**
     * {@return how many days the animal will be pregnant for before giving birth}
     */
    public int getDaysPregnant() {
        return daysPregnant;
    }

    public boolean getPregnant() {
        return isPregnant;
    }
}
