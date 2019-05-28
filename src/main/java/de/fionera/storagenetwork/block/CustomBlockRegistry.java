package de.fionera.storagenetwork.block;

import de.fionera.storagenetwork.storage.BlockStorage;
import de.fionera.storagenetwork.storage.ItemStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockRegistry {

    private final BlockStorage blockStorage = new BlockStorage();
    private final List<CustomBlockTemplate> customBlockDefinitions = new ArrayList<>();

    public CustomBlockRegistry() {
    }

    public void registerCustomBlock(CustomBlockTemplate customBlockTemplate) {
        customBlockDefinitions.add(customBlockTemplate);
    }

    public Listener getListener() {
        return new CustomBlockClickListener(this);
    }

    public boolean isCustomBlockItem(ItemStack itemStack) {
        return customBlockDefinitions.stream().anyMatch(customBlock -> customBlock.isItemForBlock(itemStack));
    }

    public Material placeBlock(Location location, CustomBlock customBlock) {
        blockStorage.setBlockForLocation(location, customBlock);

        return customBlock.getTemplate().getMaterial();
    }

    public void breakBlock(Location location) {
        final CustomBlock customBlock = blockStorage.getBlockForLocation(location);
        if (customBlock == null) {
            return;
        }

        blockStorage.destroyBlock(location);
    }

    public CustomBlock getCustomBlock(Location location) {
        return blockStorage.getBlockForLocation(location);
    }

    public CustomBlock getCustomBlockForItem(ItemStack itemStack) throws IllegalAccessException, InstantiationException {
        final CustomBlockTemplate blockTemplate = customBlockDefinitions.stream().filter(customBlockTemplate -> customBlockTemplate.isItemForBlock(itemStack)).findFirst().orElse(null);

        if (blockTemplate == null) {
            return null;
        }

        return blockTemplate.getBlockClass().newInstance();
    }
}
