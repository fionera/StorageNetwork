package de.fionera.storagenetwork.block;

import org.bukkit.event.player.PlayerInteractEvent;

public abstract class CustomBlock {
    public void onRightClick(PlayerInteractEvent playerInteractEvent) {

    }

    abstract public CustomBlockTemplate getTemplate();
}
