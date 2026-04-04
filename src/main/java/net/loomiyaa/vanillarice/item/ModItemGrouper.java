package net.loomiyaa.vanillarice.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItemGrouper
{
    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(ModItems.RICE_SEEDS);
            entries.add(ModItems.RICE_GRAIN);
            entries.add(ModItems.RICE_BAG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(ModItems.RICE_BOWL);
            entries.add(ModItems.FRIED_RICE);
            entries.add(ModItems.MAKI_SUSHI);
            entries.add(ModItems.MOCHI);
        });
    }
}
