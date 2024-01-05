package net.plaaasma.vortexmod.mapdata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class SecurityMapData extends SavedData {
    private static final String DATA_NAME = "saved_whitelists";
    private final HashMap<String, String> dataMap = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        CompoundTag dataTag = new CompoundTag();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            dataTag.putString(entry.getKey(), entry.getValue());
        }
        pCompoundTag.put(DATA_NAME, dataTag);

        return pCompoundTag;
    }

    public HashMap<String, String> getDataMap() {
        return dataMap;
    }

    public static SecurityMapData load(CompoundTag pCompoundTag) {
        SecurityMapData savedData = new SecurityMapData();
        CompoundTag dataTag = pCompoundTag.getCompound(DATA_NAME);
        for (String key : dataTag.getAllKeys()) {
            savedData.dataMap.put(key, dataTag.getString(key));
        }
        return savedData;
    }

    public static SecurityMapData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(SecurityMapData::load, SecurityMapData::new, DATA_NAME);
    }
}
