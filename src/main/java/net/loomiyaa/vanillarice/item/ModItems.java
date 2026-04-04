package net.loomiyaa.vanillarice.item;

import net.loomiyaa.vanillarice.VanillaRice;
import net.loomiyaa.vanillarice.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import java.util.List;
import java.util.function.Function;

public final class ModItems
{
    public static final Item RICE_SEEDS = register(
            "rice_seeds",
            settings -> new BlockItem(ModBlocks.RICE_CROP, settings),
            new Item.Properties().useItemDescriptionPrefix());

    public static final Item RICE_GRAIN = register(
            "rice_grain",
            Item::new,
            new Item.Properties()
    );

    public static final Item RICE_BOWL = register(
            "rice_bowl",
            StewItem::new,
            new Item.Properties().food(ModFoodComponents.RICE_BOWL)
    );

    public static final Item MAKI_SUSHI = register(
            "maki_sushi",
            Item::new,
            new Item.Properties().food(ModFoodComponents.MAKI_SUSHI)
    );

    public static final Item FRIED_RICE = register(
            "fried_rice",
            StewItem::new,
            new Item.Properties().food(ModFoodComponents.FRIED_RICE)
    );

    public static final Item MOCHI = register(
            "mochi",
            Item::new,
            new Item.Properties()
                    .food(ModFoodComponents.MOCHI, Consumables.defaultFood()
                            .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
                                    new MobEffectInstance(MobEffects.REGENERATION,50,1),
                                    new MobEffectInstance(MobEffects.RESISTANCE,100,1)
                                    ))
                            ).build()
                    )
    );

    public static final Item RICE_BAG = register(
            "rice_bag",
            Item::new,
            new Item.Properties()
    );


    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaRice.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize()
    {
        VanillaRice.LOGGER.debug(VanillaRice.MOD_ID + ": Registering Items...");
    }
}
