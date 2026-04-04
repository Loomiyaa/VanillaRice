package net.loomiyaa.vanillarice.block;

import net.loomiyaa.vanillarice.VanillaRice;
import net.loomiyaa.vanillarice.block.custom.RiceCropBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.function.Function;

public final class ModBlocks
{
    public static final Block RICE_CROP = register(
            "rice_crop",
            RiceCropBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)
                    .noOcclusion()
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
    );

    private static Block register(String path, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        final Identifier identifier = Identifier.fromNamespaceAndPath(VanillaRice.MOD_ID, path);
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, identifier);

        final Block block = Blocks.register(registryKey, factory, settings);
        Items.registerBlock(block);
        return block;
    }

    public static void initialize()
    {
        VanillaRice.LOGGER.debug(VanillaRice.MOD_ID + ": Registering blocks...");
    }
}
