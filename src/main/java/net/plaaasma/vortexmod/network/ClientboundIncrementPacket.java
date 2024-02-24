package net.plaaasma.vortexmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.custom.CoordinateDesignatorBlock;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.MonitorBlockEntity;

import java.util.Map;
import java.util.function.Supplier;

public class ClientboundIncrementPacket {
    private final BlockPos fromPos;
    private final Integer increment;
    private final String levelName;

    public ClientboundIncrementPacket(BlockPos fromPos, Integer increment, String levelName) {
        this.fromPos = fromPos;
        this.increment = increment;
        this.levelName = levelName;
    }

    public ClientboundIncrementPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readInt(), buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.fromPos);
        buffer.writeInt(this.increment);
        buffer.writeUtf(this.levelName);
    }

    public void handle(Supplier<NetworkEvent.ClientCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();

        Minecraft client = Minecraft.getInstance();
        ClientLevel clientLevel = client.level;

        if (clientLevel.dimension().location().getPath().equals(this.levelName)) {
            CoordinateDesignatorBlockEntity designatorBlockEntity = (CoordinateDesignatorBlockEntity) clientLevel.getBlockEntity(this.fromPos);

            designatorBlockEntity.data.set(4, this.increment);
        }

        realContext.setPacketHandled(true);
    }
}
