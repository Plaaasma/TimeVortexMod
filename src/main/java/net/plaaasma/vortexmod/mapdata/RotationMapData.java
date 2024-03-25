package net.plaaasma.vortexmod.mapdata;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class RotationMapData extends SavedData {
    private static final String DATA_NAME = "tardis_rotations";
    private final HashMap<String, Integer> dataMap = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        CompoundTag dataTag = new CompoundTag();

        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            dataTag.putInt(entry.getKey(), entry.getValue());
        }
        pCompoundTag.put(DATA_NAME, dataTag);

        return pCompoundTag;
    }

    public HashMap<String, Integer> getDataMap() {
        return dataMap;
    }

    public static RotationMapData load(CompoundTag pCompoundTag) {
        RotationMapData savedData = new RotationMapData();
        CompoundTag dataTag = pCompoundTag.getCompound(DATA_NAME);
        for (String key : dataTag.getAllKeys()) {
            savedData.dataMap.put(key, dataTag.getInt(key));
        }
        return savedData;
    }

    public static RotationMapData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(RotationMapData::load, RotationMapData::new, DATA_NAME);
    }
}
