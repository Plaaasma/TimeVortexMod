package net.plaaasma.vortexmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.mapdata.DimensionMapData;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListCoordinateCommand {
    public ListCoordinateCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("list")
            .executes((command) -> {
                return listCoords(command.getSource());
            })));
    }

    private int listCoords(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        String playerName = player.getScoreboardName();
        ServerLevel pLevel = source.getPlayer().serverLevel();
        MinecraftServer minecraftserver = pLevel.getServer();
        ServerLevel tardis_dim = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
        ServerLevel vortex = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
        LocationMapData coord_data = LocationMapData.get(vortex);
        DimensionMapData dim_data = DimensionMapData.get(tardis_dim);

        List<Component> locationStrings = new ArrayList<>();

        Set<String> coordKeys = coord_data.getDataMap().keySet();
        for (String coordKey : coordKeys) {
            if (coordKey.startsWith(playerName)) {
                String pointName = coordKey.substring(playerName.length()) + ": ";
                BlockPos pointPos = coord_data.getDataMap().get(coordKey);
                String pointCoords = pointPos.getX() + " " + pointPos.getY() + " " + pointPos.getZ() + " | ";
                String pointDimension = dim_data.getDataMap().get(coordKey);

                Component pointComponent = Component.literal(pointName).withStyle(ChatFormatting.GRAY).append(Component.literal(pointCoords + pointDimension).withStyle(ChatFormatting.GOLD));
                locationStrings.add(pointComponent);
            }
        }

        for (Component locComp : locationStrings) {
            source.sendSuccess(() -> locComp, false);
        }

        return 1;
    }
}
