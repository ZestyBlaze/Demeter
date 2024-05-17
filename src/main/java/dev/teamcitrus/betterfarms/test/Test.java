package dev.teamcitrus.betterfarms.test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Test(boolean yesOrNo, int randomValue, String id) implements ITest {
    public static Codec<Test> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.BOOL.fieldOf("yesOrNo").forGetter(Test::yesOrNo),
                    Codec.INT.fieldOf("randomValue").forGetter(Test::randomValue),
                    Codec.STRING.fieldOf("id").forGetter(Test::id)
            ).apply(inst, Test::new));

    @Override
    public Codec<? extends ITest> getCodec() {
        return CODEC;
    }
}
