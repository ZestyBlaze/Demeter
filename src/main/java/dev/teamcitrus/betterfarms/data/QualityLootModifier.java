package dev.teamcitrus.betterfarms.data;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.betterfarms.api.quality.Quality;
import dev.teamcitrus.betterfarms.api.util.QualityUtil;
import dev.teamcitrus.betterfarms.datagen.provider.BFItemTagProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.function.Supplier;

public class QualityLootModifier extends LootModifier {
    public static final Supplier<Codec<QualityLootModifier>> CODEC = Suppliers.memoize(
            () -> RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance).apply(instance, QualityLootModifier::new))
    );

    public QualityLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.forEach(stack -> {
            if (!stack.is(BFItemTagProvider.QUALITY_PRODUCTS)) return;
            QualityUtil.writeQualityToTag(stack, Quality.NETHERITE);
        });
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
