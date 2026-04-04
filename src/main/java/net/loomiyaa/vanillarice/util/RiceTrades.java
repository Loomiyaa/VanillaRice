package net.loomiyaa.vanillarice.util;

//import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.loomiyaa.vanillarice.item.ModItems;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
//import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class RiceTrades {
    public static void registerRiceTrades() {
/*
        // FARMER VILLAGERS LEVEL 1
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.FARMER,
                1,
                factories -> {
                    factories.add((world, entity, random) -> new MerchantOffer(
                            new ItemCost(ModItems.RICE_GRAIN, 20),
                            new ItemStack(Items.EMERALD, 1),
                            16,
                            5,
                            0.05f
                    ));
                    factories.add((world, entity, random) -> new MerchantOffer(
                            new ItemCost(ModItems.RICE_BAG, 2),
                            new ItemStack(Items.EMERALD, 1),
                            16,
                            5,
                            0.05f
                    ));
                    factories.add((world, entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 1),
                            new ItemStack(ModItems.RICE_BOWL, 4),
                            6,
                            5,
                            0.05f
                    ));
                }
        );

        // WANDERING TRADER (Seeds only)
        TradeOfferHelper.registerWanderingTraderOffers(
                factories -> factories.addOffersToPool(
                        TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
                        new VillagerTrades.ItemsForEmeralds(
                            ModItems.RICE_SEEDS,
                            1,
                            1,
                            12,
                            5,
                            0.05f
                    )
                )
        );*/
    }
}
