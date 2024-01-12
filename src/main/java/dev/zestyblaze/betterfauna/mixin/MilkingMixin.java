package dev.zestyblaze.betterfauna.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.zestyblaze.betterfauna.component.animal.AnimalComponent;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
import dev.zestyblaze.betterfauna.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Cow.class, Goat.class})
public class MilkingMixin {
    @ModifyExpressionValue(
            method = "mobInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean betterFauna$canBeMilked(boolean original, Player player, InteractionHand hand) {
        if (original && ((Animal)(Object)this).getComponent(ComponentRegistry.ANIMAL).getGender().equals(AnimalComponent.Gender.MALE)) {
            player.displayClientMessage(Component.translatable("message.betterfauna.milk.fail_gender").withStyle(ChatFormatting.RED), true);
            return false;
        } else if (((Animal)(Object)this).getComponent(ComponentRegistry.MILK).getBeenMilked() && original) {
            player.displayClientMessage(Component.translatable("message.betterfauna.milk.fail_daily").withStyle(ChatFormatting.RED), true);
            return false;
        }
        return original && AnimalUtil.isAnimalHappy((Animal)(Object)this);
    }

    @Inject(
            method = "mobInteract",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER)
    )
    private void betterFauna$setBeenMilked(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ((Animal)(Object)this).getComponent(ComponentRegistry.MILK).setBeenMilked(true);
    }
}
