package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.block.MapleLogBlock;
import dev.teamcitrus.betterfarms.block.MilkCauldronBlock;
import dev.teamcitrus.citruslib.registry.WoodSetRegistry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BetterFarms.MODID);
    public static final BlockSetType MAPLE = BlockSetType.register(new BlockSetType("maple"));
    public static final WoodType MAPLE_WOOD_TYPE = WoodType.register(new WoodType("maple", MAPLE));

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log", MapleLogBlock::new);
    public static final DeferredBlock<RotatedPillarBlock> MAPLE_WOOD = BLOCKS.register("maple_wood", MapleLogBlock::new);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", MapleLogBlock::new);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", MapleLogBlock::new);
    //public static final DeferredBlock<Block> MAPLE_PLANKS = BLOCKS.register("maple_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    //public static final DeferredBlock<StairBlock> MAPLE_STAIRS = BLOCKS.register("maple_stairs", () -> new StairBlock(() -> MAPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    //public static final DeferredBlock<SlabBlock> MAPLE_SLAB = BLOCKS.register("maple_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    //public static final DeferredBlock<FenceBlock> MAPLE_FENCE = BLOCKS.register("maple_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    //public static final DeferredBlock<FenceGateBlock> MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", () -> new FenceGateBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    //public static final DeferredBlock<DoorBlock> MAPLE_DOOR = BLOCKS.register("maple_door", () -> new DoorBlock(MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    //public static final DeferredBlock<TrapDoorBlock> MAPLE_TRAPDOOR = BLOCKS.register("maple_trapdoor", () -> new TrapDoorBlock(MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    //public static final DeferredBlock<PressurePlateBlock> MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", () -> new PressurePlateBlock(MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    //public static final DeferredBlock<ButtonBlock> MAPLE_BUTTON = BLOCKS.register("maple_button", () -> new ButtonBlock(MAPLE, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<Block> MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<MilkCauldronBlock> MILK_CAULDRON_BLOCK = BLOCKS.register("milk_cauldron", () -> new MilkCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WATER_CAULDRON)));
    public static final DeferredBlock<LiquidBlock> MILK_BLOCK = BLOCKS.register("milk_block", () -> new LiquidBlock(FluidRegistry.MILK, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static void init(IEventBus bus) {
        WoodSetRegistry.registerWoodSet(BLOCKS, "maple");
        BLOCKS.register(bus);
    }
}
