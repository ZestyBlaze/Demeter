package dev.teamcitrus.demeter.data.crops;

import dev.teamcitrus.citruslib.codec.CodecProvider;

public interface ICrop extends CodecProvider<ICrop> {
    int daysToGrow();
}
