package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
import org.codehaus.plexus.util.cli.Commandline;

public class SetCoordinateCommand {
    public SetCoordinateCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("set")
        .then(Commands.literal("coords")
        .then(Commands.argument("target", Vec3Argument.vec3())
            .executes((command) -> {
                return setCoords(command.getSource(), Vec3Argument.getCoordinates(command, "target"));
            }))
        )));
    }

    private int setCoords(CommandSourceStack source, Coordinates targetCoords) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        Vec3 targetVec = targetCoords.getPosition(source);
        ServerLevel pLevel = source.getPlayer().serverLevel();

        int x = (int) targetVec.x;
        int y = (int) targetVec.y;
        int z = (int) targetVec.z;

        boolean core_found = false;

        BlockPos corePos = ePlayerPos;

        for (int _x = -5; _x <= 5 && !core_found; _x++) {
            for (int _y = -5; _y <= 5 && !core_found; _y++) {
                for (int _z = -5; _z <= 5 && !core_found; _z++) {
                    BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                        core_found = true;
                        corePos = currentPos;
                    }
                }
            }
        }

        boolean has_keypad = false;

        for (int _x = -3; _x <= 3 && !has_keypad; _x++) {
            for (int _y = -3; _y <= 3 && !has_keypad; _y++) {
                for (int _z = -3; _z <= 3 && !has_keypad; _z++) {
                    BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.KEYPAD_BLOCK.get()) {
                        has_keypad = true;
                    }
                }
            }
        }
        if (core_found && has_keypad) {
            ModBlocks.needsUpdating.put(corePos.getX() + " " + corePos.getY() + " " + corePos.getZ(), x + " " + y + " " + z);
            source.sendSuccess(() -> Component.literal("Updating designator coordinates to: " + x + " " + y + " " + z), true);
        }
        else {
            source.sendSuccess(() -> Component.literal("Either the core is not in range or you do not have a keypad."), true);
        }

        return 1;
    }

}
