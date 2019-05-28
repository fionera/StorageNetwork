package de.fionera.storagenetwork.menu;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class MenuItem {

    private final CustomMenu customMenu;
    private Consumer<InventoryClickEvent> clickListener;

    private int position;
    private Material material;
    private int amount = 1;
    private String name;
    private String[] lore;

    public MenuItem(CustomMenu customMenu) {
        this.customMenu = customMenu;
    }

    public CustomMenu add() {


        return customMenu.setSlot(this);
    }

    public MenuItem setPosition(int x, int y) {
        return setPosition(getPosition(x, y));
    }

    public MenuItem onClick(Consumer<InventoryClickEvent> clickListener) {
        this.clickListener = clickListener;

        return this;
    }

    public MenuItem setMaterial(Material material) {
        this.material = material;

        return this;
    }

    public MenuItem setAmount(int amount) {
        this.amount = amount;

        return this;
    }

    public MenuItem setName(String name) {
        this.name = name;

        return this;
    }

    public MenuItem setLore(String... lore) {
        this.lore = lore;

        return this;
    }

    Consumer<InventoryClickEvent> getClickListener() {
        return clickListener;
    }

    protected int getPosition() {
        return position;
    }

    public MenuItem setPosition(int slot) {
        position = slot;

        return this;
    }

    protected ItemStack getItemStack() {
        Objects.requireNonNull(material);

        final ItemStack itemStack = new ItemStack(material, amount);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            throw new RuntimeException("ItemMeta is null");
        }

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        if (lore != null) {
            itemMeta.setLore(Arrays.asList(lore));
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static int getPosition(int x, int y) {
        return (y - 1) * 9 + x - 1;
    }
}
