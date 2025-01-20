package dev.teamcitrus.demeter.network;

import dev.teamcitrus.citruslib.network.PayloadProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.duck.AnimalSexes;
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

public record SyncSexPacket(int id, AnimalSexes sex) implements CustomPacketPayload {
    public static final Type<SyncSexPacket> TYPE = new Type<>(Demeter.id("sync_sex_packet"));

    public static final StreamCodec<FriendlyByteBuf, SyncSexPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SyncSexPacket::id,
            AnimalSexes.STREAM_CODEC, SyncSexPacket::sex,
            SyncSexPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<SyncSexPacket> {

        @Override
        public Type<SyncSexPacket> getType() {
            return TYPE;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SyncSexPacket> getCodec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(SyncSexPacket msg, IPayloadContext ctx) {
            Animal animal = (Animal) ctx.player().level().getEntity(msg.id);
            AnimalUtil.getAnimalData(animal).setSex(msg.sex);
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
