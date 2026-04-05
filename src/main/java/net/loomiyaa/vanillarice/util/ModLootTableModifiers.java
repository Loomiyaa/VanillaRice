package net.loomiyaa.vanillarice.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.loomiyaa.vanillarice.item.ModItems;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModLootTableModifiers
{
    private static final float RICE_SEEDS_DROP_CHANCE = 0.125f;

    public static void modifyLootTables()
    {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // to retrieve the biomes, made necessary by 1.21
            HolderLookup.RegistryLookup<Biome> impl = registries.lookupOrThrow(Registries.BIOME);

            // If the loot table is for the short / big grass block, and it is not overridden by a user:
            if (
                    source.isBuiltin() && (
                            Blocks.SHORT_GRASS.getLootTable().get() == key ||
                                    Blocks.TALL_GRASS.getLootTable().get() == key
                            )

            ) {

                // Create a new loot pool that will hold the seeds.
                LootPool.Builder pool = LootPool.lootPool()

                        // Add seeds...
                        .add(LootItem.lootTableItem(ModItems.RICE_SEEDS))

                        // to the desired biomes
                        .when(LocationCheck
                                        .checkLocation(
                                                LocationPredicate.Builder.location().setBiomes(
                                                        HolderSet.direct(
                                                                impl.getOrThrow(Biomes.BAMBOO_JUNGLE),
                                                                impl.getOrThrow(Biomes.RIVER),
                                                                impl.getOrThrow(Biomes.MANGROVE_SWAMP),
                                                                impl.getOrThrow(Biomes.SWAMP)
                                                        )
                                                )
                                        )
                        )
                        // with our chance
                        .when(LootItemRandomChanceCondition.randomChance(RICE_SEEDS_DROP_CHANCE));


                // Add the loot pool to the loot table
                tableBuilder.withPool(pool);
            }
        });
    }
}
