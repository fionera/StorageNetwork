package de.fionera.storagenetwork.block;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockClickListener implements Listener {

    private final CustomBlockRegistry customBlockRegistry;

    public CustomBlockClickListener(CustomBlockRegistry customBlockRegistry) {
        this.customBlockRegistry = customBlockRegistry;
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if (!customBlockRegistry.isCustomBlockItem(blockPlaceEvent.getItemInHand())) {
            return;
        }

        final CustomBlock customBlock;

        try {
            customBlock = customBlockRegistry.getCustomBlockForItem(blockPlaceEvent.getItemInHand());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return;
        }

        final Block block = blockPlaceEvent.getBlock();

        customBlockRegistry.placeBlock(block.getLocation(), customBlock);

        block.setType(customBlock.getTemplate().getMaterial());
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent blockBreakEvent) {
        customBlockRegistry.breakBlock(blockBreakEvent.getBlock().getLocation());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (playerInteractEvent.getPlayer().isSneaking()) {
            return;
        }

        final Block clickedBlock = playerInteractEvent.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        final CustomBlock customBlock = customBlockRegistry.getCustomBlock(clickedBlock.getLocation());
        if (customBlock == null) {
            return;
        }

        playerInteractEvent.setCancelled(true);

        customBlock.onRightClick(playerInteractEvent);
    }

}
