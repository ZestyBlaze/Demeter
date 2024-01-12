package dev.zestyblaze.betterfauna.component.animal;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface IAnimalComponent extends ComponentV3 {
    void setLove(int amount);
    int getLove();
    void incrementLove(int amount);
    void shrinkLove(int amount);
    void setGender(AnimalComponent.Gender gender);
    AnimalComponent.Gender getGender();
}
