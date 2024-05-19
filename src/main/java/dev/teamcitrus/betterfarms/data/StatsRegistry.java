package dev.teamcitrus.betterfarms.data;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.citruslib.reload.DynamicRegistry;

public class StatsRegistry extends DynamicRegistry<IStats> {
    public static final StatsRegistry INSTANCE = new StatsRegistry();

    private StatsRegistry() {
        super(BetterFarms.LOGGER, "betterfarms/stats", true, true);
    }

    @Override
    protected void registerBuiltinCodecs() {
        registerDefaultCodec(BetterFarms.id("stats"), AnimalStats.CODEC);
    }
}
