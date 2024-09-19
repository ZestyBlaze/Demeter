package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.citruslib.tab.ITabFiller;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.component.QualityLevelComponent;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class WateringCanItem extends CitrusItem implements ITabFiller {
    public WateringCanItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(((float)getFluidInTankFromStack(stack) / getTankCapacityFromStack(stack)) * 13F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return getTankCapacityFromStack(stack) > 0 ? 0x006DD9 : 0x555555;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (attemptToFill(level, player, stack)) return InteractionResultHolder.success(stack);
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
            if (getFluidInTankFromStack(context.getItemInHand()) >= 20) {
                QualityLevelComponent component = context.getItemInHand().get(ComponentRegistry.QUALITY_LEVEL.get());
                if (state.is(Blocks.FARMLAND) && state.getValue(BlockStateProperties.MOISTURE) < 7) {
                    dispenseWater(context.getItemInHand(), (ServerPlayer) context.getPlayer(), level, context.getClickedPos(), context.getHorizontalDirection(), component.level());
                    drainContainer(context.getItemInHand(), 20);
                    return InteractionResult.SUCCESS;
                } else if (state.getBlock() instanceof CropBlock
                        && level.getBlockState(context.getClickedPos().below()).getBlock().equals(Blocks.FARMLAND)
                        && level.getBlockState(context.getClickedPos().below()).getValue(BlockStateProperties.MOISTURE) < 7
                ) {
                    dispenseWater(context.getItemInHand(), (ServerPlayer) context.getPlayer(), level, context.getClickedPos().below(), context.getHorizontalDirection(), component.level());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.has(ComponentRegistry.QUALITY_LEVEL.get())) {
            QualityLevelComponent component = stack.get(ComponentRegistry.QUALITY_LEVEL.get());
            return Component.translatable(getDescriptionId(stack), StringUtils.capitalize(component.level().name().toLowerCase(Locale.ROOT)));
        }
        return Component.translatable(getDescriptionId(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(ComponentRegistry.QUALITY_LEVEL.get())) {
            tooltipComponents.add(
                    Component.translatable("item.demeter.watering_can.uses", Component.literal(
                                            calculateRemainingUses(stack) + "/" + calculateTotalUses(stack))
                                    .withStyle(ChatFormatting.AQUA))
                            .withStyle(ChatFormatting.DARK_GRAY)
            );
        } else {
            tooltipComponents.add(
                    Component.translatable("item.demeter.watering_can.fail")
                            .withStyle(ChatFormatting.RED)
            );
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, BuildCreativeModeTabContentsEvent event) {
        Arrays.stream(QualityLevel.values()).sorted(Comparator.reverseOrder()).forEach(level -> {
            ItemStack stack = new ItemStack(this);
            setLevel(stack, level);
            fillContainer(stack, getTankCapacityFromStack(stack));
            event.insertAfter(ItemRegistry.BUTTER.toStack(), stack,
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        });
    }

    public static void setLevel(ItemStack stack, QualityLevel level) {
        stack.set(ComponentRegistry.QUALITY_LEVEL.get(), new QualityLevelComponent(level));
    }

    private boolean attemptToFill(Level level, Player player, ItemStack stack) {
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos blockpos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getDirection();
        BlockPos blockpos1 = blockpos.relative(direction);
        if (blockHitResult.getType() == BlockHitResult.Type.BLOCK) {
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, stack)) {
                BlockState state = level.getBlockState(blockpos);
                if (state.getFluidState().is(FluidTags.WATER)) {
                    return fillContainer(stack, getTankCapacityFromStack(stack));
                }
            }
        }

        return false;
    }

    private void dispenseWater(ItemStack stack, ServerPlayer player, Level level, BlockPos originalPos, Direction facing, QualityLevel qualityLevel) {
        if (qualityLevel.equals(QualityLevel.IRON)) {
            for (int i = 0; i < 3; i++) {
                BlockState newState = level.getBlockState(originalPos.relative(facing, i));
                if (newState.hasProperty(BlockStateProperties.MOISTURE)) {
                    level.setBlockAndUpdate(originalPos.relative(facing, i),
                            newState.setValue(BlockStateProperties.MOISTURE, 7));
                }
            }
        } else if (qualityLevel.equals(QualityLevel.NETHERITE)) {
            for (int i = 0; i < 3; i++) {
                BlockPos pos = originalPos.relative(facing, i);
                BlockState centreState = level.getBlockState(pos);
                BlockState leftState = level.getBlockState(pos.relative(facing.getCounterClockWise()));
                BlockState rightState = level.getBlockState(pos.relative(facing.getClockWise()));

                if (centreState.hasProperty(BlockStateProperties.MOISTURE)) {
                    level.setBlockAndUpdate(pos,
                            centreState.setValue(BlockStateProperties.MOISTURE, 7));
                }
                if (leftState.hasProperty(BlockStateProperties.MOISTURE)) {
                    level.setBlockAndUpdate(pos.relative(facing.getCounterClockWise()),
                            leftState.setValue(BlockStateProperties.MOISTURE, 7));
                }
                if (rightState.hasProperty(BlockStateProperties.MOISTURE)) {
                    level.setBlockAndUpdate(pos.relative(facing.getClockWise()),
                            rightState.setValue(BlockStateProperties.MOISTURE, 7));
                }
            }
        } else {
            level.setBlockAndUpdate(originalPos, level.getBlockState(originalPos).setValue(BlockStateProperties.MOISTURE, 7));
        }
        drainContainer(stack, 20);
        player.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BUCKET_EMPTY), SoundSource.PLAYERS, originalPos.getX(), originalPos.getY(), originalPos.getZ(), 1.0f, level.random.nextFloat(), 0));
    }

    private int calculateRemainingUses(ItemStack stack) {
        return getFluidInTankFromStack(stack) / 20;
    }

    private int calculateTotalUses(ItemStack stack) {
        return getTankCapacityFromStack(stack) / 20;
    }

    private int getFluidInTankFromStack(ItemStack stack) {
        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return handler != null ? handler.getFluidInTank(0).getAmount() : 0;
    }

    private int getTankCapacityFromStack(ItemStack stack) {
        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return handler != null ? handler.getTankCapacity(0) : 0;
    }

    private boolean fillContainer(ItemStack stack, int maxWater) {
        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return handler != null && handler.fill(new FluidStack(Fluids.WATER, maxWater), IFluidHandler.FluidAction.EXECUTE) > 0;
    }

    private void drainContainer(ItemStack stack, int amount) {
        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if (handler != null)
            handler.drain(amount, IFluidHandler.FluidAction.EXECUTE);
    }
}