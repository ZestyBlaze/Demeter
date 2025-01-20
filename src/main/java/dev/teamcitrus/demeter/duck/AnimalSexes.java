package dev.teamcitrus.demeter.duck;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.Locale;

public enum AnimalSexes implements StringRepresentable {
    MALE, FEMALE;

    public static final Codec<AnimalSexes> CODEC = StringRepresentable.fromValues(AnimalSexes::values);
    public static final StreamCodec<FriendlyByteBuf, AnimalSexes> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(AnimalSexes.class);

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
