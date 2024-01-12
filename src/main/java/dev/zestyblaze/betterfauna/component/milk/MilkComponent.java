package dev.zestyblaze.betterfauna.component.milk;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Animal;

public class MilkComponent implements IMilkComponent, AutoSyncedComponent {
    private final Animal provider;
    private boolean beenMilked;

    public MilkComponent(Animal animal) {
        this.provider = animal;
    }

    @Override
    public void setBeenMilked(boolean value) {
        this.beenMilked = value;
        ComponentRegistry.MILK.sync(provider);
    }

    @Override
    public boolean getBeenMilked() {
        return this.beenMilked;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("milked", getBeenMilked());
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        setBeenMilked(tag.getBoolean("milked"));
    }
}
