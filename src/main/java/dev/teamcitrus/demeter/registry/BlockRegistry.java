package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.DeadCropBlock;
import dev.teamcitrus.demeter.block.MapleLogBlock;
import dev.teamcitrus.demeter.block.MapleSyrupBlock;
import dev.teamcitrus.demeter.block.experimental.BambooShootsBlock;
import dev.teamcitrus.demeter.block.experimental.CounterBlock;
import dev.teamcitrus.demeter.block.experimental.StrawberryBushBlock;
import dev.teamcitrus.demeter.block.trough.TroughBlock;
import dev.teamcitrus.demeter.world.DemeterConfiguredFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Demeter.MODID);
    public static final BlockSetType MAPLE_SET_TYPE = BlockSetType.register(new BlockSetType("maple"));
    public static final WoodType MAPLE_WOOD_TYPE = WoodType.register(new WoodType("demeter:maple", MAPLE_SET_TYPE));
    public static final TreeGrower MAPLE_TREE_GROWER = new TreeGrower("maple", Optional.empty(), Optional.of(DemeterConfiguredFeature.MAPLE_TREES), Optional.empty());

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log", () -> new MapleLogBlock("maple_log"));
    public static final DeferredBlock<RotatedPillarBlock> MAPLE_WOOD = BLOCKS.register("maple_wood", () -> new MapleLogBlock("maple_wood"));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", () -> new MapleLogBlock("stripped_maple_log"));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", () -> new MapleLogBlock("stripped_maple_wood"));
    public static final DeferredBlock<Block> MAPLE_PLANKS = BLOCKS.registerSimpleBlock("maple_planks");
    public static final DeferredBlock<Block> MAPLE_STAIRS = BLOCKS.register("maple_stairs", () -> new StairBlock(MAPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).setId(createID("maple_stairs"))));
    public static final DeferredBlock<Block> MAPLE_SLAB = BLOCKS.register("maple_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).setId(createID("maple_slab"))));
    public static final DeferredBlock<Block> MAPLE_FENCE = BLOCKS.register("maple_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).setId(createID("maple_fence"))));
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", () -> new FenceGateBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).setId(createID("maple_fence_gate"))));
    public static final DeferredBlock<Block> MAPLE_DOOR = BLOCKS.register("maple_door", () -> new DoorBlock(MAPLE_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).setId(createID("maple_door"))));
    public static final DeferredBlock<Block> MAPLE_TRAPDOOR = BLOCKS.register("maple_trapdoor", () -> new TrapDoorBlock(MAPLE_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).setId(createID("maple_trapdoor"))));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", () -> new PressurePlateBlock(MAPLE_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).setId(createID("maple_pressure_plate"))));
    public static final DeferredBlock<Block> MAPLE_BUTTON = BLOCKS.register("maple_button", () -> new ButtonBlock(MAPLE_SET_TYPE, 30 ,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).setId(createID("maple_button"))));
    public static final DeferredBlock<Block> MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).setId(createID("maple_leaves"))));
    public static final DeferredBlock<SaplingBlock> MAPLE_SAPLING = BLOCKS.register("maple_sapling", () -> new SaplingBlock(MAPLE_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).setId(createID("maple_sapling"))));
    public static final DeferredBlock<FlowerPotBlock> POTTED_MAPLE_SAPLING = BLOCKS.register("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).setId(createID("potted_maple_sapling"))));
    public static final DeferredBlock<StandingSignBlock> MAPLE_SIGN = BLOCKS.register("maple_sign", () -> new StandingSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).setId(createID("maple_sign"))));
    public static final DeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", () -> new WallSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).setId(createID("maple_wall_sign"))));
    public static final DeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = BLOCKS.register("maple_hanging_sign", () -> new CeilingHangingSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).setId(createID("maple_hanging_sign"))));
    public static final DeferredBlock<WallHangingSignBlock> MAPLE_WALL_HANGING_SIGN = BLOCKS.register("maple_wall_hanging_sign", () -> new WallHangingSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).setId(createID("maple_wall_hanging_sign"))));
    public static final DeferredBlock<MapleSyrupBlock> MAPLE_SYRUP_BLOCK = BLOCKS.register("maple_syrup_block", () -> new MapleSyrupBlock(BlockBehaviour.Properties.of().forceSolidOff().noOcclusion().mapColor(MapColor.COLOR_ORANGE).speedFactor(0.6F).jumpFactor(0.6F).sound(SoundType.HONEY_BLOCK).setId(createID("maple_syrup_block"))));
    public static final DeferredBlock<TroughBlock> TROUGH = BLOCKS.register("trough", () -> new TroughBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().setId(createID("trough"))));
    public static final DeferredBlock<DeadCropBlock> DEAD_CROP = BLOCKS.register("dead_crop", () -> new DeadCropBlock(BlockBehaviour.Properties.of().sound(SoundType.CROP).instabreak().noCollission().setId(createID("dead_crop"))));

    public static final DeferredBlock<Block> MILK_CAN = BLOCKS.registerSimpleBlock("milk_can");
    public static final DeferredBlock<Block> FEEDING_TRAY = BLOCKS.registerSimpleBlock("feeding_tray");
    public static final DeferredBlock<Block> NEST = BLOCKS.registerSimpleBlock("nest");

    //Flagged Blocks
    public static final DeferredBlock<Block> BAMBOO_SHOOTS = BLOCKS.register("bamboo_shoots", () -> new BambooShootsBlock(Block.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).requiredFeatures(Demeter.EXPERIMENTAL).setId(createID("bamboo_shoots"))));
    public static final DeferredBlock<LiquidBlock> WINE = BLOCKS.register("wine", () -> new LiquidBlock(FluidRegistry.WINE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).requiredFeatures(Demeter.EXPERIMENTAL).setId(createID("wine"))));
    public static final DeferredBlock<Block> COUNTER = BLOCKS.register("counter", () -> new CounterBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> STRAWBERRY_BUSH = BLOCKS.register("strawberry_bush", () -> new StrawberryBushBlock(BlockBehaviour.Properties.of().instabreak().noCollission().noOcclusion().ignitedByLava().jumpFactor(-0.99f).pushReaction(PushReaction.DESTROY).requiredFeatures(Demeter.EXPERIMENTAL).randomTicks().sound(SoundType.SWEET_BERRY_BUSH).setId(createID("strawberry_bush"))));

    public static ResourceKey<Block> createID(String id) {
        return ResourceKey.create(Registries.BLOCK, Demeter.id(id));
    }
}
