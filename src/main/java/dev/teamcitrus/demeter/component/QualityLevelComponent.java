package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public record QualityLevelComponent(QualityLevel level) {
    public static final Codec<QualityLevelComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            QualityLevel.CODEC.fieldOf("level").forGetter(QualityLevelComponent::level)
    ).apply(instance, QualityLevelComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, QualityLevelComponent> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(QualityLevel.class), QualityLevelComponent::level,
            QualityLevelComponent::new
    );
}
