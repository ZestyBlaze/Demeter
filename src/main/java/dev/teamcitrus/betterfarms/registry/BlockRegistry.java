package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.block.MilkCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BetterFarms.MODID);

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<Block> MAPLE_PLANKS = BLOCKS.register("maple_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<MilkCauldronBlock> MILK_CAULDRON_BLOCK = BLOCKS.register("milk_cauldron", () -> new MilkCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WATER_CAULDRON)));
    public static final DeferredBlock<LiquidBlock> MILK_BLOCK = BLOCKS.register("milk_block", () -> new LiquidBlock(FluidRegistry.MILK, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
}
