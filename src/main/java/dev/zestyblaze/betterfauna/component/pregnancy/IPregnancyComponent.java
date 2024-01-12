package dev.zestyblaze.betterfauna.component.pregnancy;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.world.entity.animal.Animal;

public interface IPregnancyComponent extends ComponentV3 {
    void onNewDay();
    void setPregnant(boolean value, Animal otherParent);
    boolean getPregnant();
    int getDaysToBirth();
}
