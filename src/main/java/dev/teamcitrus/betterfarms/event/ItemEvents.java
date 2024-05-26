package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.datagen.provider.BFItemTagsProvider;
import dev.teamcitrus.betterfarms.quality.Quality;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.FluidRegistry;
import dev.teamcitrus.betterfarms.util.QualityUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID)
public class ItemEvents {
    @SubscribeEvent
    public static void onItemUsed(PlayerInteractEvent.RightClickBlock event) {
        if(event.getItemStack().is(Items.MILK_BUCKET)) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            BlockPos clickPos = event.getPos();
            BlockState state = level.getBlockState(clickPos);

            if (state.is(Blocks.CAULDRON)) {
                level.setBlockAndUpdate(clickPos, BlockRegistry.MILK_CAULDRON_BLOCK.get().defaultBlockState());
                level.playSound(player, clickPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (player instanceof ServerPlayer sPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(sPlayer, clickPos, event.getItemStack());
                }
                player.awardStat(Stats.ITEM_USED.get(event.getItemStack().getItem()));
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                return;
            }

            if(!player.isSecondaryUseActive()) {
                InteractionResult blockUseResult = state.use(level, player, event.getHand(), event.getHitVec());
                if (blockUseResult.consumesAction()) {
                    event.setCanceled(true);
                    event.setCancellationResult(blockUseResult);
                    return;
                }
            }

            Direction direction = event.getHitVec().getDirection();
            BlockPos destPos = state.canBeReplaced(FluidRegistry.MILK.get()) ? clickPos : clickPos.relative(direction);
            if (destPos != clickPos && !level.getBlockState(destPos).canBeReplaced(FluidRegistry.MILK.get())) {
                return;
            }
            level.destroyBlock(destPos, true, player);
            level.setBlockAndUpdate(destPos, FluidRegistry.MILK.get().defaultFluidState().createLegacyBlock());
            level.playSound(player, destPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (player instanceof ServerPlayer sPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger(sPlayer, destPos, event.getItemStack());
            }
            player.awardStat(Stats.ITEM_USED.get(event.getItemStack().getItem()));
            if (!player.getAbilities().instabuild) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
        }
        if (event.getItemStack().is(Items.BUCKET) & event.getLevel().getBlockState(event.getPos()).is(BlockRegistry.MILK_CAULDRON_BLOCK.get())) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            BlockPos clickPos = event.getPos();
            BlockState state = level.getBlockState(clickPos);
            if(!player.isSecondaryUseActive()) {
                InteractionResult blockUseResult = state.use(level, player, event.getHand(), event.getHitVec());
                if (blockUseResult.consumesAction()) {
                    event.setCanceled(true);
                    event.setCancellationResult(blockUseResult);
                    return;
                }
            }
            level.setBlockAndUpdate(clickPos, Blocks.CAULDRON.defaultBlockState());
            level.playSound(player, clickPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            player.awardStat(Stats.ITEM_USED.get(event.getItemStack().getItem()));
            if (!player.getAbilities().instabuild) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.MILK_BUCKET));
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
        }
    }

    @SubscribeEvent
    public static void onItemUsed(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() == Items.MILK_BUCKET) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void itemFished(ItemFishedEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            NonNullList<ItemStack> items = event.getDrops();
            items.forEach(QualityUtil::randomiseQuality);
        }
    }

    @SubscribeEvent
    public static void changeTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().is(BFItemTagsProvider.QUALITY_PRODUCTS)) {
            ItemStack itemStack = event.getItemStack();
            if (QualityUtil.getQualityNumber(itemStack) == 0) return;
            Quality quality = QualityUtil.getQuality(itemStack);
            event.getToolTip().add(Component.translatable("item.betterfarms.quality_tooltip", quality.getQualityTooltip()).withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
