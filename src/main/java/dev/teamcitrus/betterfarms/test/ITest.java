package dev.teamcitrus.betterfarms.test;

import dev.teamcitrus.citruslib.codec.CodecProvider;

public interface ITest extends CodecProvider<ITest> {
    boolean yesOrNo();
    int randomValue();
    String id();
}
