package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;

public enum WateringCanLevel {
    RUSTY, COPPER, IRON, GOLDEN;

    public static final Codec<WateringCanLevel> CODEC = CitrusCodecs.enumCodec(WateringCanLevel.class);
}
