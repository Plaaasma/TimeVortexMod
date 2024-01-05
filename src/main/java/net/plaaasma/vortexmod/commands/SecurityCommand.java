package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.mapdata.SecurityMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

public class SecurityCommand {
    public SecurityCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("security")
        .then(Commands.literal("blacklist")
        .then(Commands.argument("target", EntityArgument.player())
            .executes((command) -> {
                return blacklistPlayer(command.getSource(), EntityArgument.getPlayer(command, "target"));
            }))
        )));

        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("security")
        .then(Commands.literal("whitelist")
        .then(Commands.argument("target", EntityArgument.player())
            .executes((command) -> {
                return whitelistPlayer(command.getSource(), EntityArgument.getPlayer(command, "target"));
            }))
        )));
    }

    private int blacklistPlayer(CommandSourceStack source, ServerPlayer targetPlayer) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ServerLevel pLevel = source.getPlayer().serverLevel();
        MinecraftServer minecraftserver = pLevel.getServer();
        ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
        SecurityMapData security_data = SecurityMapData.get(overworld);

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

        boolean has_bio_module = false;

        for (int _x = -16; _x <= 16 && !has_bio_module; _x++) {
            for (int _y = -16; _y <= 16 && !has_bio_module; _y++) {
                for (int _z = -16; _z <= 16 && !has_bio_module; _z++) {
                    BlockPos currentPos = corePos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.BIOMETRIC_BLOCK.get()) {
                        has_bio_module = true;
                    }
                }
            }
        }
        if (core_found && has_bio_module) {
            if (player.getScoreboardName().equals(targetPlayer.getScoreboardName())) {
                source.sendFailure(Component.literal("You cannot blacklist yourself"));
            }
            else {
                if (security_data.getDataMap().containsKey(targetPlayer.getScoreboardName().hashCode() + " " + player.getScoreboardName())) {
                    security_data.getDataMap().remove(targetPlayer.getScoreboardName().hashCode() + " " + player.getScoreboardName());
                    security_data.setDirty();
                    source.sendSuccess(() -> Component.literal("Removing " + targetPlayer.getScoreboardName() + " from the whitelist."), false);
                }
                else {
                    source.sendFailure(Component.literal(targetPlayer.getScoreboardName() + " is already blacklisted."));
                }
            }
        }
        else {
            if (!core_found) {
                source.sendFailure(Component.literal("Core is not in range."));
            }
            if (!has_bio_module) {
                source.sendFailure(Component.literal("Bio security module not in range."));
            }
        }

        return 1;
    }

    private int whitelistPlayer(CommandSourceStack source, ServerPlayer targetPlayer) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ServerLevel pLevel = source.getPlayer().serverLevel();
        MinecraftServer minecraftserver = pLevel.getServer();
        ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
        SecurityMapData security_data = SecurityMapData.get(overworld);

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

        boolean has_bio_module = false;

        for (int _x = -16; _x <= 16 && !has_bio_module; _x++) {
            for (int _y = -16; _y <= 16 && !has_bio_module; _y++) {
                for (int _z = -16; _z <= 16 && !has_bio_module; _z++) {
                    BlockPos currentPos = corePos.offset(_x, _y, _z);

                    BlockState blockState = pLevel.getBlockState(currentPos);
                    if (blockState.getBlock() == ModBlocks.BIOMETRIC_BLOCK.get()) {
                        has_bio_module = true;
                    }
                }
            }
        }
        if (core_found && has_bio_module) {
            if (security_data.getDataMap().containsKey(targetPlayer.getScoreboardName().hashCode() + " " + player.getScoreboardName())) {
                source.sendSuccess(() -> Component.literal(targetPlayer.getScoreboardName() + " is already whitelisted."), false);
            }
            else {
                security_data.getDataMap().put(targetPlayer.getScoreboardName().hashCode() + " " + player.getScoreboardName(), targetPlayer.getScoreboardName());
                security_data.setDirty();
                source.sendSuccess(() -> Component.literal("Adding " + targetPlayer.getScoreboardName() + " to the whitelist."), false);
            }
        }
        else {
            if (!core_found) {
                source.sendFailure(Component.literal("Core is not in range."));
            }
            if (!has_bio_module) {
                source.sendFailure(Component.literal("Bio security module not in range."));
            }
        }

        return 1;
    }
}
