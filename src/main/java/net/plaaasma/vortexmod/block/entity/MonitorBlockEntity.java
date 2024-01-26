package net.plaaasma.vortexmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.network.ClientboundMonitorDataPacket;
import net.plaaasma.vortexmod.network.PacketHandler;

import java.util.HashMap;
import java.util.Map;

public class MonitorBlockEntity extends BlockEntity {
    public final ContainerData data;

    private int target_x = 0;
    private int target_y = 0;
    private int target_z = 0;
    public String target_dimension = "";
    private int target_rot = 0;
    private int target_time = 0;

    private int remaining_time = 0;
    private int current_x = 0;
    private int current_y = 0;
    private int current_z = 0;
    public String current_dimension = "";
    private int current_rot = 0;


    public MonitorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MONITOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MonitorBlockEntity.this.target_x;
                    case 1 -> MonitorBlockEntity.this.target_y;
                    case 2 -> MonitorBlockEntity.this.target_z;
                    case 3 -> MonitorBlockEntity.this.target_rot;
                    case 4 -> MonitorBlockEntity.this.target_time;
                    case 5 -> MonitorBlockEntity.this.remaining_time;
                    case 6 -> MonitorBlockEntity.this.current_x;
                    case 7 -> MonitorBlockEntity.this.current_y;
                    case 8 -> MonitorBlockEntity.this.current_z;
                    case 9 -> MonitorBlockEntity.this.current_rot;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MonitorBlockEntity.this.target_x = pValue;
                    case 1 -> MonitorBlockEntity.this.target_y = pValue;
                    case 2 -> MonitorBlockEntity.this.target_z = pValue;
                    case 3 -> MonitorBlockEntity.this.target_rot = pValue;
                    case 4 -> MonitorBlockEntity.this.target_time = pValue;
                    case 5 -> MonitorBlockEntity.this.remaining_time = pValue;
                    case 6 -> MonitorBlockEntity.this.current_x = pValue;
                    case 7 -> MonitorBlockEntity.this.current_y = pValue;
                    case 8 -> MonitorBlockEntity.this.current_z = pValue;
                    case 9 -> MonitorBlockEntity.this.current_rot = pValue;
                }
            }

            @Override
            public int getCount() {
                return 10;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        CompoundTag vortexModData = pTag.getCompound(VortexMod.MODID);

        this.target_x = vortexModData.getInt("target_x");
        this.target_y = vortexModData.getInt("target_y");
        this.target_z = vortexModData.getInt("target_z");
        this.target_rot = vortexModData.getInt("target_rot");
        this.target_time = vortexModData.getInt("target_time");

        this.remaining_time = vortexModData.getInt("remaining_time");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("target_x", this.target_x);
        vortexModData.putInt("target_y", this.target_y);
        vortexModData.putInt("target_z", this.target_z);
        vortexModData.putInt("target_rot", this.target_rot);
        vortexModData.putInt("target_time", this.target_time);

        vortexModData.putInt("remaining_time", this.remaining_time);

        pTag.put(VortexMod.MODID, vortexModData);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        Map<Integer, Integer> dataMap = new HashMap<>();

        for (int i = 0; i < this.data.getCount(); i++) {
            dataMap.put(i, this.data.get(i));
        }

        PacketHandler.sendToAllClients(new ClientboundMonitorDataPacket(this.getBlockPos(), dataMap, this.target_dimension, this.current_dimension));


        setChanged(pLevel, pPos, pState);
    }
}
