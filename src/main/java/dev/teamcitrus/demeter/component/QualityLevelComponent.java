package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.demeter.duck.QualityLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public record QualityLevelComponent(QualityLevel level) {
    public static final Codec<QualityLevelComponent> CODEC = RecordCodecBuilder.create(func -> func.group(
            QualityLevel.CODEC.fieldOf("quality").forGetter(QualityLevelComponent::level)
    ).apply(func, QualityLevelComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, QualityLevelComponent> CODEC_STREAM = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(QualityLevel.class), QualityLevelComponent::level,
            QualityLevelComponent::new
    );
}
