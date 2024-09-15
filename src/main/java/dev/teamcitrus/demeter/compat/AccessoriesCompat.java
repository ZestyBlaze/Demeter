package dev.teamcitrus.demeter.compat;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.item.DemeterAccessoryItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AccessoriesCompat {
    public static void init(IEventBus bus) {
        Items.ACCESSORIES_ITEMS.register(bus);
    }

    public static class Items {
        public static final DeferredRegister.Items ACCESSORIES_ITEMS = DeferredRegister.createItems(Demeter.MODID);
        public static final DeferredItem<Item> BREEDING_CHARM = ACCESSORIES_ITEMS.register("breeding_charm", DemeterAccessoryItem::new);
    }

    public static class Internal {
        public static void addItemsToTab(CreativeModeTab.Output out) {
            Items.ACCESSORIES_ITEMS.getEntries().forEach(item -> {
                out.accept(item.get());
            });
        }
    }
}
