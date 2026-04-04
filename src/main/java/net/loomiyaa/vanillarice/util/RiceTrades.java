package net.loomiyaa.vanillarice.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.loomiyaa.vanillarice.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class RiceTrades {
    public static void registerRiceTrades() {

        // FARMER VILLAGERS LEVEL 1
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.FARMER,
                1,
                factories -> {
                    factories.add((world, entity, random) -> new TradeOffer(
                            new TradedItem(ModItems.RICE_GRAIN, 20),
                            new ItemStack(Items.EMERALD, 1),
                            16,
                            5,
                            0.05f
                    ));
                    factories.add((world, entity, random) -> new TradeOffer(
                            new TradedItem(ModItems.RICE_BAG, 2),
                            new ItemStack(Items.EMERALD, 1),
                            16,
                            5,
                            0.05f
                    ));
                    factories.add((world, entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 1),
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
                        new TradeOffers.SellItemFactory(
                            ModItems.RICE_SEEDS,
                            1,
                            1,
                            12,
                            5,
                            0.05f
                    )
                )
        );
    }
}
