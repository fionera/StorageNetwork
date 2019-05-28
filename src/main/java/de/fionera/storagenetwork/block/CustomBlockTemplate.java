package de.fionera.storagenetwork.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface CustomBlockTemplate {
    Material getMaterial();

    boolean isItemForBlock(ItemStack itemStack);

    Class<? extends CustomBlock> getBlockClass();
}
