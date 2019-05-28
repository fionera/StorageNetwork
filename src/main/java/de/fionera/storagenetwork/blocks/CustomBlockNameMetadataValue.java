package de.fionera.storagenetwork.blocks;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomBlockNameMetadataValue implements MetadataValue {
    public static final String CUSTOM_BLOCK_NAME_METADATA_KEY = "customBlockName";

    private final String blockType;
    private final JavaPlugin plugin;

    public CustomBlockNameMetadataValue(String blockType, JavaPlugin plugin) {
        this.blockType = blockType;
        this.plugin = plugin;
    }

    @Override
    public Object value() {
        return blockType;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String asString() {
        return blockType;
    }

    @Override
    public Plugin getOwningPlugin() {
        return plugin;
    }

    @Override
    public void invalidate() {

    }
}
