package dev.teamcitrus.demeter.block.nest;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.entity.ChickenAttachment;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NestBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape COLLISION = Shapes.box(0.15D, 0D, 0.15D, 0.85D, 0.2D, 0.85D);
    private static final VoxelShape SHAPE = Shapes.box(0.15D, 0D, 0.15D, 0.85D, 0.35D, 0.85D);

    public NestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new NestBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof NestBlockEntity nest) {
            Demeter.LOGGER.warn(nest.getCapability(null).getStackInSlot(0));
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!level.isClientSide()) {
            if (entity instanceof Chicken chicken) {
                chicken.setData(AttachmentRegistry.CHICKEN, new ChickenAttachment(false, BlockPos.ZERO));

                NestBlockEntity nestBlockEntity = (NestBlockEntity) level.getBlockEntity(pos);

                if (nestBlockEntity.tick <= 20) {
                    chicken.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    chicken.setYRot(state.getValue(FACING).toYRot());
                    nestBlockEntity.tick++;

                    nestBlockEntity.insertItem(new ItemStack(Items.EGG));
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
