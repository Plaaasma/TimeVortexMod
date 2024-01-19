package net.plaaasma.vortexmod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.KeypadBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ClientboundTargetMapPacket {
    private String dimension = "";
    private final BlockPos targetPos;
    private final Map<String, BlockPos> coordData;
    private final Map<String, String> dimData;

    public ClientboundTargetMapPacket(String dimenison, BlockPos targetPos, Map<String, BlockPos> coordData, Map<String, String> dimData) {
        this.dimension = dimenison;
        this.targetPos = targetPos;
        this.coordData = coordData;
        this.dimData = dimData;
    }

    public ClientboundTargetMapPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readBlockPos(), buffer.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readBlockPos), buffer.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.dimension);
        buffer.writeBlockPos(this.targetPos);
        buffer.writeMap(this.coordData, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeBlockPos);
        buffer.writeMap(this.dimData, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
    }

    public void handle(Supplier<NetworkEvent.ClientCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();

        Minecraft client = Minecraft.getInstance();
        ClientLevel clientLevel = client.level;

        KeypadBlockEntity keypadBlockEntity = (KeypadBlockEntity) clientLevel.getBlockEntity(this.targetPos);
        keypadBlockEntity.coordData = this.coordData;
        keypadBlockEntity.dimData = this.dimData;

        realContext.setPacketHandled(true);
    }
}
