package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FilledComponent(boolean filled) {
    public static final Codec<FilledComponent> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.BOOL.fieldOf("filled").forGetter(FilledComponent::filled)
    ).apply(func, FilledComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FilledComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, FilledComponent::filled,
            FilledComponent::new
    );
}
