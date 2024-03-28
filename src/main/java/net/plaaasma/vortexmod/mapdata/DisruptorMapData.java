package net.plaaasma.vortexmod.mapdata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class DisruptorMapData extends SavedData {
    private static final String DATA_NAME = "tardis_disruptors";
    private final HashMap<String, Boolean> dataMap = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        CompoundTag dataTag = new CompoundTag();

        for (Map.Entry<String, Boolean> entry : dataMap.entrySet()) {
            dataTag.putBoolean(entry.getKey(), entry.getValue());
        }
        pCompoundTag.put(DATA_NAME, dataTag);

        return pCompoundTag;
    }

    public HashMap<String, Boolean> getDataMap() {
        return dataMap;
    }

    public static DisruptorMapData load(CompoundTag pCompoundTag) {
        DisruptorMapData savedData = new DisruptorMapData();
        CompoundTag dataTag = pCompoundTag.getCompound(DATA_NAME);
        for (String key : dataTag.getAllKeys()) {
            savedData.dataMap.put(key, dataTag.getBoolean(key));
        }
        return savedData;
    }

    public static DisruptorMapData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(DisruptorMapData::load, DisruptorMapData::new, DATA_NAME);
    }
}
