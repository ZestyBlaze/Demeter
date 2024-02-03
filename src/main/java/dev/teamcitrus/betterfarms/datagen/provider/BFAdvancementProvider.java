package dev.teamcitrus.betterfarms.datagen.provider;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BFAdvancementProvider extends AdvancementProvider {
    public BFAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new BFAdvancementGenerator()));
    }

    private static class BFAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement().display(
                            Items.LEATHER,
                            Component.translatable("advancement.betterfarms.root"),
                            Component.translatable("advancement.betterfarms.root.desc"),
                            new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementType.TASK,
                            true, false, false)
                    .addCriterion("join_overworld", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, "betterfarms:root");
        }
    }
}
