package net.loomiyaa.vanillarice.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ModItemGrouper
{
    public static void registerItemGroups() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS)
                .register((creativeTab -> {
                    creativeTab.accept(ModItems.RICE_SEEDS);
                }));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register((creativeTab -> {
                    creativeTab.accept(ModItems.RICE_GRAIN);
                    creativeTab.accept(ModItems.RICE_BAG);
                }));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((creativeTab -> {
                    creativeTab.accept(ModItems.RICE_BOWL);
                    creativeTab.accept(ModItems.FRIED_RICE);
                    creativeTab.accept(ModItems.MAKI_SUSHI);
                    creativeTab.accept(ModItems.MOCHI);
                }));
    }
}
