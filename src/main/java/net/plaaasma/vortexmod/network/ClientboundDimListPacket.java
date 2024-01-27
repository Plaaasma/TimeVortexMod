package net.plaaasma.vortexmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.entity.KeypadBlockEntity;

import java.util.Map;
import java.util.function.Supplier;

public class ClientboundDimListPacket {
    private String dimension = "";
    private final BlockPos targetPos;
    private final Map<String, String> dimData;

    public ClientboundDimListPacket(String dimenison, BlockPos targetPos, Map<String, String> dimData) {
        this.dimension = dimenison;
        this.targetPos = targetPos;
        this.dimData = dimData;
    }

    public ClientboundDimListPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readBlockPos(), buffer.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.dimension);
        buffer.writeBlockPos(this.targetPos);
        buffer.writeMap(this.dimData, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
    }

    public void handle(Supplier<NetworkEvent.ClientCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();

        Minecraft client = Minecraft.getInstance();
        ClientLevel clientLevel = client.level;

        KeypadBlockEntity keypadBlockEntity = (KeypadBlockEntity) clientLevel.getBlockEntity(this.targetPos);
        keypadBlockEntity.serverLevels = this.dimData.values().stream().toList();

        realContext.setPacketHandled(true);
    }
}
