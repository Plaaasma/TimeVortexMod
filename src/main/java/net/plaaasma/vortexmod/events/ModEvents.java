package net.plaaasma.vortexmod.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.custom.VortexInterfaceBlock;
import net.plaaasma.vortexmod.commands.*;

@Mod.EventBusSubscriber(modid = VortexMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetCoordinateCommand(event.getDispatcher());
        new SetDimensionCommand(event.getDispatcher());
        new SetRotationCommand(event.getDispatcher());
        new SaveCoordinateCommand(event.getDispatcher());
        new LoadCoordinateCommand(event.getDispatcher());
        new DeleteCoordinateCommand(event.getDispatcher());
        new ListCoordinateCommand(event.getDispatcher());
        new SecurityCommand(event.getDispatcher());
        new ExteriorCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if (event.getOriginal().level().isClientSide()) {
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "targetpos",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "targetpos"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "targetdim",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "targetdim"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "targetrot",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "targetrot"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "saveddim",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "saveddim"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "savedpos",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "savedpos"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "savedname",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "savedname"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "targetname",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "targetname"));
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "deletename",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "deletename"));
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        ServerLevel serverLevel = (ServerLevel) event.getPlayer().level();
        BlockPos pPos = event.getPos();

        BlockState pState = event.getState();
        if (pState.getBlock() instanceof VortexInterfaceBlock) {
            BlockEntity blockEntity = serverLevel.getBlockEntity(pPos);

            if (blockEntity != null) {
                ItemStack droppedItem = new ItemStack(pState.getBlock()); // Create an ItemStack of the block
                blockEntity.saveToItem(droppedItem);

                // Spawn the ItemStack in the world
                double x = pPos.getX() + 0.5;
                double y = pPos.getY() + 0.5;
                double z = pPos.getZ() + 0.5;
                ItemEntity droppedEntity = new ItemEntity(serverLevel, x, y, z, droppedItem);
                serverLevel.addFreshEntity(droppedEntity);
            }
        }
    }
}
