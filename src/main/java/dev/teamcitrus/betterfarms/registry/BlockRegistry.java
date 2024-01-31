package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BetterFarms.MODID);

    public static final DeferredBlock<LiquidBlock> MILK_BLOCK = BLOCKS.register("milk_block", () -> new LiquidBlock(FluidRegistry.MILK, BlockBehaviour.Properties.ofLegacyCopy(Blocks.WATER)));
}
