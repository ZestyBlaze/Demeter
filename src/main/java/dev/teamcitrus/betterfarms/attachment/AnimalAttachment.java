package dev.teamcitrus.betterfarms.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.betterfarms.api.util.AnimalUtil;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.event.custom.level.NewDayEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AnimalAttachment {
    public static final Codec<AnimalAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("keeper").forGetter(o -> o.keeperUUID),
            Codec.INT.fieldOf("loveForKeeper").forGetter(o -> o.loveForKeeper),
            Codec.BOOL.fieldOf("hasBeenPetToday").forGetter(o -> o.hasBeenPetToday),
            Codec.BOOL.fieldOf("hasBeenFedToday").forGetter(o -> o.hasBeenFedToday),
            Codec.STRING.fieldOf("gender").forGetter(o -> o.gender.getId()),
            Codec.BOOL.fieldOf("isPregnant").forGetter(o -> o.isPregnant)
    ).apply(instance, AnimalAttachment::new));

    // Animal Life Variables
    private String keeperUUID;
    private int loveForKeeper;
    private boolean hasBeenPetToday;
    private boolean hasBeenFedToday;

    // Gender Variables
    private AnimalGenders gender;

    // Pregnancy Variables
    private boolean isPregnant;
    private int daysLeftUntilBirth;
    private Animal otherParent;

    public AnimalAttachment() {
        this("", 0, false, false, AnimalGenders.NONE.getId(), false);
    }

    public AnimalAttachment(String keeperUUID, int loveForKeeper, boolean hasBeenPetToday, boolean hasBeenFedToday, String gender, boolean isPregnant) {
        this.keeperUUID = keeperUUID;
        this.loveForKeeper = loveForKeeper;
        this.hasBeenPetToday = hasBeenPetToday;
        this.hasBeenFedToday = hasBeenFedToday;
        this.gender = AnimalGenders.getGender(gender);
        this.isPregnant = isPregnant;
    }

    /**
     * A method that runs at the start of the new day to handle certain factors
     * Fired from {@link NewDayEvent} at the start of each day
     * @param self The animal this is being fired from
     */
    public void onNewDay(Animal self) {
        if (!hasBeenPetToday) {
            this.loveForKeeper -= 2;
        }
        if (!hasBeenFedToday) {
            this.loveForKeeper -= 2;
        }

        if (isPregnant) {
            daysLeftUntilBirth--;
            if (daysLeftUntilBirth <= 0) {
                AnimalUtil.handleBirth(self, (ServerLevel)self.level(), otherParent);
                this.isPregnant = false;
                this.otherParent = null;
            }
        }

        //Reset Daily Values
        this.hasBeenPetToday = false;
        this.hasBeenFedToday = false;
    }

    public void setKeeperUUID(UUID keeperUUID) {
        setKeeperUUID(keeperUUID.toString());
    }

    public void setKeeperUUID(String keeperUUID) {
        this.keeperUUID = keeperUUID;
    }

    public String getKeeperUUID() {
        return keeperUUID;
    }

    public void setLoveForKeeper(int loveForKeeper) {
        this.loveForKeeper = loveForKeeper;
    }

    /**
     * Allows for altering the love value without hard setting the values
     * Method will auto-cap at values above 100 or below 0
     * @param value Can be positive to increase or negative to decrease
     */
    public void alterLoveForKeeper(int value) {
        if ((loveForKeeper += value) > 100) {
            this.loveForKeeper = 100;
            return;
        }
        if ((loveForKeeper-= value) < 0) {
            this.loveForKeeper = 0;
            return;
        }
        this.loveForKeeper += value;
    }

    public int getLoveForKeeper() {
        return loveForKeeper;
    }

    public void setHasBeenPetToday(boolean hasBeenPetToday) {
        this.hasBeenPetToday = hasBeenPetToday;
    }

    public boolean hasBeenPetToday() {
        return hasBeenPetToday;
    }

    public void setHasBeenFedToday(boolean hasBeenFedToday) {
        this.hasBeenFedToday = hasBeenFedToday;
    }

    public boolean hasBeenFedToday() {
        return hasBeenFedToday;
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
            this.daysLeftUntilBirth = BFStatsManager.getStats(animal).daysPregnant();
        }
    }

    public boolean getPregnant() {
        return isPregnant;
    }
    
    public void setGender(AnimalGenders gender) {
        this.gender = gender;
    }

    public AnimalGenders getGender() {
        return gender;
    }

    public enum AnimalGenders {
        MALE("male"), FEMALE("female"), NONE("none");

        private final String id;

        AnimalGenders(String name) {
            this.id = name;
        }

        public String getId() {
            return id;
        }

        public static AnimalGenders getGender(String id) {
            if (id.equals(FEMALE.getId())) {
                return FEMALE;
            } else if (id.equals(MALE.getId())) {
                return MALE;
            }
            return NONE;
        }
    }
}
