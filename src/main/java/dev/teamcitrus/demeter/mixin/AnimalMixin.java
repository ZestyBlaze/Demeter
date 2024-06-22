package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.teamcitrus.citruslib.reload.DynamicHolder;
import dev.teamcitrus.citruslib.util.ModUtils;
import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.demeter.attachment.MilkAttachment;
import dev.teamcitrus.demeter.data.AnimalStats;
import dev.teamcitrus.demeter.data.IStats;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
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
        if (!AnimalUtil.statsContains(better_Fauna$animal)) return original.call(stack, item);
        DynamicHolder<AnimalStats> stats = AnimalUtil.getStats(better_Fauna$animal);
        if (stats.get().breedingItems().isEmpty()) return original.call(stack, item);
        for (Ingredient ingredient : stats.get().breedingItems().get()) {
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
        DynamicHolder<AnimalStats> stats = AnimalUtil.getStats(better_Fauna$animal);
        if (!(AnimalUtil.statsContains(better_Fauna$animal) && stats.get().milking().isPresent())) return;
        IStats.MilkingCodec milking = stats.get().milking().get();
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!stack.is(milking.input())) return;
        if (!AnimalUtil.getGender(better_Fauna$animal).equals(AnimalGenders.FEMALE)) {
            pPlayer.displayClientMessage(Component.translatable("message.demeter.milk.fail_gender").withStyle(ChatFormatting.RED), true);
            return;
        }

        MilkAttachment attachment = better_Fauna$animal.getData(AttachmentRegistry.MILK);
        if (attachment.getHasBeenMilked()) {
            pPlayer.displayClientMessage(Component.translatable("message.demeter.milk.fail_daily").withStyle(ChatFormatting.RED), true);
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
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;getAge()I",
                    shift = At.Shift.AFTER
            )
    )
    private void betterFarms$mobInteract(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        AnimalUtil.getAnimalData(better_Fauna$animal).setHasBeenFedToday(true);
    }

    @Inject(
            method = "setInLove",
            at = @At("HEAD"),
            cancellable = true
    )
    private void betterFarms$setInLove(Player pPlayer, CallbackInfo ci) {
        if (!AnimalUtil.isAnimalHappy(better_Fauna$animal) && !ModUtils.isDevelopmentEnvironment()) ci.cancel();
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