package net.plaaasma.vortexmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.plaaasma.vortexmod.VortexMod;

public class PacketHandler {

    static String version = Minecraft.getInstance().getLaunchedVersion();
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(VortexMod.MODID, "main"))
            .networkProtocolVersion(() -> version)
            .clientAcceptedVersions(version::equals).serverAcceptedVersions(version::equals)
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
        INSTANCE.messageBuilder(ClientboundTargetMapPacket.class, 2)
                .encoder(ClientboundTargetMapPacket::encode)
                .decoder(ClientboundTargetMapPacket::new)
                .consumerMainThread(ClientboundTargetMapPacket::handle)
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
