package dev.teamcitrus.demeter.network;

import dev.teamcitrus.citruslib.network.PayloadProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.config.DemeterConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

public record BirthNotificationPacket() implements CustomPacketPayload {
    public static final Type<BirthNotificationPacket> TYPE = new Type<>(Demeter.id("notify_birth"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BirthNotificationPacket> STREAM_CODEC = StreamCodec.unit(
            new BirthNotificationPacket()
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<BirthNotificationPacket> {
        @Override
        public Type<BirthNotificationPacket> getType() {
            return TYPE;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, BirthNotificationPacket> getCodec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(BirthNotificationPacket birthNotificationPacket, IPayloadContext playPayloadContext) {
            if (Minecraft.getInstance().player != null) {
                if (DemeterConfig.animalBirthAlert.get()) {
                    Player player = playPayloadContext.player();
                    player.displayClientMessage(Component.translatable("message.demeter.baby_spawned").withStyle(ChatFormatting.GREEN), true);
                    player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME);
                }
            }
        }

        @Override
        public List<ConnectionProtocol> getSupportedProtocols() {
            return List.of(ConnectionProtocol.PLAY);
        }

        @Override
        public Optional<PacketFlow> getFlow() {
            return Optional.of(PacketFlow.CLIENTBOUND);
        }

        @Override
        public String getVersion() {
            return "1";
        }
    }
}
