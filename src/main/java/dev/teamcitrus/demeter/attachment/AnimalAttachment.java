package dev.teamcitrus.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class AnimalAttachment {
    public static final Codec<AnimalAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("love").forGetter(o -> o.love),
            Codec.INT.fieldOf("daysSinceFed").forGetter(o -> o.daysSinceFed),
            Codec.BOOL.fieldOf("hasBeenPetToday").forGetter(o -> o.hasBeenPetToday),
            Codec.BOOL.fieldOf("hasBeenFedToday").forGetter(o -> o.hasBeenFedToday),
            AnimalGenders.CODEC.fieldOf("gender").forGetter(o -> o.gender),
            Codec.BOOL.fieldOf("isPregnant").forGetter(o -> o.isPregnant),
            Codec.INT.fieldOf("daysLeftUntilBirth").forGetter(o -> o.daysLeftUntilBirth),
            CompoundTag.CODEC.fieldOf("otherParent").forGetter(o -> o.otherParentData),
            Codec.INT.fieldOf("daysLeftUntilGrown").forGetter(o -> o.daysLeftUntilGrown)
    ).apply(instance, AnimalAttachment::new));

    // Animal Life Variables
    private int love, daysSinceFed;
    private boolean hasBeenPetToday, hasBeenFedToday;

    // Gender Variables
    private AnimalGenders gender;

    // Pregnancy Variables
    private boolean isPregnant;
    private int daysLeftUntilBirth;
    private CompoundTag otherParentData;

    // Growing Variables
    private int daysLeftUntilGrown;

    public AnimalAttachment() {
        this(0, 0, false, false, AnimalGenders.MALE,
                false, 0, new CompoundTag(), 0);
    }

    public AnimalAttachment(int love, int daysSinceFed, boolean hasBeenPetToday, boolean hasBeenFedToday,
                            AnimalGenders gender, boolean isPregnant, int daysLeftUntilBirth,
                            CompoundTag otherParentData, int daysLeftUntilGrown) {
        this.love = love;
        this.daysSinceFed = daysSinceFed;
        this.hasBeenPetToday = hasBeenPetToday;
        this.hasBeenFedToday = hasBeenFedToday;
        this.gender = gender;
        this.isPregnant = isPregnant;
        this.daysLeftUntilBirth = daysLeftUntilBirth;
        this.otherParentData = otherParentData;
        this.daysLeftUntilGrown = daysLeftUntilGrown;
    }

    /**
     * A method that runs at the start of the new day to handle certain factors
     * Fired from {@link NewDayEvent} at the start of each day
     *
     * @param self The animal this is being fired from
     */
    public void onNewDay(Animal self) {
        if (!hasBeenPetToday || !hasBeenFedToday) {
            alterLove(-2);
        }

        if (isPregnant) {
            daysLeftUntilBirth--;
            if (daysLeftUntilBirth <= 0) {
                Level level = self.level();
                EntityType.create(otherParentData, level).ifPresent(entity -> {
                    AnimalUtil.handleBirth(self, (ServerLevel) self.level(), (Animal) entity);
                    this.isPregnant = false;
                    this.otherParentData = new CompoundTag();
                });
            }
        }

        if (!hasBeenFedToday && DemeterConfig.animalsDieOfHunger.get()) {
            daysSinceFed++;
            if (daysSinceFed >= DemeterConfig.daysBeforeAnimalDie.get()) {
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

    /**
     * Allows for altering the love value without hard setting the values.
     * Method will auto-cap at values above 100 or below 0
     *
     * @param value Can be positive to increase or negative to decrease
     */
    public void alterLove(int value) {
        if ((love += value) > 100) {
            this.love = 100;
            return;
        }
        if ((love -= value) < 0) {
            this.love = 0;
            return;
        }
        this.love += value;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public void setHasBeenPetToday(boolean hasBeenPetToday) {
        this.hasBeenPetToday = hasBeenPetToday;
    }

    public boolean hasBeenPetToday() {
        return hasBeenPetToday;
    }

    public void setHasBeenFedToday(boolean hasBeenFedToday) {
        this.hasBeenFedToday = hasBeenFedToday;
        if (hasBeenFedToday) {
            daysSinceFed = 0;
        }
    }

    public void setDaysLeftUntilGrown(int daysLeftUntilGrown) {
        this.daysLeftUntilGrown = daysLeftUntilGrown;
    }

    /**
     * A method to set if an animal is pregnant or not
     *
     * @param value       Whether an animal is becoming pregnant or not
     * @param otherParent The other parents other than the self, can be null
     */
    public void setPregnant(Animal animal, boolean value, Animal otherParent) {
        this.isPregnant = value;
        this.otherParentData = otherParent.serializeNBT();
        if (value) {
            this.daysLeftUntilBirth = AnimalUtil.getStats(animal).get().daysPregnant();
        }
    }

    public boolean getPregnant() {
        return isPregnant;
    }

    public AnimalGenders getGender() {
        return gender;
    }

    public void setGender(AnimalGenders gender) {
        this.gender = gender;
    }

    public enum AnimalGenders {
        MALE, FEMALE;

        public static final Codec<AnimalGenders> CODEC = CitrusCodecs.enumCodec(AnimalGenders.class);
    }
}
