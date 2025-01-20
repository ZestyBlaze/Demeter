package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.duck.AnimalSexes;
import dev.teamcitrus.demeter.network.SyncSexPacket;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Demeter.MODID)
public class PlayerEvents {
    @SubscribeEvent
    public static void startTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Animal animal) {
            AnimalSexes sex = AnimalUtil.getSex(animal);
            PacketDistributor.sendToPlayersTrackingEntity(animal, new SyncSexPacket(animal.getId(), sex));
        }
    }
}
