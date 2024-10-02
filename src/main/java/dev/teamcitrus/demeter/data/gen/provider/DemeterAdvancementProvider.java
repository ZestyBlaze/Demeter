package dev.teamcitrus.demeter.data.gen.provider;

import dev.teamcitrus.demeter.advancement.AnimalBrushedTrigger;
import dev.teamcitrus.demeter.advancement.AnimalLoveMaxTrigger;
import dev.teamcitrus.demeter.advancement.AnimalPetTrigger;
import dev.teamcitrus.demeter.advancement.MiraclePotionTrigger;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
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
            ).addCriterion("acquire_crafting", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, "demeter:root");

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
                            ItemRegistry.BRUSH.get(),
                            Component.translatable("advancement.demeter.animal_brushed"),
                            Component.translatable("advancement.demeter.animal_brushed.desc"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
            ).addCriterion("brushed_animal", AnimalBrushedTrigger.TriggerInstance.brushed())
                    .save(saver, "demeter:brushed_animal");
            AdvancementHolder spitefulBrushedAnimal = Advancement.Builder.advancement().parent(brushedAnimal).display(
                    ItemRegistry.BRUSH.get(),
                    Component.translatable("advancement.demeter.spiteful_animal_brushed"),
                    Component.translatable("advancement.demeter.spiteful_animal_brushed.desc"),
                    null,
                    AdvancementType.CHALLENGE,
                    true, true, true
            ).addCriterion("spiteful_brushed_animal", AnimalBrushedTrigger.TriggerInstance.spitefulBrushed())
                    .save(saver, "demeter:spiteful_brushed_animal");
            AdvancementHolder animalLoveMax = Advancement.Builder.advancement().parent(root).display(
                            ItemRegistry.MIRACLE_POTION.get(),
                            Component.translatable("advancement.demeter.animal_love_max"),
                            Component.translatable("advancement.demeter.animal_love_max.desc"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
            ).addCriterion("love_max", AnimalLoveMaxTrigger.TriggerInstance.loveMax())
                    .rewards(AdvancementRewards.Builder.experience(25))
                    .save(saver, "demeter:love_max");
            AdvancementHolder useMiraclePotion = Advancement.Builder.advancement().parent(root).display(
                    ItemRegistry.MIRACLE_POTION.get(),
                    Component.translatable("advancement.demeter.use_miracle_potion"),
                    Component.translatable("advancement.demeter.use_miracle_potion.desc"),
                    null,
                    AdvancementType.TASK,
                    true, true, false
            ).addCriterion("use_miracle_potion", MiraclePotionTrigger.TriggerInstance.usePotion())
                    .save(saver, "demeter:use_miracle_potion");
            AdvancementHolder useMiracleOnFrog = Advancement.Builder.advancement().parent(useMiraclePotion).display(
                    ItemRegistry.MIRACLE_POTION.get(),
                    Component.translatable("advancement.demeter.use_miracle_on_frog"),
                    Component.translatable("advancement.demeter.use_miracle_on_frog.desc"),
                    null,
                    AdvancementType.CHALLENGE,
                    true, true, true
            ).addCriterion("use_miracle_on_frog", MiraclePotionTrigger.TriggerInstance.usePotionOnFrog())
                    .rewards(AdvancementRewards.Builder.experience(30))
                    .save(saver, "demeter:use_miracle_on_frog");
        }
    }
}
