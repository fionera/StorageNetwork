package de.fionera.storagenetwork;

import com.logisticscraft.logisticsapi.LogisticsApi;
import com.logisticscraft.logisticsapi.api.BlockManager;
import com.logisticscraft.logisticsapi.block.BasicBlockFactory;
import com.logisticscraft.logisticsapi.persistence.PersistenceStorage;
import de.fionera.storagenetwork.block.CustomBlockClickListener;
import de.fionera.storagenetwork.block.CustomBlockRegistry;
import de.fionera.storagenetwork.blocks.storageviewer.StorageViewerBlockTemplate;
import de.fionera.storagenetwork.blocks.storageviewer.StorageViewerMenu;
import de.fionera.storagenetwork.menu.CustomMenuRegistry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.jnlp.PersistenceService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class StorageNetwork extends JavaPlugin {

    public static final ArrayList<String> STORAGE_VIEWER_LORE = new ArrayList<>();
    public static final String STORAGE_VIEWER_NAME = "storageViewer";

    static {
        STORAGE_VIEWER_LORE.add("Add to a Network to");
        STORAGE_VIEWER_LORE.add("display the Items in it");
    }

    private static CustomMenuRegistry customMenuRegistry = new CustomMenuRegistry();
    private static CustomBlockRegistry customBlockRegistry = new CustomBlockRegistry();

    public static CustomMenuRegistry getCustomMenuRegistry() {
        return customMenuRegistry;
    }

    public static CustomBlockRegistry getCustomBlockRegistry() {
        return customBlockRegistry;
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(customBlockRegistry.getListener(), this);
        this.getServer().getPluginManager().registerEvents(customMenuRegistry.getListener(), this);

//        customBlockRegistry.registerCustomBlock(new StorageViewerBlockTemplate());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = (Player) sender;

        final ItemStack itemStack = new ItemStack(Material.STONE);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return false;
        }

        itemMeta.setDisplayName("Storage Viewer");
        itemMeta.setLore(STORAGE_VIEWER_LORE);

        itemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(itemStack);

        return true;
    }
}