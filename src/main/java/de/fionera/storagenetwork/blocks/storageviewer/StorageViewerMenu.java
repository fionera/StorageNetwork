package de.fionera.storagenetwork.blocks.storageviewer;

import de.fionera.storagenetwork.menu.CustomMenu;
import de.fionera.storagenetwork.menu.CustomMenuRegistry;
import de.fionera.storagenetwork.menu.CustomMenuTemplate;
import de.fionera.storagenetwork.menu.MenuItem;
import de.fionera.storagenetwork.storage.ItemStorage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class StorageViewerMenu implements CustomMenuTemplate {
    private static final String STORAGE_VIEWER_TITLE = "Storage Viewer";
    private static final int INVENTORY_SIZE = 6 * 9;

    private final ItemStorage itemStorage;

    private int currentPage = 1;
    private int lastPage = 100;

    public StorageViewerMenu(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    private String getCurrentPageLore() {
        return "Page " + currentPage + " of " + lastPage;
    }

    @Override
    public CustomMenu renderMenu(CustomMenuRegistry registry) {
        final CustomMenu customMenu = registry.createMenu(7)
                .setTitle(STORAGE_VIEWER_TITLE)
                .onClick(this::renderInventoryOnClick, false)

                .addItem()
                .setMaterial(Material.RED_WOOL)
                .setName("Previous Page")
                .setPosition(1, 7)
                .onClick(inventoryClickEvent -> {
                    if (currentPage > 1) {
                        currentPage--;
                    }
                })
                .add()

                .addItem()
                .setMaterial(Material.BLUE_WOOL)
                .setName("Current Page")
                .setLore(getCurrentPageLore())
                .setPosition(5, 7)
                .add()

                .addItem()
                .setMaterial(Material.GREEN_WOOL)
                .setName("Next Page")
                .setPosition(9, 7)
                .onClick(inventoryClickEvent -> {
                    if (currentPage < lastPage) {
                        currentPage++;
                    }
                })
                .add();

        renderCurrentPage(customMenu.getInventory());

        return customMenu;
    }

    private void renderInventoryOnClick(InventoryClickEvent inventoryClickEvent) {
        final Inventory inventory = inventoryClickEvent.getInventory();

        renderCurrentPageItem(inventory);

        if (clickedInCustomMenu(inventoryClickEvent)) {
            final ItemStack cursor = inventoryClickEvent.getCursor();

            if (clickedInsideInventory(inventoryClickEvent.getSlot())) {
                if (cursor != null && cursor.getType() != Material.AIR) {
                    itemStorage.add(cursor);

                    inventoryClickEvent.setCursor(new ItemStack(Material.AIR));
                } else {
                    final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
                    if (currentItem != null && currentItem.getType() != Material.AIR) {

                        final ItemStack itemStack = itemStorage.get(currentItem, 64);

                        if (itemStack != null) {

                            inventoryClickEvent.setCursor(itemStack);
                            inventoryClickEvent.setCancelled(true);
                        }
                    }
                }
            }
        } else {
            inventoryClickEvent.setCancelled(false);
        }

        renderCurrentPage(inventory);

//        inventoryClickEvent.getWhoClicked().closeInventory();
    }

    private boolean clickedInsideInventory(int slot) {
        return slot < INVENTORY_SIZE;
    }

    private void renderCurrentPageItem(Inventory inventory) {
        final ItemStack pageItem = inventory.getItem(MenuItem.getPosition(5, 7));
        if (pageItem != null) {
            ItemMeta itemMeta = pageItem.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setLore(Collections.singletonList(getCurrentPageLore()));
                pageItem.setItemMeta(itemMeta);
            }
        }
    }

    private void renderCurrentPage(Inventory inventory) {

        final List<ItemStack> view = itemStorage.getView(INVENTORY_SIZE, (currentPage - 1) * INVENTORY_SIZE);

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (view.size() > i) {
                inventory.setItem(i, view.get(i));
            } else {
                inventory.setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    private boolean clickedInCustomMenu(InventoryClickEvent inventoryClickEvent) {
        return inventoryClickEvent.getClickedInventory() == inventoryClickEvent.getInventory();
    }
}
