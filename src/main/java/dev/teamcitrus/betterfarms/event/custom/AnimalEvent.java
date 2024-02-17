package dev.teamcitrus.betterfarms.event.custom;

import net.minecraft.world.entity.animal.Animal;
import net.neoforged.neoforge.event.entity.EntityEvent;

//TODO: For future events
public class AnimalEvent extends EntityEvent {
    private final Animal animal;

    public AnimalEvent(Animal entity) {
        super(entity);
        this.animal = entity;
    }

    @Override
    public Animal getEntity() {
        return animal;
    }
}
