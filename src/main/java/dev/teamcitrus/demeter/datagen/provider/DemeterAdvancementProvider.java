package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.advancement.AnimalBrushedTrigger;
import dev.teamcitrus.demeter.advancement.AnimalLoveMaxTrigger;
import dev.teamcitrus.demeter.advancement.AnimalPetTrigger;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
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

public class DemeterAdvancementProvider extends AdvancementProvider {
    public DemeterAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new BFAdvancementGenerator()));
    }

    private static class BFAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement().display(
                            Items.LEATHER,
                            Component.translatable("advancement.demeter.root"),
                            Component.translatable("advancement.demeter.root.desc"),
                            ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementType.TASK,
                            false, false, false
            ).addCriterion("start_game", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, "demeter:root");
            AdvancementHolder milkPlaced = Advancement.Builder.advancement().parent(root).display(
                            Items.MILK_BUCKET,
                            Component.translatable("advancement.demeter.milk_placed"),
                            Component.translatable("advancement.demeter.milk_placed.desc"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
            ).addCriterion("milk_placed", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(BlockRegistry.MILK_BLOCK.get()))
                    .save(saver, "demeter:milk_placed");


            AdvancementHolder petAnimal = Advancement.Builder.advancement().parent(root).display(
                            Items.COW_SPAWN_EGG,
                            Component.translatable("advancement.demeter.animal_pet"),
                            Component.translatable("advancement.demeter.animal_pet.desc"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
            ).addCriterion("pet_animal", AnimalPetTrigger.PetTrigger.pet())
                    .save(saver, "demeter:pet_animal");
            AdvancementHolder brushedAnimal = Advancement.Builder.advancement().parent(petAnimal).display(
                            ItemRegistry.BRUSH.asItem(),
                            Component.translatable("advancement.demeter.animal_brushed"),
                            Component.translatable("advancement.demeter.animal_brushed.desc"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
            ).addCriterion("brushed_animal", AnimalBrushedTrigger.TriggerInstance.brushed())
                    .save(saver, "demeter:brushed_animal");
            AdvancementHolder animalLoveMax = Advancement.Builder.advancement().parent(root).display(
                            Items.PINK_STAINED_GLASS,
                            Component.translatable("advancement.demeter.animal_love_max"),
                            Component.translatable("advancement.demeter.animal_love_max.desc"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
            ).addCriterion("love_max", AnimalLoveMaxTrigger.TriggerInstance.loveMax())
                    .rewards(AdvancementRewards.Builder.experience(25))
                    .save(saver, "demeter:love_max");
        }
    }
}
