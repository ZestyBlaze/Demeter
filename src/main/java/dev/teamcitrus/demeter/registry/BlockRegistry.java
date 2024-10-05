package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.citruslib.block.CitrusHangingSign;
import dev.teamcitrus.citruslib.block.CitrusSign;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.DeadCropBlock;
import dev.teamcitrus.demeter.block.MapleLogBlock;
import dev.teamcitrus.demeter.block.MapleSyrupBlock;
import dev.teamcitrus.demeter.world.tree.DemeterTrees;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("deprecation")
public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Demeter.MODID);
    public static final BlockSetType MAPLE_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("maple"));
    public static final WoodType MAPLE_WOOD_TYPE = WoodType.register(new WoodType("demeter:maple", MAPLE_BLOCK_SET_TYPE));

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log", MapleLogBlock::new);
    public static final DeferredBlock<RotatedPillarBlock> MAPLE_WOOD = BLOCKS.register("maple_wood", MapleLogBlock::new);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", MapleLogBlock::new);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", MapleLogBlock::new);
    public static final DeferredBlock<Block> MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<SaplingBlock> MAPLE_SAPLING = BLOCKS.register("maple_sapling", () -> new SaplingBlock(DemeterTrees.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<StandingSignBlock> MAPLE_SIGN = BLOCKS.register("maple_sign", () -> new CitrusSign.CitrusStandingSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", () -> new CitrusSign.CitrusWallSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = BLOCKS.register("maple_hanging_sign", () -> new CitrusHangingSign.CitrusCeilingHangingSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<WallHangingSignBlock> MAPLE_WALL_HANGING_SIGN = BLOCKS.register("maple_wall_hanging_sign", () -> new CitrusHangingSign.CitrusWallHangingSignBlock(MAPLE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)));
    public static final DeferredBlock<MapleSyrupBlock> MAPLE_SYRUP_BLOCK = BLOCKS.register("maple_syrup_block", () -> new MapleSyrupBlock(BlockBehaviour.Properties.of().forceSolidOff().noOcclusion().mapColor(MapColor.COLOR_ORANGE).speedFactor(0.6F).jumpFactor(0.6F).sound(SoundType.HONEY_BLOCK)));
    public static final DeferredBlock<DeadCropBlock> DEAD_CROP = BLOCKS.register("dead_crop", () -> new DeadCropBlock(BlockBehaviour.Properties.of().sound(SoundType.CROP).instabreak().noCollission()));
}
