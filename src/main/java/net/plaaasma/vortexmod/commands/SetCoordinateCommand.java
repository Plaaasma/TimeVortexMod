package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import org.codehaus.plexus.util.cli.Commandline;

public class SetCoordinateCommand {
    public SetCoordinateCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("target")
        .then(Commands.literal("set")
        .then(Commands.literal("coords")
        .then(Commands.argument("target", Vec3Argument.vec3())
            .executes((command) -> {
                return setCoords(command.getSource(), Vec3Argument.getCoordinates(command, "target"));
            }))
        ))));
    }

    private int setCoords(CommandSourceStack source, Coordinates targetCoords) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        BlockPos targetVec = targetCoords.getBlockPos(source);
        ServerLevel pLevel = source.getPlayer().serverLevel();

        int x = targetVec.getX();
        int y = targetVec.getY();
        int z = targetVec.getZ();

        boolean core_found = false;

        BlockPos corePos = ePlayerPos;

        for (int _x = -16; _x <= 16 && !core_found; _x++) {
            for (int _y = -16; _y <= 16 && !core_found; _y++) {
                for (int _z = -16; _z <= 16 && !core_found; _z++) {
                    BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                        core_found = true;
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
            designatorEntity.data.set(0, x);
            designatorEntity.data.set(1, y);
            designatorEntity.data.set(2, z);
            source.sendSuccess(() -> Component.literal("Updating designator coordinates to: ").append(Component.literal(x + " " + y + " " + z).withStyle(ChatFormatting.GOLD)), false);
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
