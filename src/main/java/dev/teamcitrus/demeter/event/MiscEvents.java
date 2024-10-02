package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.loaders.NameLoader;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = Demeter.MODID)
public class MiscEvents {
    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addRecipe(
                Ingredient.of(PotionContents.createItemStack(Items.POTION, Potions.REGENERATION)),
                Ingredient.of(Items.RABBIT_FOOT),
                new ItemStack(ItemRegistry.MIRACLE_POTION.get())
        );
    }

    @SubscribeEvent
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new NameLoader());
    }
}
