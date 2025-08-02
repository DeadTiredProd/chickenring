package com.example.chickenring;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent ROTTING_CHICKEN_FOOD = new FoodComponent.Builder()
            .hunger(5)
            .saturationModifier(0.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.8f)
            .meat()
            .build();
}
