package de.fionera.storagenetwork.menu;

import net.minecraft.server.v1_13_R2.IInventory;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class CustomMenuListener implements Listener {
    private final CustomMenuRegistry customMenuRegistry;

    public CustomMenuListener(CustomMenuRegistry customMenuRegistry) {
        this.customMenuRegistry = customMenuRegistry;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        final CustomMenu customMenu = getCustomMenu(inventoryClickEvent.getInventory());
        if (customMenu == null) {
            return;
        }

        inventoryClickEvent.setCancelled(true);

        if (customMenu.getClickListener() != null && customMenu.getClickListener().getRight()) {
            customMenu.getClickListener().getLeft().accept(inventoryClickEvent);
        }

        final MenuItem menuItem = customMenu.getMenuItem(inventoryClickEvent.getSlot());
        if (menuItem != null && menuItem.getClickListener() != null) {
            menuItem.getClickListener().accept(inventoryClickEvent);
        }

        if (customMenu.getClickListener() != null && !customMenu.getClickListener().getRight()) {
            customMenu.getClickListener().getLeft().accept(inventoryClickEvent);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryCloseEvent(InventoryCloseEvent inventoryCloseEvent) {
        final CustomMenu customMenu = getCustomMenu(inventoryCloseEvent.getInventory());
        if (customMenu == null) {
            return;
        }

        if (customMenu.getClickListener() != null) {
            customMenu.getCloseListener().accept(inventoryCloseEvent);
        }
    }

    private CustomMenu getCustomMenu(Inventory inventory) {
        final IInventory iInventory = ((CraftInventory) inventory).getInventory();

        final Object[] customMenus = customMenuRegistry.getAllMenus().stream()
                .filter(customMenu -> {
                    final IInventory customMenuIInventory = ((CraftInventory) customMenu.getInventory()).getInventory();
                    return customMenuIInventory == iInventory;
                }).toArray();


        if (customMenus.length == 0) {
            return null;
        }

        if (customMenus.length > 1) {
            throw new RuntimeException("More than one Custom Menu found");
        }

        return (CustomMenu) customMenus[0];
    }
}
