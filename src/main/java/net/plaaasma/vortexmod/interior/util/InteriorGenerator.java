package net.plaaasma.vortexmod.interior.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.interior.registry.InteriorSchema;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used for generating an interior, placed using {@link InteriorGenerator#place(ServerLevel, BlockPos, BlockPos)}
 * @see InteriorSchema
 * @author duzo
 */
public class InteriorGenerator {
    private final InteriorSchema schema;

    public InteriorGenerator(InteriorSchema schema) {
        this.schema = schema;
    }

    /**
     * Places this interior at the centre of the specified corner coordinates
     * @param level The TARDIS dimension to place this interior in
     * @param first The first interior corner
     * @param second The second interior corner
     * @return The position of the door, or first if placement failed
     * @author duzo
     */
    public BlockPos place(ServerLevel level, BlockPos first, BlockPos second) {
        if (this.schema == null) {
            VortexMod.P_LOGGER.error("Tried to place interior with null schema");
            return first;
        }

        Optional<StructureTemplate> found = this.schema.findTemplate(level);
        if (found.isEmpty()) {
            VortexMod.P_LOGGER.error("Schema " + this.schema.id() + " is missing a structure!");
            return first;
        }

        StructureTemplate template = found.get();

        BlockPos centre = BlockPos.containing(InteriorUtil.toBox(first, second).getCenter());
        BlockPos size = new BlockPos(template.getSize().getX(), template.getSize().getY(), template.getSize().getZ());
        BlockPos offset = new BlockPos(-(size.getX() / 2),0,-(size.getZ() / 2)); // We offset it by its own negative size divided by two to ensure it is properly placed around the centre
        BlockPos placedPos = centre.offset(offset);

        template.placeInWorld(
                level, // The dimension this will be placed in
                template.getZeroPositionWithTransform(placedPos.atY(-128), Mirror.NONE, Rotation.NONE),
                placedPos,
                new StructurePlaceSettings(),
                level.getRandom(),
                Block.UPDATE_NONE
        );

        Optional<BlockPos> door = findBlockInTemplate(
                template,
                placedPos,
                Direction.NORTH,
                ModBlocks.DOOR_BLOCK.get()
        );

        if (door.isEmpty()) {
            VortexMod.P_LOGGER.warn("Could not find door in interior " + this.schema.id());
        }

        return door.orElse(first);
    }

    /**
     * Places an interior based off the owner
     * @param server the current server, used for grabbing the tardis dimension
     * @param owner the tardis' owner
     * @return the position of the door
     * @see #place(ServerLevel, BlockPos, BlockPos)
     */
    public BlockPos place(MinecraftServer server, String owner) {
        BlockPos[] corners = InteriorUtil.getCorners(server, owner);

        return this.place(
                server.getLevel(ModDimensions.tardisDIM_LEVEL_KEY),
                corners[0],
                corners[1]
        );
    }

    /**
     * Changes the interior
     * This method
     *  -> Clears all the existing blocks
     *  -> Places the interior
     *  -> Moves the interface to the centre of the interior, if there was already one in this interior
     * @param clearInterior Whether to clear out the entire interior before placing a new one - if true this may cause lag!
     * @return the door position
     */
    public BlockPos changeInterior(MinecraftServer server, String owner, boolean clearInterior) {
        BlockPos[] corners = InteriorUtil.getCorners(server, owner);

        ServerLevel level = server.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);

        BlockPos centre = BlockPos.containing(InteriorUtil.toBox(corners[0], corners[1]).getCenter());

        if (clearInterior) {
            List<BlockPos> excluded =
                    clearArea(
                            level,
                            corners[0],
                            corners[1],
                            ModBlocks.INTERFACE_BLOCK.get() // todo add more to the exclusion if necessary
                    );

            // Laggy code? yes.
            BlockPos core = excluded.stream().filter(pos -> {
                BlockState state = level.getBlockState(pos);
                return state.is(ModBlocks.INTERFACE_BLOCK.get());
            }).findFirst().orElse(corners[0]);

            // Dont think this code works / is needed.
            BlockState coreState = level.getBlockState(core);
            level.setBlock(core, Blocks.AIR.defaultBlockState(), Block.UPDATE_NONE);
            level.setBlock(centre, coreState, Block.UPDATE_NONE);
        }

        return this.place(server, owner);
    }


    /**
     * Finds the specified block in the template
     * @param template the template to search in
     * @param pos the position where this template was placed in the world
     * @param direction the direction of the template
     * @param targetBlock the block to search for
     * @return the found block or an empty optional
     */
    public static Optional<BlockPos> findBlockInTemplate(StructureTemplate template, BlockPos pos, Direction direction, Block targetBlock) {
        List<StructureTemplate.StructureBlockInfo> list = template.filterBlocks(
                pos,
                new StructurePlaceSettings().setRotation(
                        directionToRotation(direction)
                ),
                targetBlock
        );

        if (list.isEmpty())
            return Optional.empty();

        return Optional.of(list.get(0).pos());
    }
    public static Rotation directionToRotation(Direction direction) {
        return switch (direction) {
            case NORTH -> Rotation.CLOCKWISE_180;
            case EAST -> Rotation.COUNTERCLOCKWISE_90;
            case WEST -> Rotation.CLOCKWISE_90;
            default -> Rotation.NONE;
        };
    }

    /**
     * THIS WILL BE VERY LAGGY!!
     * Sets all the blocks in the given positions to air
     * @param level the world to clear in
     * @param first the first corner of the area
     * @param second the second corner of the area
     * @param excludes all the {@link Block}'s to ignore and not delete
     * @return a list of positions of the blocks that were excluded
     */
    public static List<BlockPos> clearArea(ServerLevel level, BlockPos first, BlockPos second, Block... excludes) {
        long time = System.currentTimeMillis();
        List<BlockPos> excluded = new ArrayList<>();

        for (BlockPos pos : BlockPos.betweenClosed(first, second)) {
            if (Arrays.stream(excludes).anyMatch(b -> level.getBlockState(pos).is(b))) { // this line is likely the laggiest part
                excluded.add(pos);
                continue;
            }

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_NONE);
        }

        VortexMod.P_LOGGER.warn("Area cleared in " + (System.currentTimeMillis() - time) + "ms");

        return excluded;
    }
}
