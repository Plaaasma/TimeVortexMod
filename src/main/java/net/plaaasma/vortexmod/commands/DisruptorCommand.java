package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.plaaasma.vortexmod.mapdata.DisruptorMapData;

import java.util.HashMap;

public class DisruptorCommand {
    public DisruptorCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tadmin")
        .requires(source -> source.hasPermission(4))
        .then(Commands.literal("disruptor")
        .then(Commands.literal("add")
            .executes((command) -> {
                return disruptChunk(command.getSource());
            }))));

        dispatcher.register(Commands.literal("tadmin")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("disruptor")
                        .then(Commands.literal("remove")
                                .executes((command) -> {
                                    return undisruptChunk(command.getSource());
                                }))));

        dispatcher.register(Commands.literal("tadmin")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("disruptor")
                        .then(Commands.literal("list")
                                .executes((command) -> {
                                    return listDisruptChunks(command.getSource());
                                }))));
    }

    private int disruptChunk(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ChunkPos eChunkPos = new ChunkPos(ePlayerPos);
        ServerLevel pLevel = source.getPlayer().serverLevel();
        DisruptorMapData disruptorMapData = DisruptorMapData.get(pLevel);
        HashMap<String, Integer> dataMap = disruptorMapData.getDataMap();

        dataMap.put(eChunkPos.toString(), 0);
        source.sendSuccess(() -> Component.literal("Adding " + eChunkPos + " to the disrupt list."), false);

        disruptorMapData.setDirty();

        return 1;
    }

    private int undisruptChunk(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos ePlayerPos = player.blockPosition();
        ChunkPos eChunkPos = new ChunkPos(ePlayerPos);
        ServerLevel pLevel = source.getPlayer().serverLevel();
        DisruptorMapData disruptorMapData = DisruptorMapData.get(pLevel);
        HashMap<String, Integer> dataMap = disruptorMapData.getDataMap();

        if (dataMap.containsKey(eChunkPos.toString())) {
            dataMap.remove(eChunkPos.toString());
            source.sendSuccess(() -> Component.literal("Removing " + eChunkPos + " from the disrupt list."), false);

            disruptorMapData.setDirty();
        }
        else{
            source.sendFailure(Component.literal(eChunkPos + " is not in the disrupt list."));
        }

        return 1;
    }

    private int listDisruptChunks(CommandSourceStack source) throws CommandSyntaxException {
        ServerLevel pLevel = source.getPlayer().serverLevel();
        DisruptorMapData disruptorMapData = DisruptorMapData.get(pLevel);
        HashMap<String, Integer> dataMap = disruptorMapData.getDataMap();

        source.sendSuccess(() -> Component.literal("---------------"), false);
        for (String key : dataMap.keySet()) {
            source.sendSuccess(() -> Component.literal(key + ": " + dataMap.get(key)), false);
        }
        source.sendSuccess(() -> Component.literal("---------------"), false);

        return 1;
    }
}
