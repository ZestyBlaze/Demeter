package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.FluidRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            if(!player.isSecondaryUseActive()) {
                InteractionResult blockUseResult = state.use(level, player, event.getHand(), event.getHitVec());
                if (blockUseResult.consumesAction()) {
                    event.setCanceled(true);
                    event.setCancellationResult(blockUseResult);
                    return;
                }
            }
            if (state.is(Blocks.CAULDRON)) {
                level.setBlockAndUpdate(clickPos, BlockRegistry.MILK_CAULDRON_BLOCK.get().defaultBlockState());
            } else {
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
            }
            player.awardStat(Stats.ITEM_USED.get(event.getItemStack().getItem()));
            if (!player.getAbilities().instabuild) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
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
}
