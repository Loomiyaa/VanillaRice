package net.loomiyaa.vanillarice.item;

import net.minecraft.world.food.FoodProperties;

public final class ModFoodComponents
{
    public static final FoodProperties RICE_BOWL = new FoodProperties.Builder().
            nutrition(3)
            .saturationModifier(0.6F)
            .build();

    public static final FoodProperties MAKI_SUSHI = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6F)
            .build();

    public static final FoodProperties FRIED_RICE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6F)
            .build();

    public static final FoodProperties MOCHI = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();
}
