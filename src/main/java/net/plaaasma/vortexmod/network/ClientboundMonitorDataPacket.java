package net.plaaasma.vortexmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.entity.KeypadBlockEntity;
import net.plaaasma.vortexmod.block.entity.MonitorBlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ClientboundMonitorDataPacket {
    private final BlockPos targetPos;
    private final Map<Integer, Integer> fromTag;
    private final String targetDim;
    private final String currentDim;

    public ClientboundMonitorDataPacket(BlockPos targetPos, Map<Integer, Integer> fromTag, String targetDim, String currentDim) {
        this.targetPos = targetPos;
        this.fromTag = fromTag;
        this.targetDim = targetDim;
        this.currentDim = currentDim;
    }

    public ClientboundMonitorDataPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readInt), buffer.readUtf(), buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.targetPos);
        buffer.writeMap(this.fromTag, FriendlyByteBuf::writeInt, FriendlyByteBuf::writeInt);
        buffer.writeUtf(this.targetDim);
        buffer.writeUtf(this.currentDim);
    }

    public void handle(Supplier<NetworkEvent.ClientCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();

        Minecraft client = Minecraft.getInstance();
        ClientLevel clientLevel = client.level;

        MonitorBlockEntity monitorBlockEntity = (MonitorBlockEntity) clientLevel.getBlockEntity(this.targetPos);

        Iterable<Integer> keyList = this.fromTag.keySet();

        for (Integer key : keyList) {
            monitorBlockEntity.data.set(key, this.fromTag.get(key));
        }

        monitorBlockEntity.target_dimension = this.targetDim;
        monitorBlockEntity.current_dimension = this.currentDim;

        realContext.setPacketHandled(true);
    }
}
