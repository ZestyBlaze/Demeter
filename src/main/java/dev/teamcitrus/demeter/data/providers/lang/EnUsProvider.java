package dev.teamcitrus.demeter.data.providers.lang;

import dev.teamcitrus.citruslib.datagen.CitrusLanguageProvider;
import dev.teamcitrus.citruslib.util.JavaUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.curios.CuriosCompat;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.data.providers.DemeterBiomesTagProvider;
import dev.teamcitrus.demeter.data.providers.DemeterBlockTagsProvider;
import dev.teamcitrus.demeter.data.providers.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.data.providers.experimental.DemeterEBiomeProvider;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.FluidTypeRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class EnUsProvider extends CitrusLanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, Demeter.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        QualityLevel[] qualities = QualityLevel.values();

        add("advancement.demeter.root", "Demeter");
        add("advancement.demeter.root.desc", "The Introduction to the Farming Overhaul Mod!");
        add("advancement.demeter.acquire_food_pouch", "Optimal Feeding");
        add("advancement.demeter.acquire_food_pouch.desc", "Acquire a Food Pouch to store feed for animals!");
        add("advancement.demeter.animal_brushed", "Neat and Tidy");
        add("advancement.demeter.animal_brushed.desc", "Brush an Animal!");
        add("advancement.demeter.animal_pet", "Easy Does It");
        add("advancement.demeter.animal_pet.desc", "Pet an Animal!");
        add("advancement.demeter.animal_love_max", "My Favourite Animal");
        add("advancement.demeter.animal_love_max.desc", "Get an Animal to max love!");
        add("advancement.demeter.obtain_maple_syrup", "Tapped");
        add("advancement.demeter.obtain_maple_syrup.desc", "Obtain a Maple Syrup Bottle from tapping a tree");
        add("advancement.demeter.spiteful_animal_brushed", "Malicious Brushing");
        add("advancement.demeter.spiteful_animal_brushed.desc", "Brush your Animal with Ill Intent");
        add("advancement.demeter.use_miracle_potion", "It's a Miracle!");
        add("advancement.demeter.use_miracle_potion.desc", "Use a Miracle Potion on an Animal");
        add("advancement.demeter.use_miracle_potion_on_frog", "Chemicals in the Water");
        add("advancement.demeter.use_miracle_potion_on_frog.desc", "'The Freaking Frogs are Gay!'");
        add("config.demeter.animal_config", "Animal Config");
        add("config.demeter.animal_config.old_age", "Dies of Old Age");
        add("config.demeter.animal_config.hunger", "Dies of Hunger");
        add("config.demeter.animal_config.hunger_days", "Days before Hunger Death");
        add("config.demeter.animal_config.product_days", "Days before Products Stop");
        add("config.demeter.animal_config.pregnancy_down_period", "Pregnancy Down Period");
        add("config.demeter.animal_config.wool_growth_days", "Days for Wool Growth");
        add("config.demeter.crop_config", "Crop Config");
        add("config.demeter.crop_config.crops_wilt", "Crops Wilt");
        add("config.demeter.crop_config.crop_wilt_days", "Days to Wilt");
        add("config.demeter.love_config", "Love Config");
        add("config.demeter.love_config.spawn_love_value", "Initial Spawn Love Value");
        add("config.demeter.love_config.min_happiness", "Animal Happiness Minimum");
        add("config.demeter.love_config.pet_love", "Love from Petting");
        add("config.demeter.love_config.brush_love", "Love from Brushing");
        add("config.demeter.love_config.feed_love", "Love from Feeding");
        add("config.demeter.farmland_config", "Farmland Config");
        add("config.demeter.farmland_config.morning_dirt_chance", "Morning Dirt Chance");
        add("config.demeter.farmland_config.irrigation_enabled", "Irrigation Enabled");
        add("config.demeter.enchantment_config", "Enchantment Config");
        add("config.demeter.enchantment_config.comfort_bonus", "Comfort Bonus");
        add("config.demeter.enchantment_config.spite_effect", "Spite Effect");
        add("config.demeter.enchantment_config.spite_loss", "Spite Love Loss");
        add("config.demeter.quality_config", "Quality Config");
        add("config.demeter.quality_config.copper_chance", "Copper Quality Chance");
        add("config.demeter.quality_config.iron_chance", "Iron Quality Chance");
        add("config.demeter.quality_config.netherite_chance", "Netherite Quality Chance");
        add("config.demeter.hud_config", "HUD Config");
        add("config.demeter.hud_config.clock_type", "Clock Type");
        add("config.demeter.hud_config.display_time_hud", "Display Time on HUD");
        add("config.demeter.hud_config.require_clock_time", "Require Clock for Time");
        add("config.demeter.qol_config", "QOL Config");
        add("config.demeter.qol_config.birth_alert", "Animal Birth Alert");
        add("enchantment.demeter.barber", "Barber");
        add("enchantment.demeter.comfort", "Comfort");
        add("enchantment.demeter.spite", "Curse of Spite");
        add("error.demeter.maxhighermin", "'maxChildrenPerBirth' is higher than 'maxChildrenPerBirth' for entity type `%s` resulting in the birth failing!");
        add("error.demeter.namesloadfail", "Error while loading names");
        add("item.demeter.food_pouch.tooltip", "Can hold a mix of animal foods");
        add("item.demeter.milk_bottle.desc", "Removes one random negative potion effect");
        add("item.demeter.pocket_watch.error", "Since \"requireClockItemForTime\" is currently not true, this item is not required in gameplay");
        add("item.demeter.quality_tooltip", "Quality: %s");
        add("item.demeter.strawberries", "Strawberries");
        add("item.demeter.watering_can", "Watering Can");
        add("item.demeter.watering_can.fail", "Something has gone wrong, this can has no data!");
        add("item.demeter.watering_can.uses", "Uses: %s");
        add("itemGroup.demeter", "Demeter");
        add("message.demeter.animal_petted", "You pet your %s for today");
        add("message.demeter.baby_spawned", "One of your animals has given birth!");
        add("message.demeter.birth.fail", "An issue occurred with a birth, please check the logs for more information");
        add("message.demeter.brush.fail_daily", "This animal has already been brushed today!");
        add("message.demeter.milk.fail_daily", "This animal has already been milked today!");
        add("message.demeter.milk.fail_sex", "This animal is male and cannot be milked!");
        add("stat.demeter.animals_fed", "Times Animals Fed");
        add("stat.demeter.times_pet", "Times Animals Pet");
        add(DemeterItemTagsProvider.TOOLS_ANIMAL_BRUSH, "Animal Brushes");
        add(DemeterItemTagsProvider.CLOCKS, "Clocks");
        add(DemeterItemTagsProvider.TROUGH_FOODS_HAY, "Hay Trough Foods");
        add(DemeterItemTagsProvider.MAPLE_LOGS, "Maple Logs");
        add(DemeterItemTagsProvider.QUALITY_PRODUCTS, "Quality Products");
        add(DemeterItemTagsProvider.TROUGH_FOODS_SLOP, "Slop Trough Foods");
        add(DemeterItemTagsProvider.TROUGH_FOODS, "Trough Foods");
        add(DemeterBlockTagsProvider.MAPLE_LOGS, "Maple Logs");
        add(DemeterEBiomeProvider.HAS_BAMBOO_SHOOTS, "Has Bamboo Shoots");
        add(DemeterBiomesTagProvider.HAS_MAPLE_TREES, "Has Maple Trees");

        FluidTypeRegistry.FLUIDS.getEntries().forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("fluid_type\\.demeter\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });

        generateBlockLanguageKeys(BlockRegistry.BLOCKS);
        generateItemLanguageKeys(ItemRegistry.ITEMS, List.of(ItemRegistry.WATERING_CAN.get()));
        generateItemLanguageKeys(CuriosCompat.Items.CURIOS_ITEMS);
        Arrays.stream(qualities).forEach(quality -> add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }
}
