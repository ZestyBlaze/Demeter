package dev.teamcitrus.demeter.network;

import dev.teamcitrus.citruslib.network.PayloadHelper;
import dev.teamcitrus.citruslib.network.PayloadProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.config.DemeterConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.List;
import java.util.Optional;

public class BirthNotificationPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = Demeter.id("notify_birth");

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {}

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static class Provider implements PayloadProvider<BirthNotificationPacket, PlayPayloadContext> {
        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public BirthNotificationPacket read(FriendlyByteBuf friendlyByteBuf) {
            return new BirthNotificationPacket();
        }

        @Override
        public void handle(BirthNotificationPacket birthNotificationPacket, PlayPayloadContext playPayloadContext) {
            PayloadHelper.handle(() -> {
                if (Minecraft.getInstance().player != null) {
                    if (DemeterConfig.animalBirthAlert.get()) {
                        playPayloadContext.player().ifPresent(player -> {
                            player.displayClientMessage(Component.translatable("message.demeter.baby_spawned").withStyle(ChatFormatting.GREEN), true);
                            player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME);
                        });
                    }
                }
            }, playPayloadContext);
        }

        @Override
        public List<ConnectionProtocol> getSupportedProtocols() {
            return List.of(ConnectionProtocol.PLAY);
        }

        @Override
        public Optional<PacketFlow> getFlow() {
            return Optional.of(PacketFlow.CLIENTBOUND);
        }
    }
}
