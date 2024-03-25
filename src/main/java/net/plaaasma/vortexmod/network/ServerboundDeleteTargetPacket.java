package net.plaaasma.vortexmod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.KeypadBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.mapdata.DimensionMapData;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.mapdata.RotationMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

import java.util.function.Supplier;

public class ServerboundDeleteTargetPacket {
    private final BlockPos from_pos;
    private final String save_name;
    private final Boolean targetScreen;

    public ServerboundDeleteTargetPacket(BlockPos from_pos, String save_pos, Boolean targetScreen) {
        this.from_pos = from_pos;
        this.save_name = save_pos;
        this.targetScreen = targetScreen;
    }

    public ServerboundDeleteTargetPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readUtf(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.from_pos);
        buffer.writeUtf(this.save_name);
        buffer.writeBoolean(this.targetScreen);
    }

    public void handle(Supplier<NetworkEvent.ServerCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();
        if (!this.targetScreen) {
            MinecraftServer minecraftserver = realContext.getSender().getServer();
            ServerLevel tardis_dim = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
            ServerLevel vortex = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
            ServerPlayer player = realContext.getSender();
            LocationMapData coord_data = LocationMapData.get(vortex);
            RotationMapData rotation_data = RotationMapData.get(vortex);
            DimensionMapData dim_data = DimensionMapData.get(tardis_dim);
            ServerLevel level = realContext.getSender().serverLevel();

            boolean core_found = false;

            BlockPos corePos = this.from_pos;
            VortexInterfaceBlockEntity vortexInterfaceBlockEntity = null;

            for (int _x = -16; _x <= 16 && !core_found; _x++) {
                for (int _y = -16; _y <= 16 && !core_found; _y++) {
                    for (int _z = -16; _z <= 16 && !core_found; _z++) {
                        BlockPos currentPos = this.from_pos.offset(_x, _y, _z);

                        BlockState blockState = level.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                            vortexInterfaceBlockEntity = (VortexInterfaceBlockEntity) level.getBlockEntity(currentPos);
                            core_found = true;
                            corePos = currentPos;
                        }
                    }
                }
            }

            boolean has_components = false;
            boolean has_keypad = false;
            boolean has_designator = false;

            CoordinateDesignatorBlockEntity designatorEntity = null;
            KeypadBlockEntity keypadBlockEntity = null;

            for (int _x = -16; _x <= 16 && !has_components; _x++) {
                for (int _y = -16; _y <= 16 && !has_components; _y++) {
                    for (int _z = -16; _z <= 16 && !has_components; _z++) {
                        BlockPos currentPos = corePos.offset(_x, _y, _z);

                        BlockState blockState = level.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.KEYPAD_BLOCK.get()) {
                            keypadBlockEntity = (KeypadBlockEntity) level.getBlockEntity(currentPos);
                            has_keypad = true;
                        } else if (blockState.getBlock() == ModBlocks.COORDINATE_BLOCK.get()) {
                            designatorEntity = (CoordinateDesignatorBlockEntity) level.getBlockEntity(currentPos);
                            has_designator = true;
                        }
                        if (has_keypad && has_designator) {
                            has_components = true;
                        }
                    }
                }
            }

            if (core_found && has_components && designatorEntity != null) {
                String dataKey = player.getScoreboardName() + this.save_name;
                if (coord_data.getDataMap().containsKey(dataKey)) {
                    BlockPos savedPos = coord_data.getDataMap().get(dataKey);
                    String savedDimName = dim_data.getDataMap().get(dataKey);

                    coord_data.getDataMap().remove(dataKey);
                    rotation_data.getDataMap().remove(dataKey);
                    dim_data.getDataMap().remove(dataKey);
                    realContext.getSender().displayClientMessage(Component.literal("Deleting " + this.save_name + ". (" + savedPos.getX() + " " + savedPos.getY() + " " + savedPos.getZ() + " | " + savedDimName + ")"), false);

                    coord_data.setDirty();
                    rotation_data.setDirty();
                    dim_data.setDirty();

                    keypadBlockEntity.coordData = coord_data.getDataMap();
                    keypadBlockEntity.dimData = dim_data.getDataMap();
                    PacketHandler.sendToAllClients(new ClientboundTargetMapPacket(level.dimension().location().getPath(), this.from_pos, coord_data.getDataMap(), dim_data.getDataMap()));
                } else {
                    realContext.getSender().displayClientMessage(Component.literal("You do not have a saved destination called " + this.save_name + ", you can list your destinations with /tardis list"), false);
                }
            } else {
                if (!core_found) {
                    realContext.getSender().displayClientMessage(Component.literal("Core is not in range.").withStyle(ChatFormatting.RED), false);
                }
                if (!has_components) {
                    realContext.getSender().displayClientMessage(Component.literal("Coordinate components not in range. (Keypad and Designator)").withStyle(ChatFormatting.RED), false);
                }
            }
        }

        realContext.setPacketHandled(true);
    }
}
