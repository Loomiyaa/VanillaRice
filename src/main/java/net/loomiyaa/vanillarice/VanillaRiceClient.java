package net.loomiyaa.vanillarice;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.loomiyaa.vanillarice.block.ModBlocks;
import net.minecraft.client.render.BlockRenderLayer;

public class VanillaRiceClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        BlockRenderLayerMap.putBlock(ModBlocks.RICE_CROP, BlockRenderLayer.CUTOUT);
    }
}
