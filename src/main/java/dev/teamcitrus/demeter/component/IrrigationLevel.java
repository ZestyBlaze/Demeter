package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;

public enum IrrigationLevel {
    COPPER, IRON, NETHERITE;

    public static final Codec<IrrigationLevel> CODEC = CitrusCodecs.enumCodec(IrrigationLevel.class);
}
