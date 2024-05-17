package dev.teamcitrus.betterfarms.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.betterfarms.api.util.AnimalUtil;
import dev.teamcitrus.betterfarms.config.BetterFarmsConfig;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.event.custom.level.NewDayEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

public class AnimalAttachment {
    public static final Codec<AnimalAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("loveForKeeper").forGetter(o -> o.love),
            Codec.BOOL.fieldOf("hasBeenPetToday").forGetter(o -> o.hasBeenPetToday),
            Codec.BOOL.fieldOf("hasBeenFedToday").forGetter(o -> o.hasBeenFedToday),
            Codec.STRING.fieldOf("gender").forGetter(o -> o.gender.getId()),
            Codec.BOOL.fieldOf("isPregnant").forGetter(o -> o.isPregnant)
    ).apply(instance, AnimalAttachment::new));

    // Animal Life Variables
    private int love, daysSinceFed;
    private boolean hasBeenPetToday, hasBeenFedToday;

    // Gender Variables
    private AnimalGenders gender;

    // Pregnancy Variables
    private boolean isPregnant;
    private int daysLeftUntilBirth;
    private Animal otherParent;

    // Growing Variables
    private int daysLeftUntilGrown;

    public AnimalAttachment() {
        this(0, false, false, AnimalGenders.MALE.getId(), false);
    }

    public AnimalAttachment(int love, boolean hasBeenPetToday, boolean hasBeenFedToday, String gender, boolean isPregnant) {
        this.love = love;
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
        if (!hasBeenPetToday || !hasBeenFedToday) {
            alterLoveForKeeper(-2);
        }

        if (isPregnant) {
            daysLeftUntilBirth--;
            if (daysLeftUntilBirth <= 0) {
                AnimalUtil.handleBirth(self, (ServerLevel)self.level(), otherParent);
                this.isPregnant = false;
                this.otherParent = null;
            }
        }

        if (!hasBeenFedToday && BetterFarmsConfig.animalsDieOfHunger.get()) {
            daysSinceFed++;
            if (daysSinceFed >= BetterFarmsConfig.daysBeforeAnimalDie.get()) {
                self.die(self.level().damageSources().genericKill());
            }
        }

        if (self.isBaby()) {
            --daysLeftUntilGrown;
            if (daysLeftUntilGrown <= 0) {
                self.setBaby(false);
            }
        }

        //Reset Daily Values
        this.hasBeenPetToday = false;
        this.hasBeenFedToday = false;
    }

    public void setLove(int love) {
        this.love = love;
    }

    /**
     * Allows for altering the love value without hard setting the values
     * Method will auto-cap at values above 100 or below 0
     * @param value Can be positive to increase or negative to decrease
     */
    public void alterLoveForKeeper(int value) {
        if ((love += value) > 100) {
            this.love = 100;
            return;
        }
        if ((love-= value) < 0) {
            this.love = 0;
            return;
        }
        this.love += value;
    }

    public int getLove() {
        return love;
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

    public void setDaysLeftUntilGrown(int daysLeftUntilGrown) {
        this.daysLeftUntilGrown = daysLeftUntilGrown;
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
        MALE("male"), FEMALE("female");

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
            } else {
                return MALE;
            }
        }
    }
}
