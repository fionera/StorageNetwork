package de.fionera.storagenetwork.storage;

import de.fionera.storagenetwork.block.CustomBlock;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockStorage {
    private final Map<Location, CustomBlock> blocks;

    public BlockStorage() {
        this.blocks = Collections.synchronizedMap(new HashMap<>());
    }

    public CustomBlock getBlockForLocation(Location location) {
        synchronized (blocks) {
            if (blocks.containsKey(location)) {
                return blocks.get(location);
            }
        }

        return null;
    }

    public void setBlockForLocation(Location location, CustomBlock customBlock) {
        synchronized (blocks) {
            if (blocks.containsKey(location)) {
                throw new RuntimeException("There is already a block");
            }

            blocks.put(location, customBlock);
        }
    }

    public void destroyBlock(Location location) {
        synchronized (blocks) {
            if (blocks.containsKey(location)) {
                blocks.remove(location);
            } else {
                throw new RuntimeException("There is no custom block at that location");
            }
        }
    }
}
