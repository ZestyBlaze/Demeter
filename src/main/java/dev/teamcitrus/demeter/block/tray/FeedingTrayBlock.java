package dev.teamcitrus.demeter.block.tray;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class FeedingTrayBlock extends Block implements EntityBlock {
    public static final IntegerProperty FOOD_LEVEL = IntegerProperty.create("food_level", 0, 2);
    private static final VoxelShape SHAPE = Shapes.box(0.0D, 0D, 0.0D, 1.0D, 0.075D, 1.0D);

    public FeedingTrayBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (!level.getBlockState(pos).getValue(FOOD_LEVEL).equals(2)) {
                FeedingTrayBlockEntity entity = (FeedingTrayBlockEntity) level.getBlockEntity(pos);

                if (entity != null) {
                    if (stack.is(Tags.Items.SEEDS)) {
                        ItemStack remaining = entity.insertItem(stack.copy());
                        stack.setCount(remaining.getCount());
                        level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(FOOD_LEVEL, state.getValue(FOOD_LEVEL) + 1));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FeedingTrayBlockEntity(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FOOD_LEVEL);
    }
}
