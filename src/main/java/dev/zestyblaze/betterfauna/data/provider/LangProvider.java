package dev.zestyblaze.betterfauna.data.provider;

import dev.zestyblaze.betterfauna.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class LangProvider extends FabricLanguageProvider {
    public LangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemRegistry.GENDER_TEST, "Gender Test");
        translationBuilder.add(ItemRegistry.PREGNANCY_TEST, "Pregnancy Test");
        translationBuilder.add(ItemRegistry.TRUFFLE, "Truffle");
        translationBuilder.add("message.betterfauna.milk.fail_gender", "This animal is male and cannot be milked");
        translationBuilder.add("message.betterfauna.milk.fail_daily", "This animal has already been milked today");
    }
}
