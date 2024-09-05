package dev.teamcitrus.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record IrrigationLevelComponent(IrrigationLevel level) {
    public static final Codec<IrrigationLevelComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IrrigationLevel.CODEC.fieldOf("level").forGetter(IrrigationLevelComponent::level)
    ).apply(instance, IrrigationLevelComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, IrrigationLevelComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(IrrigationLevel.CODEC), IrrigationLevelComponent::level,
            IrrigationLevelComponent::new
    );
}
