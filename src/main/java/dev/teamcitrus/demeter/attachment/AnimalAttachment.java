package dev.teamcitrus.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.datamaps.AnimalData;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class AnimalAttachment {
    public static final Codec<AnimalAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("love").forGetter(o -> o.love),
            Codec.INT.fieldOf("daysSinceFed").forGetter(o -> o.daysSinceFed),
            Codec.BOOL.fieldOf("hasBeenPetToday").forGetter(o -> o.hasBeenPetToday),
            Codec.BOOL.fieldOf("hasBeenFedToday").forGetter(o -> o.hasBeenFedToday),
            Codec.BOOL.fieldOf("hasBeenBrushedToday").forGetter(o -> o.hasBeenBrushedToday),
            AnimalGenders.CODEC.fieldOf("gender").forGetter(o -> o.gender),
            Codec.BOOL.fieldOf("isPregnant").forGetter(o -> o.isPregnant),
            Codec.INT.fieldOf("daysLeftUntilBirth").forGetter(o -> o.daysLeftUntilBirth),
            CompoundTag.CODEC.fieldOf("otherParent").forGetter(o -> o.otherParentData),
            Codec.INT.fieldOf("daysLeftUntilGrown").forGetter(o -> o.daysLeftUntilGrown)
    ).apply(instance, AnimalAttachment::new));

    // Animal Life Variables
    private int love, daysSinceFed;
    private boolean hasBeenPetToday, hasBeenFedToday, hasBeenBrushedToday;

    // Gender Variables
    private AnimalGenders gender;

    // Pregnancy Variables
    private boolean isPregnant;
    private int daysLeftUntilBirth;
    private CompoundTag otherParentData;

    // Growing Variables
    private int daysLeftUntilGrown;

    public AnimalAttachment() {
        this(DemeterConfig.spawnLoveValue.get(), 0, false, false, false,
                AnimalGenders.MALE, false, 0, new CompoundTag(), 0);
    }

    public AnimalAttachment(int love, int daysSinceFed, boolean hasBeenPetToday, boolean hasBeenFedToday,
                            boolean hasBeenBrushedToday, AnimalGenders gender, boolean isPregnant,
                            int daysLeftUntilBirth, CompoundTag otherParentData, int daysLeftUntilGrown) {
        this.love = love;
        this.daysSinceFed = daysSinceFed;
        this.hasBeenPetToday = hasBeenPetToday;
        this.hasBeenFedToday = hasBeenFedToday;
        this.hasBeenBrushedToday = hasBeenBrushedToday;
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
        if (!hasBeenPetToday) {
            alterLove(Optional.empty(), -5);
        }
        if (!hasBeenFedToday) {
            alterLove(Optional.empty(),-5);
        }

        if (isPregnant) {
            Demeter.LOGGER.error("Pregnant log");
            --daysLeftUntilBirth;
            Demeter.LOGGER.error("Days left: {}", daysLeftUntilBirth);
            if (daysLeftUntilBirth <= 0) {
                Level level = self.level();
                Demeter.LOGGER.error("Attempt to handle birth");
                EntityType.create(otherParentData, level).ifPresent(entity -> {
                    Demeter.LOGGER.error("Parent found, handling birth");
                    AnimalUtil.handleBirth(self, (ServerLevel) self.level(), (Animal) entity);
                    this.isPregnant = false;
                    this.otherParentData = new CompoundTag();
                });
            }
        }

        if (!hasBeenFedToday && DemeterConfig.animalsDieOfHunger.get()) {
            daysSinceFed++;
            if (daysSinceFed >= DemeterConfig.daysBeforeAnimalDie.get()) {
                self.discard();
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
        this.hasBeenBrushedToday = false;
    }

    /**
     * Allows for altering the love value without hard setting the values.
     * Method will auto-cap at values above 100 or below 0
     *
     * @param player An optional player as the reason why the love is increasing
     * @param value Can be positive to increase or negative to decrease
     */
    public void alterLove(Optional<ServerPlayer> player, int value) {
        this.love = Mth.clamp(this.love + value, 0, 100);
        if (player.isPresent() && love >= 100) {
            AdvancementRegistry.LOVE_MAX.get().trigger(player.get());
        }
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

    public int getDaysSinceFed() {
        return daysSinceFed;
    }

    public boolean hasBeenFedToday() {
        return hasBeenFedToday;
    }

    public void setHasBeenBrushedToday(boolean hasBeenBrushedToday) {
        this.hasBeenBrushedToday = hasBeenBrushedToday;
    }

    public boolean hasBeenBrushedToday() {
        return hasBeenBrushedToday;
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
        this.otherParentData = otherParent.serializeNBT(animal.level().registryAccess());
        if (value) {
            AnimalData data = AnimalUtil.getStats(animal);
            if (data != null) {
                this.daysLeftUntilBirth = AnimalUtil.getStats(animal).daysPregnant();
            }
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
