package dev.zestyblaze.betterfauna.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.zestyblaze.betterfauna.BetterFauna;
import dev.zestyblaze.betterfauna.component.animal.AnimalComponent;
import dev.zestyblaze.betterfauna.component.animal.IAnimalComponent;
import dev.zestyblaze.betterfauna.component.milk.IMilkComponent;
import dev.zestyblaze.betterfauna.component.milk.MilkComponent;
import dev.zestyblaze.betterfauna.component.pregnancy.IPregnancyComponent;
import dev.zestyblaze.betterfauna.component.pregnancy.PregnancyComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;

public class ComponentRegistry implements EntityComponentInitializer {
    public static final ComponentKey<IAnimalComponent> ANIMAL = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(BetterFauna.MODID, "animal"), IAnimalComponent.class);
    public static final ComponentKey<IMilkComponent> MILK = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(BetterFauna.MODID, "milk"), IMilkComponent.class);
    public static final ComponentKey<IPregnancyComponent> PREGNANCY = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(BetterFauna.MODID, "pregnancy"), IPregnancyComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(Animal.class, ANIMAL, AnimalComponent::new);
        registry.registerFor(Cow.class, MILK, MilkComponent::new);
        registry.registerFor(Goat.class, MILK, MilkComponent::new);

        registry.registerFor(Cow.class, PREGNANCY, animal -> new PregnancyComponent(animal, 9));
        registry.registerFor(Pig.class, PREGNANCY, animal -> new PregnancyComponent(animal, 4));
        registry.registerFor(Sheep.class, PREGNANCY, animal -> new PregnancyComponent(animal, 5));
        registry.registerFor(Horse.class, PREGNANCY, animal -> new PregnancyComponent(animal, 24));
        registry.registerFor(Llama.class, PREGNANCY, animal -> new PregnancyComponent(animal, 11));
        registry.registerFor(Rabbit.class, PREGNANCY, animal -> new PregnancyComponent(animal, 4));
        registry.registerFor(Cat.class, PREGNANCY, animal -> new PregnancyComponent(animal, 5));
        registry.registerFor(Wolf.class, PREGNANCY, animal -> new PregnancyComponent(animal, 5));
        registry.registerFor(Donkey.class, PREGNANCY, animal -> new PregnancyComponent(animal, 24));
        registry.registerFor(Parrot.class, PREGNANCY, animal -> new PregnancyComponent(animal, 4));
        registry.registerFor(MushroomCow.class, PREGNANCY, animal -> new PregnancyComponent(animal, 11));
    }
}
