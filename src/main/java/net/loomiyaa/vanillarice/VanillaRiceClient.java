package net.loomiyaa.vanillarice;

import net.fabricmc.api.ClientModInitializer;
import net.loomiyaa.vanillarice.block.ModBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class VanillaRiceClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        //BlockRenderL.putBlock(ModBlocks.RICE_CROP, ChunkSectionLayer.CUTOUT);
    }
}
