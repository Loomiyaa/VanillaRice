package net.loomiyaa.vanillarice.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ModItemGrouper
{
    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.accept(ModItems.RICE_SEEDS);
            entries.accept(ModItems.RICE_GRAIN);
            entries.accept(ModItems.RICE_BAG);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(ModItems.RICE_BOWL);
            entries.accept(ModItems.FRIED_RICE);
            entries.accept(ModItems.MAKI_SUSHI);
            entries.accept(ModItems.MOCHI);
        });
    }
}
