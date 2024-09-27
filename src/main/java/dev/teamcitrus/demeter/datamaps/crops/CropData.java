package dev.teamcitrus.demeter.datamaps.crops;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record CropData(int daysToGrow) {
    public static final Codec<CropData> CODEC = RecordCodecBuilder.create(in -> in.group(
                    ExtraCodecs.POSITIVE_INT.fieldOf("daysToGrow").forGetter(CropData::daysToGrow)
            ).apply(in, CropData::new));
}
