package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.mapdata.DimensionMapData;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

public class SaveCoordinateCommand {
    public SaveCoordinateCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("locations")
        .then(Commands.literal("save")
                .then(Commands.argument("name", MessageArgument.message())
                        .executes((command) -> {
                            return setCoords(command.getSource(), MessageArgument.getMessage(command, "name"));
                        })))));
    }

    private int setCoords(CommandSourceStack source, Component locName) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ServerLevel pLevel = source.getPlayer().serverLevel();
        MinecraftServer minecraftserver = pLevel.getServer();
        ServerLevel tardis_dim = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
        ServerLevel vortex = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
        LocationMapData coord_data = LocationMapData.get(vortex);
        DimensionMapData dim_data = DimensionMapData.get(tardis_dim);

        boolean core_found = false;

        BlockPos corePos = ePlayerPos;

        VortexInterfaceBlockEntity vortexInterfaceBlockEntity = null;

        for (int _x = -16; _x <= 16 && !core_found; _x++) {
            for (int _y = -16; _y <= 16 && !core_found; _y++) {
                for (int _z = -16; _z <= 16 && !core_found; _z++) {
                    BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                        core_found = true;
                        vortexInterfaceBlockEntity = (VortexInterfaceBlockEntity) pLevel.getBlockEntity(currentPos);
                        corePos = currentPos;
                    }
                }
            }
        }

        boolean has_components = false;
        boolean has_keypad = false;
        boolean has_designator = false;

        CoordinateDesignatorBlockEntity designatorEntity = null;

        for (int _x = -16; _x <= 16 && !has_components; _x++) {
            for (int _y = -16; _y <= 16 && !has_components; _y++) {
                for (int _z = -16; _z <= 16 && !has_components; _z++) {
                    BlockPos currentPos = corePos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.KEYPAD_BLOCK.get()) {
                        has_keypad = true;
                    }
                    else if (blockState.getBlock() == ModBlocks.COORDINATE_BLOCK.get()) {
                        designatorEntity = (CoordinateDesignatorBlockEntity) pLevel.getBlockEntity(currentPos);
                        has_designator = true;
                    }
                    if (has_keypad && has_designator) {
                        has_components = true;
                    }
                }
            }
        }
        if (core_found && has_components && designatorEntity != null) {
            int dim_hash = vortexInterfaceBlockEntity.data.get(10);

            Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();
            ServerLevel currentLevel = pLevel;

            for (ServerLevel cLevel : serverLevels) {
                if (cLevel.dimension().location().getPath().hashCode() == dim_hash) {
                    currentLevel = cLevel;
                }
            }

            BlockPos targetVec = new BlockPos(vortexInterfaceBlockEntity.data.get(3), vortexInterfaceBlockEntity.data.get(4), vortexInterfaceBlockEntity.data.get(5));

            coord_data.getDataMap().put(player.getScoreboardName() + locName.getString(), targetVec);
            dim_data.getDataMap().put(player.getScoreboardName() + locName.getString(), currentLevel.dimension().location().getPath());
            ServerLevel finalCurrentLevel = currentLevel;
            source.sendSuccess(() -> Component.literal("Adding the current target coordinates (" + targetVec.getX() + " " + targetVec.getY() + " " + targetVec.getZ() +  " | " + finalCurrentLevel.dimension().location().getPath() + ") as " + locName.getString()), false);
        }
        else {
            if (!core_found) {
                source.sendFailure(Component.literal("Core is not in range."));
            }
            if (!has_components) {
                source.sendFailure(Component.literal("Coordinate components not in range. (Keypad and Designator)"));
            }
        }

        dim_data.setDirty();
        coord_data.setDirty();

        return 1;
    }
}
