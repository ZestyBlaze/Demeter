package dev.teamcitrus.demeter.block;

import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.ItemAbility;

public class MapleLogBlock extends RotatedPillarBlock {
    public static final BooleanProperty SYRUP = BooleanProperty.create("syrup");
    public static final BooleanProperty TAPPED = BooleanProperty.create("tapped");

    public MapleLogBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_ORANGE));
        this.registerDefaultState(this.getStateDefinition().any().setValue(SYRUP, false).setValue(TAPPED, false));
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.is(BlockRegistry.MAPLE_LOG.get())) {
                return BlockRegistry.STRIPPED_MAPLE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if (state.is(BlockRegistry.MAPLE_WOOD.get())) {
                return BlockRegistry.STRIPPED_MAPLE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(SYRUP, TAPPED);
    }
}
