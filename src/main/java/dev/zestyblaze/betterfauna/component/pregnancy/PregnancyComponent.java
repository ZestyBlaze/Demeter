package dev.zestyblaze.betterfauna.component.pregnancy;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
import dev.zestyblaze.betterfauna.util.AnimalUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

public class PregnancyComponent implements IPregnancyComponent, AutoSyncedComponent {
    private final Animal self;
    private final int daysToBirth;
    private int daysLeftTilBirth;
    private boolean isPregnant;
    private Animal otherParent;

    public PregnancyComponent(Animal animal, int daysPregnant) {
        this.self = animal;
        this.daysToBirth = daysPregnant;
    }

    public void onNewDay() {
        if (getPregnant()) {
            daysLeftTilBirth--;
            if (daysLeftTilBirth <= 0) {
                AnimalUtil.handleBirth(self, (ServerLevel)self.level(), otherParent);
                setPregnant(false, null);
            }
        }
    }

    @Override
    public void setPregnant(boolean value, Animal otherParent) {
        this.isPregnant = value;
        this.otherParent = otherParent;
        if (value) {
            this.daysLeftTilBirth = getDaysToBirth();
        }
        ComponentRegistry.PREGNANCY.sync(self);
    }

    @Override
    public boolean getPregnant() {
        return this.isPregnant;
    }

    @Override
    public int getDaysToBirth() {
        return this.daysToBirth;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("pregnant", getPregnant());
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.isPregnant = tag.getBoolean("pregnant");
    }
}
