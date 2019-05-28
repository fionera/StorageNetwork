package de.fionera.storagenetwork.menu;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftChatMessage;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomMenu {

    private final CustomMenuRegistry customMenuRegistry;
    private final Inventory inventory;

    private Pair<Consumer<InventoryClickEvent>, Boolean> clickListener;
    private Consumer<InventoryCloseEvent> closeListener;

    private Map<Integer, MenuItem> menuItems = new HashMap<>();
    private String title;

    protected CustomMenu(CustomMenuRegistry customMenuRegistry, int size) {
        this.customMenuRegistry = customMenuRegistry;
        this.inventory = Bukkit.createInventory(null, size);
    }

    public MenuItem addItem() {
        return new MenuItem(this);
    }

    public CustomMenuRegistry register() {
        customMenuRegistry.registerMenu(this);

        return customMenuRegistry;
    }

    public CustomMenu onClick(Consumer<InventoryClickEvent> clickListener, boolean beforeItemClick) {
        this.clickListener = Pair.of(clickListener, beforeItemClick);

        return this;
    }

    Pair<Consumer<InventoryClickEvent>, Boolean> getClickListener() {
        return clickListener;
    }

    public CustomMenu onClose(Consumer<InventoryCloseEvent> closeListener) {
        this.closeListener = closeListener;

        return this;
    }

    public Consumer<InventoryCloseEvent> getCloseListener() {
        return closeListener;
    }

    CustomMenu setSlot(MenuItem menuItem) {
        final int position = menuItem.getPosition();

        menuItems.put(position, menuItem);
        inventory.setItem(position, menuItem.getItemStack());

        return this;
    }

    public String getTitle() {
        return title;
    }

    public CustomMenu setTitle(String title) {
        this.title = title;

        //TODO: Do I really want to use NMS?
        ((CraftInventoryCustom) this.inventory).getInventory().getDisplayName().a().set(0, CraftChatMessage.fromString(title)[0]);

        return this;
    }

    MenuItem getMenuItem(int slot) {
        return menuItems.get(slot);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
