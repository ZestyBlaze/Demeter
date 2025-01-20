package dev.teamcitrus.demeter.network;

import dev.teamcitrus.citruslib.network.PayloadProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

public record SyncGenderPacket(int id, AnimalAttachment.AnimalGenders gender) implements CustomPacketPayload {
    public static final Type<SyncGenderPacket> TYPE = new Type<>(Demeter.id("sync_gender_packet"));

    public static final StreamCodec<FriendlyByteBuf, SyncGenderPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SyncGenderPacket::id,
            AnimalAttachment.AnimalGenders.STREAM_CODEC, SyncGenderPacket::gender,
            SyncGenderPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<SyncGenderPacket> {

        @Override
        public Type<SyncGenderPacket> getType() {
            return TYPE;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SyncGenderPacket> getCodec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(SyncGenderPacket msg, IPayloadContext ctx) {
            Animal animal = (Animal) ctx.player().level().getEntity(msg.id);
            AnimalUtil.getAnimalData(animal).setGender(msg.gender);
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
