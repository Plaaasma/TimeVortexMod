package net.plaaasma.vortexmod.mapdata;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class DimensionMapData extends SavedData {
    private static final String DATA_NAME = "saved_dim_locations";
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

    public static DimensionMapData load(CompoundTag pCompoundTag) {
        DimensionMapData savedData = new DimensionMapData();
        CompoundTag dataTag = pCompoundTag.getCompound(DATA_NAME);
        for (String key : dataTag.getAllKeys()) {
            savedData.dataMap.put(key, dataTag.getString(key));
        }
        return savedData;
    }

    public static DimensionMapData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(DimensionMapData::load, DimensionMapData::new, DATA_NAME);
    }
}
