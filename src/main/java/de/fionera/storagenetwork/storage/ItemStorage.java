package de.fionera.storagenetwork.storage;


import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Threadsafe Storage for ItemStacks with unlimited amount
 */
public class ItemStorage {

    private final List<ItemStack> storageList;

    public ItemStorage() {
        storageList = Collections.synchronizedList(new ArrayList<>());
    }

    public void add(ItemStack itemStack) {
        synchronized (storageList) {
            for (int i = storageList.size() - 1; i >= 0; i--) {
                final ItemStack currentItemStack = storageList.get(i);
                if (!itemStack.isSimilar(currentItemStack)) {
                    continue;
                }

                currentItemStack.setAmount(currentItemStack.getAmount() + itemStack.getAmount());
                return;
            }

            storageList.add(itemStack.clone());
        }
    }

    public ItemStack get(ItemStack itemStack, int amount) {
        ItemStack returnedStack = null;

        synchronized (storageList) {
            for (int i = storageList.size() - 1; i >= 0; i--) {
                final ItemStack currentItemStack = storageList.get(i);

                if (currentItemStack.getType() != itemStack.getType()) {
                    continue;
                }

                int newAmount = currentItemStack.getAmount() - amount;
                if (newAmount <= 0) {
                    amount = currentItemStack.getAmount();

                    storageList.remove(i);
                } else {
                    currentItemStack.setAmount(newAmount);
                }

                returnedStack = currentItemStack.clone();
                returnedStack.setAmount(amount);

                break;
//                itemStack.getItemMeta().getLore().remove(itemStack.getItemMeta().getLore().size() - 1);
            }
        }

        return returnedStack;
    }

    public List<ItemStack> getView(int size, int offset) {
        List<ItemStack> cloneList = new ArrayList<>();
        int end = offset + size;

        synchronized (storageList) {
            if (storageList.size() < end) {
                end = storageList.size();
            }

            if (end > offset) {
                for (ItemStack itemStack : storageList.subList(offset, end)) {
                    cloneList.add(itemStack.clone());
                }
            }
        }

        for (int i = 0; i < cloneList.size(); i++) {
            ItemStack itemStack = cloneList.get(i);

            final int amount = itemStack.getAmount();
            itemStack.setAmount(1);

            final ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) {
                throw new RuntimeException("Item without ItemMeta");
            }

            List<String> lore = itemMeta.getLore();

            if (lore == null) {
                lore = new ArrayList<>();
            }

            lore.add("Amount: " + amount);

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            cloneList.set(i, itemStack);
        }

        return cloneList;
    }


}
