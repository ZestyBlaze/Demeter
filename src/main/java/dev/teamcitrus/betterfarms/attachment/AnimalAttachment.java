package dev.teamcitrus.betterfarms.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.util.AnimalUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

public class AnimalAttachment {
    public static final Codec<AnimalAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("gender").forGetter(o -> o.gender.getId()),
            Codec.BOOL.fieldOf("isPregnant").forGetter(o -> o.isPregnant)
    ).apply(instance, AnimalAttachment::new));

    // Gender Variables
    private AnimalGenders gender;

    // Pregnancy Variables
    private boolean isPregnant;
    private int daysLeftUntilBirth;
    private Animal otherParent;

    public AnimalAttachment() {
        this(AnimalGenders.NONE.getId(), false);
    }

    public AnimalAttachment(String gender, boolean isPregnant) {
        this.gender = AnimalGenders.getGender(gender);
        this.isPregnant = isPregnant;
    }

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
            this.daysLeftUntilBirth = BFStatsManager.getStats(animal).daysPregnant();
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
