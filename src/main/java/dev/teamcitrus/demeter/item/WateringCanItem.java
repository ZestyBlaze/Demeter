package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.citruslib.tab.ITabFiller;
import dev.teamcitrus.demeter.component.IrrigationLevel;
import dev.teamcitrus.demeter.component.IrrigationLevelComponent;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.network.chat.Component;
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
import java.util.*;

public class WateringCanItem extends CitrusItem implements ITabFiller {
    private static final int WATER_CAN_DRAIN_PER_USE = 20;

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
        else return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
            if (state.is(Blocks.FARMLAND) && getFluidInTankFromStack(context.getItemInHand()) >= 500) {
                level.setBlockAndUpdate(context.getClickedPos(), state.setValue(BlockStateProperties.MOISTURE, 7));
                drainContainer(context.getItemInHand(), WATER_CAN_DRAIN_PER_USE);
                return InteractionResult.SUCCESS;
            } else if (state.getBlock() instanceof CropBlock) {
                BlockState state2 = context.getLevel().getBlockState(context.getClickedPos().below());
                level.setBlockAndUpdate(context.getClickedPos().below(), state2.setValue(BlockStateProperties.MOISTURE, 7));
                drainContainer(context.getItemInHand(), WATER_CAN_DRAIN_PER_USE);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public Component getName(ItemStack stack) {
        IrrigationLevelComponent component = stack.get(ComponentRegistry.WATERING_CAN_LEVEL.get());
        return Component.translatable(getDescriptionId(stack), StringUtils.capitalize(component.level().name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                Component.translatable("item.demeter.watering_can.uses")
        );
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, BuildCreativeModeTabContentsEvent event) {
        Arrays.stream(IrrigationLevel.values()).sorted(Comparator.reverseOrder()).forEach(level -> {
            ItemStack stack = new ItemStack(this);
            setLevel(stack, level);
            fillContainer(stack, getTankCapacityFromStack(stack));
            event.insertAfter(new ItemStack(ItemRegistry.BUTTER.get()), stack,
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        });
    }

    public static void setLevel(ItemStack stack, IrrigationLevel level) {
        stack.set(ComponentRegistry.WATERING_CAN_LEVEL.get(), new IrrigationLevelComponent(level));
    }

    private boolean attemptToFill(Level level, Player player, ItemStack stack) {
        BlockHitResult rayTraceResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (rayTraceResult.getType() == BlockHitResult.Type.BLOCK) {
            BlockState state = level.getBlockState(rayTraceResult.getBlockPos());
            if (state.getFluidState().is(FluidTags.WATER)) {
                return fillContainer(stack, getTankCapacityFromStack(stack));
            }
        }

        return false;
    }

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