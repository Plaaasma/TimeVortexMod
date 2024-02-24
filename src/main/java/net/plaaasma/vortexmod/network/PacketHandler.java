package net.plaaasma.vortexmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.plaaasma.vortexmod.VortexMod;

public class PacketHandler {

    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(VortexMod.MODID, "main"))
            .networkProtocolVersion(() -> NetworkConstants.NETVERSION)
            .clientAcceptedVersions(a->true)
            .serverAcceptedVersions(a->true)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ServerboundTargetPacket.class, 0)
            .encoder(ServerboundTargetPacket::encode)
            .decoder(ServerboundTargetPacket::new)
            .consumerMainThread(ServerboundTargetPacket::handle)
            .add();
        INSTANCE.messageBuilder(ServerboundSaveTargetPacket.class, 1)
                .encoder(ServerboundSaveTargetPacket::encode)
                .decoder(ServerboundSaveTargetPacket::new)
                .consumerMainThread(ServerboundSaveTargetPacket::handle)
                .add();
        INSTANCE.messageBuilder(ServerboundDeleteTargetPacket.class, 2)
                .encoder(ServerboundDeleteTargetPacket::encode)
                .decoder(ServerboundDeleteTargetPacket::new)
                .consumerMainThread(ServerboundDeleteTargetPacket::handle)
                .add();
        INSTANCE.messageBuilder(ServerboundAngelSeenPacket.class, 3)
                .encoder(ServerboundAngelSeenPacket::encode)
                .decoder(ServerboundAngelSeenPacket::new)
                .consumerMainThread(ServerboundAngelSeenPacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientboundTargetMapPacket.class, 4)
                .encoder(ClientboundTargetMapPacket::encode)
                .decoder(ClientboundTargetMapPacket::new)
                .consumerMainThread(ClientboundTargetMapPacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientboundDimListPacket.class, 5)
                .encoder(ClientboundDimListPacket::encode)
                .decoder(ClientboundDimListPacket::new)
                .consumerMainThread(ClientboundDimListPacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientboundMonitorDataPacket.class, 6)
                .encoder(ClientboundMonitorDataPacket::encode)
                .decoder(ClientboundMonitorDataPacket::new)
                .consumerMainThread(ClientboundMonitorDataPacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientboundIncrementPacket.class, 7)
                .encoder(ClientboundIncrementPacket::encode)
                .decoder(ClientboundIncrementPacket::new)
                .consumerMainThread(ClientboundIncrementPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
