package dev.zestyblaze.betterfauna.component.milk;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface IMilkComponent extends ComponentV3 {
    void setBeenMilked(boolean value);
    boolean getBeenMilked();
}
