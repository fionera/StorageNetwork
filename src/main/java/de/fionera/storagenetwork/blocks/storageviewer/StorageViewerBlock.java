package de.fionera.storagenetwork.blocks.storageviewer;

import com.logisticscraft.logisticsapi.block.LogisticBlock;
import com.logisticscraft.logisticsapi.libs.itemnbt.NBTCompound;
import de.fionera.storagenetwork.storage.ItemStorage;


public class StorageViewerBlock extends LogisticBlock implements {

    private final ItemStorage itemStorage = new ItemStorage();

    @Override
    public void onNBTSave(NBTCompound nbtData) {
        super.onNBTSave(nbtData);
    }
}
