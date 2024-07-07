package dev.teamcitrus.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.demeter.quality.Quality;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record QualityDataComponent(Quality quality) {
    public static final Codec<QualityDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Quality.CODEC.fieldOf("quality").forGetter(o -> o.quality)
    ).apply(instance, QualityDataComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, QualityDataComponent> STREAM_CODEC = StreamCodec.unit(
            new QualityDataComponent(Quality.NONE)
    );
}
