package net.plaaasma.vortexmod.interior.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.mapdata.LocationMapData;

import javax.swing.*;
import java.util.Set;

public class InteriorUtil {
    public static final int INTERIOR_SIZE = 10000;

    /**
     * Gets the corners of a players interior
     * TARDIS' will have a size of 10000 in each axis
     * @param server the current server, used for obtaining data
     * @param owner the tardis to look for
     * @return the corners found
     */
    public static BlockPos[] getCorners(MinecraftServer server, String owner) {
        LocationMapData data = LocationMapData.get(server.getLevel(Level.OVERWORLD));

        // This ( at least from what ive read ) should be the greatest coordinate of the interior
        // Tardis' ( from what ive seen ) will have a size of 10000
        BlockPos first = data.getDataMap().get(owner);

        System.out.println(data.getDataMap());

        if (first == null) {
            // Bad code.
            VortexMod.P_LOGGER.warn("Could not find interior for " + owner + " | Assuming next position is ours");
            first = findFurthestInterior(server).offset(INTERIOR_SIZE, 0, INTERIOR_SIZE); // This is where the next interior will be, assume it'll be ours
        }


        // We offset the greatest coordinate by the size of the interior, to get the smallest possible coord the interior can have
        BlockPos second = first.offset(-INTERIOR_SIZE, 0, -INTERIOR_SIZE);

        return new BlockPos[] {first, second};
    }

    /**
     * @return The two corners in box form, for better math
     */
    public static AABB toBox(BlockPos first, BlockPos second) {
        return new AABB(first, second);
    }

    // Not my code, this was from VortexInterfaceBlock
    public static BlockPos findFurthestInterior(MinecraftServer server) {
        LocationMapData data = LocationMapData.get(server.getLevel(Level.OVERWORLD));
        Set<String> keyList = data.getDataMap().keySet();

        int greatest_x_coordinate = -1000000;
        int greatest_z_coordinate = -1000000;
        for (String key : keyList) {
            BlockPos interiorPos = data.getDataMap().get(key);
            int x_coordinate = interiorPos.getX();
            int z_coordinate = interiorPos.getZ();
            if (x_coordinate > greatest_x_coordinate) {
                greatest_x_coordinate = x_coordinate;
            }
            if (z_coordinate > greatest_z_coordinate) {
                greatest_z_coordinate = z_coordinate;
            }
        }

        return new BlockPos(greatest_x_coordinate, 0, greatest_z_coordinate);
    }
}
