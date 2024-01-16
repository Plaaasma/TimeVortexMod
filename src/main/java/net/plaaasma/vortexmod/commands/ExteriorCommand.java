package net.plaaasma.vortexmod.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.mapdata.SecurityMapData;

import java.util.Collection;

public class ExteriorCommand {
    public ExteriorCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tardis")
        .then(Commands.literal("exterior")
        .then(Commands.literal("text")
        .then(Commands.argument("target", MessageArgument.message())
            .executes((command) -> {
                return setSign(command.getSource(), MessageArgument.getMessage(command, "target"));
            }))
        )));
    }

    private int setSign(CommandSourceStack source, Component signComponent) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos playerPos = player.blockPosition();
        ServerLevel pLevel = source.getPlayer().serverLevel();

        if (signComponent.getString().length() > 16) {
            source.sendFailure(Component.literal("Sign text cannot be more than 16 characters"));
        }
        else {
            VortexInterfaceBlockEntity interfaceEntity = null;

            boolean core_found = false;

            for (int x = -16; x <= 16 && !core_found; x++) {
                for (int y = -16; y <= 16 && !core_found; y++) {
                    for (int z = -16; z <= 16 && !core_found; z++) {
                        BlockPos currentPos = playerPos.offset(x, y, z);

                        BlockState blockState = pLevel.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                            core_found = true;
                            interfaceEntity = (VortexInterfaceBlockEntity) pLevel.getBlockEntity(currentPos);
                        }
                    }
                }
            }

            if (interfaceEntity != null) {
                if (interfaceEntity.data.get(2) == player.getScoreboardName().hashCode()) {
                    interfaceEntity.setSignJ(signComponent.getString());
                    source.sendSuccess(() -> Component.literal("Set sign text to: " + signComponent.getString()).withStyle(ChatFormatting.AQUA), false);
                }
                else {
                    source.sendFailure(Component.literal("You do not own this TARDIS."));
                }
            }
            else {
                source.sendFailure(Component.literal("Core is not in range."));
            }
        }
        return 1;
    }
}
