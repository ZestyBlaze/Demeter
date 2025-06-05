package dev.teamcitrus.demeter.compat.curios;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class CuriosCompat {
    public static void init(IEventBus bus) {
        Items.CURIOS_ITEMS.register(bus);
    }

    public static class Items {
        public static final DeferredRegister.Items CURIOS_ITEMS = DeferredRegister.createItems(Demeter.MODID);
        public static final DeferredItem<Item> BREEDING_CHARM = CURIOS_ITEMS.register("breeding_charm", () -> new DemeterCuriosItem("breeding_charm"));
        public static final DeferredItem<Item> POCKET_WATCH = CURIOS_ITEMS.register("pocket_watch", PocketWatchItem::new);
    }

    public static void addItemsToTab(CreativeModeTab.Output out) {
        Items.CURIOS_ITEMS.getEntries().forEach(item -> {
            out.accept(item.get());
        });
    }

    public static boolean isWearing(LivingEntity entity, Item item) {
        Optional<ICuriosItemHandler> inventory = CuriosApi.getCuriosInventory(entity);
        return inventory.map(handler -> handler.isEquipped(item)).orElse(false);
    }
}
