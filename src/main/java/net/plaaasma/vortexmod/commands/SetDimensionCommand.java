package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

public class SetDimensionCommand {

    public SetDimensionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("set")
        .then(Commands.literal("dim")
        .then(Commands.argument("dimension", DimensionArgument.dimension())
            .executes((command) -> {
                return setDim(command.getSource(), DimensionArgument.getDimension(command, "dimension"));
            }))
        )));
    }

    private int setDim(CommandSourceStack source, ServerLevel targetDim) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ServerLevel pLevel = source.getPlayer().serverLevel();

        MinecraftServer minecraftserver = pLevel.getServer();
        ServerLevel tardisDimension = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);

        if (targetDim == tardisDimension) {
            return 1;
        }

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
            interfaceEntity.data.set(10, targetDim.dimension().location().getPath().hashCode());
            source.sendSuccess(() -> Component.literal("Updating target dimension").withStyle(ChatFormatting.GOLD), false);
        }
        else {
            if (!core_found) {
                source.sendSuccess(() -> Component.literal("Core is not in range."), false);
            }
            if (!has_components) {
                source.sendSuccess(() -> Component.literal("Coordinate components not in range."), false);
            }
        }

        return 1;
    }

}
