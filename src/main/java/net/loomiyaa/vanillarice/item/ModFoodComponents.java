package net.loomiyaa.vanillarice.item;

import net.minecraft.component.type.FoodComponent;

public final class ModFoodComponents
{
    public static final FoodComponent RICE_BOWL = new FoodComponent.Builder().
            nutrition(3)
            .saturationModifier(0.6F)
            .build();

    public static final FoodComponent MAKI_SUSHI = new FoodComponent.Builder()
            .nutrition(5)
            .saturationModifier(0.6F)
            .build();

    public static final FoodComponent FRIED_RICE = new FoodComponent.Builder()
            .nutrition(5)
            .saturationModifier(0.6F)
            .build();

    public static final FoodComponent MOCHI = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();
}
