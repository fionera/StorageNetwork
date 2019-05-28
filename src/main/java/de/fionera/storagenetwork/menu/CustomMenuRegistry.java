package de.fionera.storagenetwork.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class CustomMenuRegistry {

    private List<CustomMenu> customMenuList = new ArrayList<>();

    private List<CustomMenu> renderMenuList = new ArrayList<>();

    public CustomMenuRegistry() {
    }

    public CustomMenu createMenu(int rows) {
        return new CustomMenu(this, rows * 9);
    }

    void registerMenu(CustomMenu customMenu) {
        customMenuList.add(customMenu);
    }

    /**
     * For static inventories
     *
     * @param player
     * @param customMenuName
     */
    public void openMenu(Player player, String customMenuName) {
        final CustomMenu customMenu = customMenuList.stream().filter(cm -> cm.getTitle().equals(customMenuName)).findFirst().orElseThrow(RuntimeException::new);

        player.openInventory(customMenu.getInventory());
    }

    /**
     * For dynamic playerbound inventories
     *
     * @param player
     * @param menuTemplate
     */
    public void openMenu(Player player, CustomMenuTemplate menuTemplate) {
        final CustomMenu customMenu = menuTemplate.renderMenu(this);

        customMenu.onClose(inventoryCloseEvent -> {
            renderMenuList.remove(customMenu);
        });

        renderMenuList.add(customMenu);

        player.openInventory(customMenu.getInventory());
    }

    public Listener getListener() {
        return new CustomMenuListener(this);
    }

    public List<CustomMenu> getCustomMenuList() {
        return customMenuList;
    }

    public List<CustomMenu> getRenderMenuList() {
        return renderMenuList;
    }

    public List<CustomMenu> getAllMenus() {
        final ArrayList<CustomMenu> menus = new ArrayList<>();
        menus.addAll(customMenuList);
        menus.addAll(renderMenuList);

        return menus;
    }
}
