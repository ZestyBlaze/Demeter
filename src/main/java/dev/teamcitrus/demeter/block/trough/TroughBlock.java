package dev.teamcitrus.demeter.block.trough;

import dev.teamcitrus.demeter.data.providers.DemeterItemTagsProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class TroughBlock extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(1D, 0D, 1D, 15D, 12D, 15D);
    public static final EnumProperty<FoodType> FOOD_TYPE = EnumProperty.create("food_type", FoodType.class);
    public static final IntegerProperty FOOD_LEVEL = IntegerProperty.create("food_level", 0, 4);

    public TroughBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (!level.getBlockState(pos).getValue(FOOD_LEVEL).equals(4)) {
                TroughBlockEntity entity = (TroughBlockEntity) level.getBlockEntity(pos);

                if (entity != null) {
                    if (stack.is(DemeterItemTagsProvider.TROUGH_FOODS_HAY) && (level.getBlockState(pos).getValue(FOOD_TYPE).equals(FoodType.HAY)
                            || level.getBlockState(pos).getValue(FOOD_TYPE).equals(FoodType.NONE))) {
                        ItemStack remaining = entity.insertItem(stack.copy());
                        stack.setCount(remaining.getCount());
                        level.setBlockAndUpdate(pos, level.getBlockState(pos)
                                .setValue(FOOD_TYPE, FoodType.HAY)
                                .setValue(FOOD_LEVEL, state.getValue(FOOD_LEVEL) + 1)
                        );
                        return InteractionResult.SUCCESS;
                    }
                    if (stack.is(DemeterItemTagsProvider.TROUGH_FOODS_SLOP) && (level.getBlockState(pos).getValue(FOOD_TYPE).equals(FoodType.SLOP)
                            || level.getBlockState(pos).getValue(FOOD_TYPE).equals(FoodType.NONE))) {
                        ItemStack remaining = entity.insertItem(stack.copy());
                        stack.setCount(remaining.getCount());
                        level.setBlockAndUpdate(pos, level.getBlockState(pos)
                                .setValue(FOOD_TYPE, FoodType.SLOP)
                                .setValue(FOOD_LEVEL, state.getValue(FOOD_LEVEL) + 1)
                        );
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
        return new TroughBlockEntity(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FOOD_TYPE, FOOD_LEVEL);
    }

    public enum FoodType implements StringRepresentable {
        NONE, HAY, SLOP;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
