package dev.teamcitrus.demeter.block.experimental;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.experimental.tile.CounterTileEntity;
import dev.teamcitrus.demeter.duck.Section;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static dev.teamcitrus.demeter.registry.BlockRegistry.createID;

public class CounterBlock extends AbstractCookwareBlock implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape NORTH = Block.box(0D, 0D, 1D, 16D, 16D, 16D);
    private static final VoxelShape SOUTH = Block.box(0D, 1D, 0D, 16D, 16D, 15D);
    private static final VoxelShape WEST = Block.box(1D, 1D, 0D, 16D, 16D, 16D);
    private static final VoxelShape EAST = Block.box(0D, 1D, 0D, 15D, 16D, 16D);

    public CounterBlock(Properties properties) {
        super(properties.strength(1.5f).sound(SoundType.WOOD).requiredFeatures(Demeter.EXPERIMENTAL).setId(createID("counter")));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(Section.SECTION, Section.STRAIGHT));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
            default -> super.getShape(state, getter, pos, ctx);
        };
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(Items.IRON_SWORD)) {
            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            CounterTileEntity tileEntity = (CounterTileEntity) level.getBlockEntity(pos);
            tileEntity.activate();
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CounterTileEntity(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(Section.SECTION, FACING);
    }
}
