package de.fionera.storagenetwork.blocks.storageviewer;

import de.fionera.storagenetwork.block.CustomBlock;
import de.fionera.storagenetwork.block.CustomBlockTemplate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StorageViewerBlockTemplate implements CustomBlockTemplate {

    @Override
    public Material getMaterial() {
        return Material.RED_WOOL;
    }

    @Override
    public boolean isItemForBlock(ItemStack itemStack) {
        return true;
    }

    @Override
    public Class<? extends CustomBlock> getBlockClass() {
        return StorageViewerBlock.class;
    }
}
