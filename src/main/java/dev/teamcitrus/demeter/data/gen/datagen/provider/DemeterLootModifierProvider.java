package dev.teamcitrus.demeter.data.gen.datagen.provider;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.glm.QualityLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class DemeterLootModifierProvider extends GlobalLootModifierProvider {
    public DemeterLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Demeter.MODID);
    }

    @Override
    protected void start() {
        add("quality_wheat",
                new QualityLootModifier(
                        new LootItemCondition[]{
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.WHEAT).build()
                        }
                )
        );
        add("quality_carrots",
                new QualityLootModifier(
                        new LootItemCondition[]{
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.CARROTS).build()
                        }
                )
        );
        add("quality_potato",
                new QualityLootModifier(
                        new LootItemCondition[]{
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.POTATOES).build()
                        }
                )
        );
        add("quality_melon",
                new QualityLootModifier(
                        new LootItemCondition[]{
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.MELON).build()
                        }
                )
        );
        add("quality_beetroots",
                new QualityLootModifier(
                        new LootItemCondition[]{
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.BEETROOTS).build()
                        }
                )
        );
    }
}
