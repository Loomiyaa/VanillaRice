package net.loomiyaa.vanillarice.item;

import net.loomiyaa.vanillarice.VanillaRice;
import net.loomiyaa.vanillarice.block.ModBlocks;
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
    public static final Item RICE_SEEDS = registerItem(
            "rice_seeds",
            settings -> new BlockItem(ModBlocks.RICE_CROP, settings),
            new Item.Properties().useItemDescriptionPrefix());

    public static final Item RICE_GRAIN = registerItem(
            "rice_grain",
            Item::new,
            new Item.Properties()
    );

    public static final Item RICE_BOWL = registerItem(
            "rice_bowl",
            StewItem::new,
            new Item.Properties().food(ModFoodComponents.RICE_BOWL)
    );

    public static final Item MAKI_SUSHI = registerItem(
            "maki_sushi",
            Item::new,
            new Item.Properties().food(ModFoodComponents.MAKI_SUSHI)
    );

    public static final Item FRIED_RICE = registerItem(
            "fried_rice",
            StewItem::new,
            new Item.Properties().food(ModFoodComponents.FRIED_RICE)
    );

    public static final Item MOCHI = registerItem(
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

    public static final Item RICE_BAG = registerItem(
            "rice_bag",
            Item::new,
            new Item.Properties()
    );


    public static Item registerItem(String path, Function<Item.Properties, Item> factory, Item.Properties settings) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaRice.MOD_ID, path));
        return Items.registerItem(registryKey, factory, settings);
    }

    public static void initialize()
    {
        VanillaRice.LOGGER.debug(VanillaRice.MOD_ID + ": Registering Items...");
    }
}
