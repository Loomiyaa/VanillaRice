package net.loomiyaa.vanillarice.item;

import net.loomiyaa.vanillarice.VanillaRice;
import net.loomiyaa.vanillarice.block.ModBlocks;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;

public final class ModItems
{
    public static final Item RICE_SEEDS = registerItem(
            "rice_seeds",
            settings -> new BlockItem(ModBlocks.RICE_CROP, settings),
            new Item.Settings().useItemPrefixedTranslationKey());

    public static final Item RICE_GRAIN = registerItem(
            "rice_grain",
            Item::new,
            new Item.Settings()
    );

    public static final Item RICE_BOWL = registerItem(
            "rice_bowl",
            StewItem::new,
            new StewItem.Settings().food(ModFoodComponents.RICE_BOWL)
    );

    public static final Item MAKI_SUSHI = registerItem(
            "maki_sushi",
            Item::new,
            new Item.Settings().food(ModFoodComponents.MAKI_SUSHI)
    );

    public static final Item FRIED_RICE = registerItem(
            "fried_rice",
            StewItem::new,
            new StewItem.Settings().food(ModFoodComponents.FRIED_RICE)
    );

    public static final Item MOCHI = registerItem(
            "mochi",
            Item::new,
            new Item.Settings()
                    .food(ModFoodComponents.MOCHI, ConsumableComponents.food()
                            .consumeEffect(new ApplyEffectsConsumeEffect(List.of(
                                    new StatusEffectInstance(StatusEffects.REGENERATION,50,1),
                                    new StatusEffectInstance(StatusEffects.RESISTANCE,100,1)
                                    ))
                            ).build()
                    )
    );

    public static final Item RICE_BAG = registerItem(
            "rice_bag",
            Item::new,
            new Item.Settings()
    );


    public static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(VanillaRice.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize()
    {
        VanillaRice.LOGGER.debug(VanillaRice.MOD_ID + ": Registering Items...");
    }
}
