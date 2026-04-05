package net.loomiyaa.vanillarice;

import net.fabricmc.api.ModInitializer;
import net.loomiyaa.vanillarice.block.ModBlocks;
import net.loomiyaa.vanillarice.item.ModItemGrouper;
import net.loomiyaa.vanillarice.item.ModItems;
import net.loomiyaa.vanillarice.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaRice implements ModInitializer
{
	public static final String MOD_ID = "vanillarice";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitialize()
	{
		ModItems.initialize();
		ModBlocks.initialize();
		ModItemGrouper.registerItemGroups();

		ModLootTableModifiers.modifyLootTables();

		VanillaRice.LOGGER.debug(VanillaRice.MOD_ID + ": Initialization done!");
	}
}
