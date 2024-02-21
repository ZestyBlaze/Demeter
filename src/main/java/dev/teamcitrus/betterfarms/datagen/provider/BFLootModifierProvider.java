package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.QualityLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

public class BFLootModifierProvider extends GlobalLootModifierProvider {
    public BFLootModifierProvider(PackOutput output) {
        super(output, BetterFarms.MODID);
    }

    @Override
    protected void start() {
        add("quality_carrots",
                new QualityLootModifier(
                        new LootItemCondition[] {
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.CARROTS).build()
                        }
                )
        );
    }
}
