package dev.teamcitrus.demeter.data;

import dev.teamcitrus.citruslib.reload.DynamicRegistry;
import dev.teamcitrus.demeter.Demeter;

public class StatsRegistry extends DynamicRegistry<IStats> {
    public static final StatsRegistry INSTANCE = new StatsRegistry();

    private StatsRegistry() {
        super(Demeter.LOGGER, "betterfarms/stats", true, true);
    }

    @Override
    protected void registerBuiltinCodecs() {
        registerDefaultCodec(Demeter.id("stats"), AnimalStats.CODEC);
    }
}
