package dev.teamcitrus.demeter.data.crops;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CropInfo(
        int daysToGrow
) implements ICrop {
    public static final Codec<CropInfo> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.fieldOf("daysToGrow").forGetter(CropInfo::daysToGrow)
    ).apply(func, CropInfo::new));

    @Override
    public Codec<? extends ICrop> getCodec() {
        return CODEC;
    }
}
