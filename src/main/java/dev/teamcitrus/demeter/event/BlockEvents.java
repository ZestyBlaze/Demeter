package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.CropAttachment;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.world.level.block.CropBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Demeter.MODID)
public class BlockEvents {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof CropBlock) {
            CropAttachment crop = event.getLevel().getChunk(event.getPos()).getData(AttachmentRegistry.CROP);
            crop.addLocation(event.getPos());
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() instanceof CropBlock) {
            CropAttachment crop = event.getLevel().getChunk(event.getPos()).getData(AttachmentRegistry.CROP);
            crop.removeLocation(event.getPos());
        }
    }
}
