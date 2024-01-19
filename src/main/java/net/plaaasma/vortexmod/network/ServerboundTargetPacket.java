package net.plaaasma.vortexmod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;

import java.util.function.Supplier;

public class ServerboundTargetPacket {
    private final BlockPos from_pos;
    private final String from_dimension;
    private final BlockPos to_pos;
    private final int to_rotation;
    private final String to_dimension;

    public ServerboundTargetPacket(BlockPos from_pos, String from_dimension, BlockPos to_pos, int to_rotation, String to_dimension) {
        this.from_pos = from_pos;
        this.from_dimension = from_dimension;
        this.to_pos = to_pos;
        this.to_dimension = to_dimension;
        this.to_rotation = to_rotation;
    }

    public ServerboundTargetPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readUtf(), buffer.readBlockPos(), buffer.readInt(), buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.from_pos);
        buffer.writeUtf(this.from_dimension);
        buffer.writeBlockPos(this.to_pos);
        buffer.writeInt(this.to_rotation);
        buffer.writeUtf(this.to_dimension);
    }

    public void handle(Supplier<NetworkEvent.ServerCustomPayloadEvent.Context> context) {
        NetworkEvent.Context realContext = context.get();
        MinecraftServer minecraftServer = realContext.getSender().getServer();
        ServerLevel level = null;
        ServerLevel toLevel = null;
        for (ServerLevel cLevel : minecraftServer.getAllLevels()) {
            if (cLevel.dimension().location().getPath().equals(this.from_dimension)) {
                level = cLevel;
            }
            if (cLevel.dimension().location().getPath().equals(this.to_dimension)) {
                toLevel = cLevel;
            }
        }

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
        int x = this.to_pos.getX();
        int y = this.to_pos.getY();
        int z = this.to_pos.getZ();
        if (core_found && has_components && designatorEntity != null) {
            if (x != -6632961) {
                designatorEntity.data.set(0, x);
            }
            else {
                x = designatorEntity.data.get(0);
            }
            if (y != -1537) {
                designatorEntity.data.set(1, y);
            }
            else {
                y = designatorEntity.data.get(1);
            }
            if (z != -6632961) {
                designatorEntity.data.set(2, z);
            }
            else {
                z = designatorEntity.data.get(2);
            }

            int toRotation = vortexInterfaceBlockEntity.data.get(12);
            if (this.to_rotation < 360 && this.to_rotation > -360) {
                toRotation = this.to_rotation;
                vortexInterfaceBlockEntity.data.set(12, this.to_rotation);
            }

            String toDimension;
            if (toLevel != null) {
                toDimension = toLevel.dimension().location().getPath();
            }
            else {
                toDimension = vortexInterfaceBlockEntity.getTargetDimensionJ();
            }

            vortexInterfaceBlockEntity.data.set(10, toDimension.hashCode());

            realContext.getSender().displayClientMessage(Component.literal("Updating target coordinates to: ")
                    .append(Component.literal(x + " " + y + " " + z + "\n").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("Updating target rotation to: "))
                    .append(Component.literal(toRotation + "\n").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("Updating target dimension to: "))
                    .append(Component.literal(toDimension).withStyle(ChatFormatting.GOLD)), false);
        }
        else {
            if (!core_found) {
                realContext.getSender().displayClientMessage(Component.literal("Core is not in range.").withStyle(ChatFormatting.RED), false);
            }
            if (!has_components) {
                realContext.getSender().displayClientMessage(Component.literal("Coordinate components not in range. (Keypad and Designator)").withStyle(ChatFormatting.RED), false);
            }
        }

        realContext.setPacketHandled(true);
    }
}
