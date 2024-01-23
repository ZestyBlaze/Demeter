package dev.zestyblaze.betterfauna.data.provider.lang;

import dev.zestyblaze.betterfauna.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class EnUsProvider extends FabricLanguageProvider {
    public EnUsProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        Set<Item> items = new HashSet<>(ItemRegistry.ITEMS);

        items.forEach(i -> {
            String name = i.getDescriptionId().replaceFirst("item\\.betterfauna\\.", "");
            name = toTitleCase(name, "_");
            translationBuilder.add(i.getDescriptionId(), name);
        });

        translationBuilder.add("itemGroup.betterfauna", "Better Fauna");
        translationBuilder.add("message.betterfauna.milk.fail_gender", "This animal is male and cannot be milked");
        translationBuilder.add("message.betterfauna.milk.fail_daily", "This animal has already been milked today");
    }

    /**
     * A method grabbed from Lodestone Lib by Egshels and SammySemiColon to capitalise names and replace [regex] with a space
     * <a href="https://github.com/LodestarMC/Lodestone/blob/65c087683cc1ac2698c4365d1026b00979234443/src/main/java/team/lodestar/lodestone/helpers/DataHelper.java#L36C30-L36C30">...</a>
     */
    private String toTitleCase(String givenString, String regex) {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }
}
