package dev.teamcitrus.betterfarms.effect;

import dev.teamcitrus.betterfarms.client.renderer.effect.BlessingTooltipRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.function.Consumer;

public class BlessingEffect extends MobEffect {
    public BlessingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00ff00);
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new BlessingTooltipRenderer(effectInstance -> {
            return "Lore Lore more lore, tooltip gotta be long to test something so let's see if this is any good. Wow this is just rambling at this point WOOOOOOOOOOOOOO";
        }));
    }
}
