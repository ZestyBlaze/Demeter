package dev.teamcitrus.demeter.data.crops;

import dev.teamcitrus.citruslib.reload.DynamicRegistry;
import dev.teamcitrus.demeter.Demeter;

public class CropRegistry extends DynamicRegistry<ICrop> {
    public static final CropRegistry INSTANCE = new CropRegistry();

    private CropRegistry() {
        super(Demeter.LOGGER, "demeter/crops", true, true);
    }

    @Override
    protected void registerBuiltinCodecs() {
        registerDefaultCodec(Demeter.id("crops"), CropInfo.CODEC);
    }
}
