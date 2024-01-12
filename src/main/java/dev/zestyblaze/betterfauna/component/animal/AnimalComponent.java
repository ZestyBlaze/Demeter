package dev.zestyblaze.betterfauna.component.animal;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Animal;

public class AnimalComponent implements IAnimalComponent, AutoSyncedComponent {
    private final Animal provider;
    private int love = 0;
    private Gender gender = Gender.NONE;

    public AnimalComponent(Animal animal) {
        this.provider = animal;
    }

    @Override
    public void setLove(int amount) {
        if (amount <= 100 && amount >= 0) {
            this.love = amount;
            ComponentRegistry.ANIMAL.sync(provider);
        }
    }

    @Override
    public int getLove() {
        return this.love;
    }

    @Override
    public void incrementLove(int amount) {
        if ((getLove() + amount) <= 100) {
            this.love += amount;
        } else {
            this.love = 100;
        }
        ComponentRegistry.ANIMAL.sync(provider);
    }

    @Override
    public void shrinkLove(int amount) {
        if ((getLove() - amount) >= 0) {
            this.love -= amount;
        } else {
            this.love = 0;
        }
        ComponentRegistry.ANIMAL.sync(provider);
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
        ComponentRegistry.ANIMAL.sync(provider);
    }

    @Override
    public Gender getGender() {
        return this.gender;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("love", getLove());
        tag.putString("gender", getGender().getId());
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        setLove(tag.getInt("love"));
        setGender(Gender.getGender(tag.getString("gender")));
    }

    public enum Gender {
        MALE("male"), FEMALE("female"), NONE("none");

        private final String id;

        Gender(String name) {
            this.id = name;
        }

        public String getId() {
            return id;
        }

        public static Gender getGender(String id) {
            if (id.equals(FEMALE.getId())) {
                return Gender.FEMALE;
            }
            return Gender.MALE;
        }
    }
}
