package dev.teamcitrus.demeter.duck;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;

public enum Section implements StringRepresentable {
    STRAIGHT, INNER, OUTER;

    public static final EnumProperty<Section> SECTION = EnumProperty.create("section", Section.class);

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
