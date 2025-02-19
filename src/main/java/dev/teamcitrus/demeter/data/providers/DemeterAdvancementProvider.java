package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
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
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DemeterAdvancementProvider extends AdvancementProvider {
    public DemeterAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new BFAdvancementGenerator()));
    }

    private static class BFAdvancementGenerator implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            AdvancementHolder root = Advancement.Builder.advancement().display(
                            Items.LEATHER,
                            Component.translatable("advancement.demeter.root"),
                            Component.translatable("advancement.demeter.root.desc"),
                            ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementType.TASK,
                            false, false, false
            ).addCriterion("acquire_crafting", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, id("root"));

            AdvancementHolder obtainMapleSyrup = Advancement.Builder.advancement().parent(root).display(
                    ItemRegistry.MAPLE_SYRUP_BOTTLE,
                    Component.translatable("advancement.demeter.obtain_maple_syrup"),
                    Component.translatable("advancement.demeter.obtain_maple_syrup.desc"),
                    null,
                    AdvancementType.TASK,
                    true, true, false
            ).addCriterion("obtain_maple_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MAPLE_SYRUP_BOTTLE))
                    .save(saver, id("obtain_maple_syrup"));

            AdvancementHolder petAnimal = Advancement.Builder.advancement().parent(root).display(
                            Items.COW_SPAWN_EGG,
                            Component.translatable("advancement.demeter.animal_pet"),
                            Component.translatable("advancement.demeter.animal_pet.desc"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
            ).addCriterion("pet_animal", AnimalPetTrigger.PetTrigger.pet())
                    .save(saver, id("pet_animal"));

            AdvancementHolder brushedAnimal = Advancement.Builder.advancement().parent(petAnimal).display(
                            ItemRegistry.ANIMAL_BRUSH.get(),
                            Component.translatable("advancement.demeter.animal_brushed"),
                            Component.translatable("advancement.demeter.animal_brushed.desc"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
            ).addCriterion("brushed_animal", AnimalBrushedTrigger.TriggerInstance.brushed())
                    .save(saver, id("brushed_animal"));

            AdvancementHolder spitefulBrushedAnimal = Advancement.Builder.advancement().parent(brushedAnimal).display(
                    ItemRegistry.ANIMAL_BRUSH.get(),
                    Component.translatable("advancement.demeter.spiteful_animal_brushed"),
                    Component.translatable("advancement.demeter.spiteful_animal_brushed.desc"),
                    null,
                    AdvancementType.CHALLENGE,
                    true, true, true
            ).addCriterion("spiteful_brushed_animal", AnimalBrushedTrigger.TriggerInstance.spitefulBrushed())
                    .save(saver, id("spiteful_brushed_animal"));

            AdvancementHolder animalLoveMax = Advancement.Builder.advancement().parent(petAnimal).display(
                            ItemRegistry.MIRACLE_POTION.get(),
                            Component.translatable("advancement.demeter.animal_love_max"),
                            Component.translatable("advancement.demeter.animal_love_max.desc"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
            ).addCriterion("love_max", AnimalLoveMaxTrigger.TriggerInstance.loveMax())
                    .rewards(AdvancementRewards.Builder.experience(25))
                    .save(saver, id("love_max"));

            AdvancementHolder useMiraclePotion = Advancement.Builder.advancement().parent(root).display(
                    ItemRegistry.MIRACLE_POTION.get(),
                    Component.translatable("advancement.demeter.use_miracle_potion"),
                    Component.translatable("advancement.demeter.use_miracle_potion.desc"),
                    null,
                    AdvancementType.TASK,
                    true, true, false
            ).addCriterion("use_miracle_potion", MiraclePotionTrigger.TriggerInstance.usePotion())
                    .save(saver, id("use_miracle_potion"));

            AdvancementHolder useMiraclePotionOnFrog = Advancement.Builder.advancement().parent(useMiraclePotion).display(
                    ItemRegistry.MIRACLE_POTION.get(),
                    Component.translatable("advancement.demeter.use_miracle_potion_on_frog"),
                    Component.translatable("advancement.demeter.use_miracle_potion_on_frog.desc"),
                    null,
                    AdvancementType.CHALLENGE,
                    true, true, true
            ).addCriterion("use_miracle_potion_on_frog", MiraclePotionTrigger.TriggerInstance.usePotionOnFrog())
                    .rewards(AdvancementRewards.Builder.experience(30))
                    .save(saver, id("use_miracle_potion_on_frog"));

            AdvancementHolder acquireFoodPouch = Advancement.Builder.advancement().parent(root).display(
                    ItemRegistry.FOOD_POUCH.get(),
                    Component.translatable("advancement.demeter.acquire_food_pouch"),
                    Component.translatable("advancement.demeter.acquire_food_pouch.desc"),
                    null,
                    AdvancementType.TASK,
                    true, true, false
            ).addCriterion("acquire_food_pouch", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FOOD_POUCH.get()))
                    .save(saver, id("acquire_food_pouch"));
        }

        private String id(String value) {
            return Demeter.id(value).toString();
        }
    }
}
