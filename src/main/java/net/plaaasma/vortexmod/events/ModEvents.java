package net.plaaasma.vortexmod.events;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.commands.SetCoordinateCommand;

@Mod.EventBusSubscriber(modid = VortexMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetCoordinateCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if (event.getOriginal().level().isClientSide()) {
            event.getEntity().getPersistentData().putIntArray(VortexMod.MODID + "targetpos",
                    event.getOriginal().getPersistentData().getIntArray(VortexMod.MODID + "targetpos"));
        }
    }
}
