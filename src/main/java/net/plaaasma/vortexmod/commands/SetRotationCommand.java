package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sun.jdi.connect.Connector;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

public class SetRotationCommand {
    public SetRotationCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("set")
        .then(Commands.literal("rotation")
        .then(Commands.argument("yaw", IntegerArgumentType.integer())
            .executes((command) -> {
                return setRotation(command.getSource(), IntegerArgumentType.getInteger(command, "yaw"));
            }))
        )));
    }

    private int setRotation(CommandSourceStack source, Integer targetRot) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ServerLevel pLevel = source.getPlayer().serverLevel();
        MinecraftServer minecraftserver = pLevel.getServer();
        ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);

        boolean core_found = false;

        BlockPos corePos = ePlayerPos;

        VortexInterfaceBlockEntity interfaceEntity = null;

        for (int _x = -16; _x <= 16 && !core_found; _x++) {
            for (int _y = -16; _y <= 16 && !core_found; _y++) {
                for (int _z = -16; _z <= 16 && !core_found; _z++) {
                    BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                        core_found = true;
                        corePos = currentPos;
                        interfaceEntity = (VortexInterfaceBlockEntity) pLevel.getBlockEntity(corePos);
                    }
                }
            }
        }

        boolean has_components = false;
        boolean has_keypad = false;
        boolean has_designator = false;

        CoordinateDesignatorBlockEntity designatorEntity = null;

        for (int _x = -16; _x <= 16; _x++) {
            for (int _y = -16; _y <= 16; _y++) {
                for (int _z = -16; _z <= 16; _z++) {
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
            DimensionMapData dim_data = DimensionMapData.get(overworld);
            interfaceEntity.data.set(12, targetRot);
            Direction rotationDirection;
            if (targetRot >= 0 && targetRot < 90) {
                rotationDirection = Direction.NORTH;
            }
            else if (targetRot >= 90 && targetRot < 180) {
                rotationDirection = Direction.EAST;
            }
            else if (targetRot >= 180 && targetRot < 270) {
                rotationDirection = Direction.SOUTH;
            }
            else {
                rotationDirection = Direction.WEST;
            }
            source.sendSuccess(() -> Component.literal("Updating target rotation to: ").append(Component.literal(rotationDirection.getOpposite().toString()).withStyle(ChatFormatting.GOLD)), false);
        }
        else {
            if (!core_found) {
                source.sendFailure(Component.literal("Core is not in range."));
            }
            if (!has_components) {
                source.sendFailure(Component.literal("Coordinate components not in range. (Keypad and Designator)"));
            }
        }

        return 1;
    }

}
