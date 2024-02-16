package net.plaaasma.vortexmod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.AngelBlockEntity;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.mapdata.DimensionMapData;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

import java.util.function.Supplier;

public class ServerboundAngelSeenPacket {
    private final BlockPos angel_pos;

    public ServerboundAngelSeenPacket(BlockPos angel_pos) {
        this.angel_pos = angel_pos;
    }

    public ServerboundAngelSeenPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.angel_pos);
    }

    public void handle(Supplier<NetworkEvent.ServerCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();
        ServerLevel contextLevel = realContext.getSender().serverLevel();
        AngelBlockEntity angelBlockEntity = (AngelBlockEntity) contextLevel.getBlockEntity(angel_pos);
        angelBlockEntity.data.set(0, 1);

        realContext.setPacketHandled(true);
    }
}
