package net.plaaasma.vortexmod.events;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.commands.SetCoordinateCommand;
import net.plaaasma.vortexmod.commands.SetDimensionCommand;
import net.plaaasma.vortexmod.commands.SetRotationCommand;

@Mod.EventBusSubscriber(modid = VortexMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetCoordinateCommand(event.getDispatcher());
        new SetDimensionCommand(event.getDispatcher());
        new SetRotationCommand(event.getDispatcher());

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
        }
    }
}
