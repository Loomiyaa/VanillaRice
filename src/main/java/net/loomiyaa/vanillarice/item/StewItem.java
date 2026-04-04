package net.loomiyaa.vanillarice.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

// this was shamelessly stolen from https://github.com/skniro and their https://github.com/skniro/UsefulFood-Reborn/ mod D:
public class StewItem extends Item {
    public StewItem(Item.Properties settings) {
        super(settings);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        ItemStack itemStack = super.finishUsingItem(stack, world, user);
        if (user instanceof Player playerEntity) {
            if (playerEntity.hasInfiniteMaterials()) {
                return itemStack;
            }
        }

        return new ItemStack(Items.BOWL);
    }
}
