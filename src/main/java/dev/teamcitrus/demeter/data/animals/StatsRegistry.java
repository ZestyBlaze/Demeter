package dev.teamcitrus.demeter.data.animals;

import dev.teamcitrus.citruslib.reload.DynamicRegistry;
import dev.teamcitrus.demeter.Demeter;

public class StatsRegistry extends DynamicRegistry<IStats> {
    public static final StatsRegistry INSTANCE = new StatsRegistry();

    private StatsRegistry() {
        super(Demeter.LOGGER, "demeter/stats", true, true);
    }

    @Override
    protected void registerBuiltinCodecs() {
        registerDefaultCodec(Demeter.id("stats"), AnimalStats.CODEC);
    }
}
