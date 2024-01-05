package net.plaaasma.vortexmod.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.client.DalekModel;
import net.plaaasma.vortexmod.entities.client.LostTravelerModel;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;

@Mod.EventBusSubscriber(modid = VortexMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.LOST_TRAVELER_LAYER, LostTravelerModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DALEK_LAYER, DalekModel::createBodyLayer);
    }
}
