package net.plaaasma.vortexmod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.entities.custom.AngelEntity;
import net.plaaasma.vortexmod.sound.ModSounds;

import java.util.UUID;
import java.util.function.Supplier;

public class ServerboundAngelSeenPacket {
    private final UUID angel_uuid;
    private final String from_dimension;

    public ServerboundAngelSeenPacket(UUID angel_uuid, String from_dimension) {
        this.angel_uuid = angel_uuid;
        this.from_dimension = from_dimension;
    }

    public ServerboundAngelSeenPacket(FriendlyByteBuf buffer) {
        this(buffer.readUUID(), buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.angel_uuid);
        buffer.writeUtf(this.from_dimension);
    }

    public void handle(Supplier<NetworkEvent.ServerCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();
        MinecraftServer minecraftServer = realContext.getSender().getServer();
        ServerLevel level = null;
        for (ServerLevel cLevel : minecraftServer.getAllLevels()) {
            if (cLevel.dimension().location().getPath().equals(this.from_dimension)) {
                level = cLevel;
            }
        }

        AngelEntity angelEntity = (AngelEntity) level.getEntity(this.angel_uuid);
        angelEntity.setObserved(true);

        realContext.setPacketHandled(true);
    }
}
