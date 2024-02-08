package net.plaaasma.vortexmod.interior.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.mapdata.LocationMapData;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

public class InteriorUtil {
    public static final Random RANDOM = new Random();
    public static final int INTERIOR_SIZE = 256;

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

        if (first == null) {
            // Bad code.
            VortexMod.P_LOGGER.warn("Could not find interior for " + owner + " | Giving this TARDIS a new position");
            data.getDataMap().put(owner, findNewInteriorPosition());
            first = data.getDataMap().get(owner);
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

    /**
     * @return A random position from range -10000 to 10000 in the x and z axis
     */
    public static BlockPos findNewInteriorPosition() {
        return new BlockPos(RANDOM.nextInt(-10000, 10000), -128, RANDOM.nextInt(-10000, 10000));
    }

    /**
     * Returns whether a position is within a volume of coordinates
     * @param pos the position to look for
     * @param first lowest corner of the volume
     * @param second largest corner of the volume
     * @return whether the position is within the volume
     */
    public static boolean isInArea(BlockPos pos, BlockPos first, BlockPos second) {
        return pos.getX() >= first.getX() && pos.getX() <= second.getX() && pos.getZ() >= first.getZ() && pos.getZ() <= second.getZ();
    }

    /**
     * Returns whether an entity is within a volume of coordinates
     * @param entity the entity to look for
     * @param first lowest corner of the volume
     * @param second largest corner of the volume
     * @return whether the entity is within the volume
     */
    public static boolean isInArea(Entity entity, BlockPos first, BlockPos second) {
        return isInArea(entity.blockPosition(), first, second);
    }

    /**
     * Returns whether a position is within a TARDIS' interior
     * @param pos the position to look for
     * @param server server for grabbing location data
     * @param owner the key in the LocationMapData for a TARDIS ( TODO - I've just realised now that we store interiors using Entity UUIDS, not Owner Hashcodes. Dammit )
     * @return whether the position is within the interior
     */
    public static boolean isInInterior(BlockPos pos, MinecraftServer server, String owner) {
        BlockPos[] corners = getCorners(server, owner);

        return isInArea(pos, corners[0], corners[1]);
    }
    /**
     * Returns whether an entity is within a TARDIS' interior
     * @param entity the entity to look for
     * @param server server for grabbing location data
     * @param owner the key in the LocationMapData for a TARDIS ( TODO - I've just realised now that we store interiors using Entity UUIDS, not Owner Hashcodes. Dammit )
     * @return whether the position is within the interior
     */
    public static boolean isInInterior(Entity entity, MinecraftServer server, String owner) {
        return isInInterior(entity.blockPosition(), server, owner);
    }

    /**
     * Gets the centre of two coordinates
     * @param first the largest corner
     * @param second the smallest corner
     * @return the centre
     */
    public static BlockPos getCentre(BlockPos first, BlockPos second) {
        return BlockPos.containing(InteriorUtil.toBox(first, second).getCenter());
    }

    /**
     * Maths out the centre of an interior
     * @param server server for grabbing location data
     * @param owner the key for the TARDIS' interior corner
     * @return the centre
     */
    public static BlockPos getInteriorCentre(MinecraftServer server, String owner) {
        BlockPos[] corners = getCorners(server, owner);
        return getCentre(corners[0], corners[1]);
    }

    /**
     * Finds the nearest air block from a position
     * @param level the world to look in
     * @param pos the source position
     * @return the position of the air block
     */
    public static BlockPos findNearestAir(ServerLevel level, BlockPos pos) {
        return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
    }

    /**
     * Finds the first valid centre for a size, goes upwards - warning may be laggy
     * @param level the level to search in
     * @param source the centre of the current air where the search starts
     * @param size the area to check in
     * @return the centre of the first valid air or null if none was found
     */
    @Nullable
    public static BlockPos findAirForSize(ServerLevel level, BlockPos source, int size) {
        BlockPos found = null;

        BlockPos current = source;
        BlockPos first = source.north(size).west(size);
        BlockPos second = source.south(size).east(size);

        int iterations = 0;
        int maxIterations = 128; // This limits how many iterations occur so the server doesnt freeze

        while (found == null && iterations < maxIterations) {
            boolean isCurrentAir = level.getBlockState(current).canBeReplaced();

            for (BlockPos pos : BlockPos.betweenClosed(first, second)) {
                if (isCurrentAir && level.getBlockState(pos).canBeReplaced()) {
                    found = pos;
                    break;
                }
            }

            current = current.above();
            first = first.above();
            second = second.above();
            iterations++;
        }

        return found;
    }

    // Not my code, this was from VortexInterfaceBlock
    @Deprecated
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
