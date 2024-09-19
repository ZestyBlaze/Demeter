package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record QualityLevelComponent(QualityLevel level) {
    public static final Codec<QualityLevelComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            QualityLevel.CODEC.fieldOf("level").forGetter(QualityLevelComponent::level)
    ).apply(instance, QualityLevelComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, QualityLevelComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(QualityLevel.CODEC), QualityLevelComponent::level,
            QualityLevelComponent::new
    );
}
