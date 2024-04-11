package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.teamcitrus.betterfarms.api.util.AnimalUtil;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.betterfarms.attachment.MilkAttachment;
import dev.teamcitrus.betterfarms.data.AnimalStats;
import dev.teamcitrus.betterfarms.data.AnimalStats.MilkingCodec;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.fml.loading.FMLLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public class AnimalMixin {
    @Unique private final Animal better_Fauna$animal = (Animal)(Object)this;

    @WrapOperation(
            method = "isFood",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean betterFarms$isFood(ItemStack stack, Item item, Operation<Boolean> original) {
        if (!BFStatsManager.newMap.containsKey(better_Fauna$animal.getType())) return original.call(stack, item);
        AnimalStats stats = BFStatsManager.getStats(better_Fauna$animal);
        if (stats.breedingItems().isEmpty()) return original.call(stack, item);
        for (Ingredient ingredient : stats.breedingItems().get()) {
            if (ingredient.test(stack)) {
                AnimalUtil.getAnimalData(better_Fauna$animal).setHasBeenFedToday(true);
                return true;
            }
        }
        return false;
    }

    @Inject(
            method = "mobInteract",
            at = @At("HEAD"),
            cancellable = true
    )
    private void betterFarms$handleNewMilking(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (pPlayer.level().isClientSide) return;
        AnimalStats stats = BFStatsManager.getStats(better_Fauna$animal);
        if (!(BFStatsManager.newMap.containsKey(better_Fauna$animal.getType()) && stats.milking().isPresent())) return;
        MilkingCodec milking = stats.milking().get();
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!stack.is(milking.input())) return;
        if (!AnimalUtil.getGender(better_Fauna$animal).equals(AnimalGenders.FEMALE)) {
            pPlayer.displayClientMessage(Component.translatable("message.betterfarms.milk.fail_gender").withStyle(ChatFormatting.RED), true);
            return;
        }

        MilkAttachment attachment = better_Fauna$animal.getData(AttachmentRegistry.MILK);
        if (attachment.getHasBeenMilked()) {
            pPlayer.displayClientMessage(Component.translatable("message.betterfarms.milk.fail_daily").withStyle(ChatFormatting.RED), true);
            return;
        }

        ItemStack result = ItemUtils.createFilledResult(stack, pPlayer, milking.output().getDefaultInstance());
        ServerPlayer serverPlayer = (ServerPlayer)pPlayer;
        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.COW_MILK), SoundSource.PLAYERS, better_Fauna$animal.getX(), better_Fauna$animal.getY(), better_Fauna$animal.getZ(), 1.0f, 1.0f, 0));
        pPlayer.setItemInHand(pHand, result);
        attachment.setHasBeenMilked(true);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }

    @Inject(
            method = "setInLove",
            at = @At("HEAD"),
            cancellable = true
    )
    private void betterFarms$setInLove(Player pPlayer, CallbackInfo ci) {
        if (!AnimalUtil.isAnimalHappy(better_Fauna$animal) && FMLLoader.isProduction()) ci.cancel();
    }

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z",
                    ordinal = 0
            )
    )
    private boolean betterFarms$checkMateGender(boolean original, Animal otherEntity) {
        return original && AnimalUtil.areOppositeGenders(better_Fauna$animal, otherEntity);
    }
}
