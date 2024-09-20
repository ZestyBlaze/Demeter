package dev.teamcitrus.demeter.datagen.provider.lang;

import dev.teamcitrus.citruslib.datagen.CitrusLanguageProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.AccessoriesCompat;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.datagen.provider.DemeterBiomeTagProvider;
import dev.teamcitrus.demeter.datagen.provider.DemeterBlockTagsProvider;
import dev.teamcitrus.demeter.datagen.provider.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.BlockRegistry;
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

        add("advancement.demeter.root", "BetterFarms");
        add("advancement.demeter.root.desc", "The Introduction to the Farming Overhaul!");
        add("advancement.demeter.milk_placed", "Splish Splash, Milk Bath");
        add("advancement.demeter.milk_placed.desc", "Empty out your Milk Bucket!");
        add("advancement.demeter.animal_brushed", "Neat and Tidy");
        add("advancement.demeter.animal_brushed.desc", "Brush an Animal!");
        add("advancement.demeter.animal_pet", "Easy Does It");
        add("advancement.demeter.animal_pet.desc", "Pet an Animal!");
        add("advancement.demeter.animal_love_max", "My Favourite Animal");
        add("advancement.demeter.animal_love_max.desc", "Get an Animal to max love!");
        add("advancement.demeter.spiteful_animal_brushed", "Malicious Brushing");
        add("advancement.demeter.spiteful_animal_brushed.desc", "Brush your Animal with Ill Intent");
        add("advancement.demeter.use_miracle_potion", "It's a Miracle!");
        add("advancement.demeter.use_miracle_potion.desc", "Use a Miracle Potion on an Animal");
        add("advancement.demeter.use_miracle_on_frog", "Chemicals in the Water");
        add("advancement.demeter.use_miracle_on_frog.desc", "'The Freaking Frogs are Gay!'");
        add("enchantment.demeter.barber", "Barber");
        add("enchantment.demeter.comfort", "Comfort");
        add("enchantment.demeter.spite", "Curse of Spite");
        add("error.demeter.maxhighermin", "'maxChildrenPerBirth' is higher than 'maxChildrenPerBirth' resulting in the birth failing!");
        add("error.demeter.namesloadfail", "Error while loading names");
        add("item.demeter.quality_tooltip", "Quality: %s");
        add("item.demeter.milk_bottle.desc", "Removes one random negative potion effect");
        add("item.demeter.watering_can", "%s Watering Can");
        add("item.demeter.watering_can.fail", "Something has gone wrong, this can has no data!");
        add("item.demeter.watering_can.uses", "Uses: %s");
        add("itemGroup.demeter", "Demeter");
        add("message.demeter.animal_petted", "You pet your %s for today");
        add("message.demeter.baby_spawned", "One of your animals has given birth!");
        add("message.demeter.brush.fail_daily", "This animal has already been brushed today!");
        add("message.demeter.milk.fail_daily", "This animal has already been milked today!");
        add("message.demeter.milk.fail_gender", "This animal is male and cannot be milked!");
        add(DemeterItemTagsProvider.TOOLS_ANIMAL_BRUSH, "Animal Brushes");
        add(DemeterItemTagsProvider.ACCESSORIES_CHARM, "Charm");
        add(DemeterItemTagsProvider.MAPLE_LOGS, "Maple Logs");
        add(DemeterItemTagsProvider.QUALITY_PRODUCTS, "Quality Products");
        add(DemeterBlockTagsProvider.MAPLE_LOGS, "Maple Logs");
        add(DemeterBiomeTagProvider.HAS_MAPLE_TREES, "Has Maple Trees");

        generateBlockLanguageKeys(BlockRegistry.BLOCKS);
        generateItemLanguageKeys(ItemRegistry.ITEMS, List.of(ItemRegistry.WATERING_CAN.get()));
        generateItemLanguageKeys(AccessoriesCompat.Items.ACCESSORIES_ITEMS);
        Arrays.stream(qualities).forEach(quality -> add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }
}
