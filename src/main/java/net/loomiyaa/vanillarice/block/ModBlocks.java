package net.loomiyaa.vanillarice.block;

import net.loomiyaa.vanillarice.VanillaRice;
import net.loomiyaa.vanillarice.block.custom.RiceCropBlock;
import net.minecraft.block.*;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public final class ModBlocks
{
    public static final Block RICE_CROP = register(
            "rice_crop",
            RiceCropBlock::new,
            Block.Settings.copy(Blocks.WHEAT)
                    .nonOpaque()
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
    );

    private static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final Identifier identifier = Identifier.of(VanillaRice.MOD_ID, path);
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);
        return block;
    }

    public static void initialize()
    {
        VanillaRice.LOGGER.debug(VanillaRice.MOD_ID + ": Registering blocks...");
    }
}
