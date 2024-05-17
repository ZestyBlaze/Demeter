package dev.teamcitrus.betterfarms.test;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.reload.DynamicRegistry;

public class TestRegistry extends DynamicRegistry<ITest> {
    public static final TestRegistry INSTANCE = new TestRegistry();

    private TestRegistry() {
        super(BetterFarms.LOGGER, "test", true, true);
    }

    @Override
    protected void registerBuiltinCodecs() {
        this.registerDefaultCodec(BetterFarms.id("test"), Test.CODEC);
    }
}
