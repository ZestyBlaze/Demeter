package dev.teamcitrus.demeter.data.crops;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CropInfo implements ICrop {
    public static final Codec<CropInfo> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.fieldOf("daysToGrow").forGetter(CropInfo::daysToGrow)
    ).apply(func, CropInfo::new));

    private final int daysToGrow;

    public CropInfo(int daysToGrow) {
        this.daysToGrow = daysToGrow;
    }

    @Override
    public Codec<? extends ICrop> getCodec() {
        return CODEC;
    }

    @Override
    public int daysToGrow() {
        return daysToGrow;
    }
}
