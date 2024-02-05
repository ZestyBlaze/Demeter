package dev.teamcitrus.betterfarms.item;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.AnimalStats;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.Optional;

public class CodecTestItem extends Item {
    public CodecTestItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (BFStatsManager.newMap.containsKey(pInteractionTarget.getType()) && !pPlayer.level().isClientSide()) {
            Optional<AnimalStats.MilkingCodec> milkingManager = BFStatsManager.getStats(pInteractionTarget).milking();
            if (milkingManager.isPresent()) {
                AnimalStats.MilkingCodec mm = milkingManager.get();
                BetterFarms.LOGGER.log(Level.INFO,
                        "Values: {}, {}",
                        mm.input(), mm.output()
                        );
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
