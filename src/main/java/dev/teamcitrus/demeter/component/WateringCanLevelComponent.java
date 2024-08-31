package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WateringCanLevelComponent(WateringCanLevel level) {
    public static final Codec<WateringCanLevelComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WateringCanLevel.CODEC.fieldOf("level").forGetter(WateringCanLevelComponent::level)
    ).apply(instance, WateringCanLevelComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, WateringCanLevelComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(WateringCanLevel.CODEC), WateringCanLevelComponent::level,
            WateringCanLevelComponent::new
    );
}
