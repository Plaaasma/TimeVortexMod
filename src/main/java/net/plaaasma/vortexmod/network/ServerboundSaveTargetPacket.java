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
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.mapdata.DimensionMapData;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

import java.util.function.Supplier;

public class ServerboundSaveTargetPacket {
    private final BlockPos from_pos;
    private final String save_name;
    private final Boolean is_save;

    public ServerboundSaveTargetPacket(BlockPos from_pos, String save_pos, Boolean is_save) {
        this.from_pos = from_pos;
        this.save_name = save_pos;
        this.is_save = is_save;
    }

    public ServerboundSaveTargetPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readUtf(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.from_pos);
        buffer.writeUtf(this.save_name);
        buffer.writeBoolean(this.is_save);
    }

    public void handle(Supplier<NetworkEvent.ServerCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();
        if (this.is_save) {
            MinecraftServer minecraftserver = realContext.getSender().getServer();
            ServerLevel tardis_dim = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
            ServerLevel vortex = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
            ServerPlayer player = realContext.getSender();
            LocationMapData coord_data = LocationMapData.get(vortex);
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

            for (int _x = -16; _x <= 16 && !has_components; _x++) {
                for (int _y = -16; _y <= 16 && !has_components; _y++) {
                    for (int _z = -16; _z <= 16 && !has_components; _z++) {
                        BlockPos currentPos = corePos.offset(_x, _y, _z);

                        BlockState blockState = level.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.KEYPAD_BLOCK.get()) {
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
                int dim_hash = vortexInterfaceBlockEntity.data.get(10);

                Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();
                ServerLevel currentLevel = level;

                for (ServerLevel cLevel : serverLevels) {
                    if (cLevel.dimension().location().getPath().hashCode() == dim_hash) {
                        currentLevel = cLevel;
                    }
                }

                BlockPos targetVec = new BlockPos(vortexInterfaceBlockEntity.data.get(3), vortexInterfaceBlockEntity.data.get(4), vortexInterfaceBlockEntity.data.get(5));

                coord_data.getDataMap().put(player.getScoreboardName() + this.save_name, targetVec);
                dim_data.getDataMap().put(player.getScoreboardName() + this.save_name, currentLevel.dimension().location().getPath());

                realContext.getSender().displayClientMessage(Component.literal("Adding the current target coordinates (" + targetVec.getX() + " " + targetVec.getY() + " " + targetVec.getZ() +  " | " + currentLevel.dimension().location().getPath() + ") as " + this.save_name), false);
                PacketHandler.sendToAllClients(new ClientboundTargetMapPacket(level.dimension().location().getPath(), this.from_pos, coord_data.getDataMap(), dim_data.getDataMap()));
            } else {
                if (!core_found) {
                    realContext.getSender().displayClientMessage(Component.literal("Core is not in range.").withStyle(ChatFormatting.RED), false);
                }
                if (!has_components) {
                    realContext.getSender().displayClientMessage(Component.literal("Coordinate components not in range. (Keypad and Designator)").withStyle(ChatFormatting.RED), false);
                }
            }
        }
        else {
            MinecraftServer minecraftserver = realContext.getSender().getServer();
            ServerLevel tardis_dim = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
            ServerLevel vortex = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
            ServerPlayer player = realContext.getSender();
            LocationMapData coord_data = LocationMapData.get(vortex);
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

            for (int _x = -16; _x <= 16 && !has_components; _x++) {
                for (int _y = -16; _y <= 16 && !has_components; _y++) {
                    for (int _z = -16; _z <= 16 && !has_components; _z++) {
                        BlockPos currentPos = corePos.offset(_x, _y, _z);

                        BlockState blockState = level.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.KEYPAD_BLOCK.get()) {
                            has_keypad = true;
                        }
                        else if (blockState.getBlock() == ModBlocks.COORDINATE_BLOCK.get()) {
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
                if (coord_data.getDataMap().containsKey(player.getScoreboardName() + this.save_name)) {
                    BlockPos savedPos = coord_data.getDataMap().get(player.getScoreboardName() + this.save_name);
                    String savedDimName = dim_data.getDataMap().get(player.getScoreboardName() + this.save_name);
                    int savedDimHash = 0;
                    Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();

                    for (ServerLevel cLevel : serverLevels) {
                        if (cLevel.dimension().location().getPath().equals(savedDimName)) {
                            savedDimHash = cLevel.dimension().location().getPath().hashCode();
                        }
                    }

                    vortexInterfaceBlockEntity.data.set(14, 1);
                    vortexInterfaceBlockEntity.data.set(15, savedPos.getX());
                    vortexInterfaceBlockEntity.data.set(16, savedPos.getY());
                    vortexInterfaceBlockEntity.data.set(17, savedPos.getZ());
                    vortexInterfaceBlockEntity.data.set(18, savedDimHash);

                    realContext.getSender().displayClientMessage(Component.literal("Loading " + this.save_name + " to the designator. (" + savedPos.getX() + " " + savedPos.getY() + " " + savedPos.getZ() + " | " + savedDimName + ")"), false);
                }
                else {
                    realContext.getSender().displayClientMessage(Component.literal("You do not have a saved destination called " + this.save_name + ", you can list your destinations with /tardis list"), false);
                }
            }
            else {
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
