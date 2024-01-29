package dev.teamcitrus.betterfarms.attachment;

import dev.teamcitrus.betterfarms.data.BFStatsListener;
import dev.teamcitrus.betterfarms.util.AnimalGenders;
import dev.teamcitrus.betterfarms.util.AnimalUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

public class AnimalAttachment {
    // Gender Variables
    private AnimalGenders gender;

    // Pregnancy Variables
    private boolean isPregnant = false;
    private int daysLeftUntilBirth;
    private Animal otherParent;

    /**
     * A method that runs at the start of the new day to handle certain factors
     * @param self The animal this is being fired from
     */
    public void onNewDay(Animal self) {
        if (isPregnant) {
            daysLeftUntilBirth--;
            if (daysLeftUntilBirth <= 0) {
                AnimalUtil.handleBirth(self, (ServerLevel)self.level(), otherParent);
                setPregnant(self, false, null);
            }
        }
    }

    /**
     * A method to set if an animal is pregnant or not
     * @param value Whether an animal is becoming pregnant or not
     * @param otherParent The other parents other than the self, can be null
     */
    public void setPregnant(Animal animal, boolean value, @Nullable Animal otherParent) {
        this.isPregnant = value;
        this.otherParent = otherParent;
        if (value) {
            this.daysLeftUntilBirth = BFStatsListener.getManager(animal).getDaysPregnant();
        }
    }
    
    public void setGender(AnimalGenders gender) {
        this.gender = gender;
    }

    public AnimalGenders getGender() {
        return this.gender;
    }

    public boolean getPregnant() {
        return isPregnant;
    }
}
