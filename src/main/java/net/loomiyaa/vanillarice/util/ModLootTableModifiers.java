package net.loomiyaa.vanillarice.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.loomiyaa.vanillarice.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class ModLootTableModifiers
{
    private static final float RICE_SEEDS_DROP_CHANCE = 0.125f;

    public static void modifyLootTables()
    {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // to retrieve the biomes, made necessary by 1.21
            RegistryWrapper.Impl<Biome> impl = registries.getOrThrow(RegistryKeys.BIOME);

            // If the loot table is for the short / big grass block, and it is not overridden by a user:
            if (
                    source.isBuiltin() && (
                            Blocks.SHORT_GRASS.getLootTableKey().get() == key ||
                                    Blocks.TALL_GRASS.getLootTableKey().get() == key
                            )

            ) {

                // Create a new loot pool that will hold the seeds.
                LootPool.Builder pool = LootPool.builder()

                        // Add seeds...
                        .with(ItemEntry.builder(ModItems.RICE_SEEDS))

                        // to the desired biomes
                        .conditionally(LocationCheckLootCondition
                                        .builder(
                                                LocationPredicate.Builder.create().biome(
                                                        RegistryEntryList.of(
                                                                impl.getOrThrow(BiomeKeys.BAMBOO_JUNGLE),
                                                                impl.getOrThrow(BiomeKeys.RIVER),
                                                                impl.getOrThrow(BiomeKeys.MANGROVE_SWAMP),
                                                                impl.getOrThrow(BiomeKeys.SWAMP)
                                                        )
                                                )
                                        )
                        )
                        // with our chance
                        .conditionally(RandomChanceLootCondition.builder(RICE_SEEDS_DROP_CHANCE));


                // Add the loot pool to the loot table
                tableBuilder.pool(pool);
            }
        });
    }
}
