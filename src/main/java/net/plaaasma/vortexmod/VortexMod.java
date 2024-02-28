package net.plaaasma.vortexmod;

import com.mojang.logging.LogUtils;
import dan200.computercraft.impl.ComputerCraftAPIService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.*;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.entities.client.renderers.*;
import net.plaaasma.vortexmod.item.ModCreativeModeTabs;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.screen.custom.screen.KeypadScreen;
import net.plaaasma.vortexmod.sound.ModSounds;
import net.plaaasma.vortexmod.screen.ModMenuTypes;
import net.plaaasma.vortexmod.screen.custom.screen.SizeManipulatorScreen;
import org.slf4j.Logger;;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VortexMod.MODID)
public class VortexMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "vortexmod";

    // Directly reference a slf4j logger
    public static final Logger P_LOGGER = LogUtils.getLogger();
    private static final Logger LOGGER = LogUtils.getLogger();

    public VortexMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModEntities.register(modEventBus);

        ModSounds.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            EntityRenderers.register(ModEntities.BLUE_TRADER.get(), LostTravelerRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_TRADER.get(), LostTravelerRenderer::new);
            EntityRenderers.register(ModEntities.PURPLE_TRADER.get(), LostTravelerRenderer::new);
            EntityRenderers.register(ModEntities.BLACK_TRADER.get(), LostTravelerRenderer::new);

            EntityRenderers.register(ModEntities.GOLD_DALEK.get(), DalekRenderer::new);
            EntityRenderers.register(ModEntities.SILVER_DALEK.get(), DalekRenderer::new);
            EntityRenderers.register(ModEntities.BLACK_DALEK.get(), DalekRenderer::new);
            EntityRenderers.register(ModEntities.SILVER_BLACK_DALEK.get(), DalekRenderer::new);

            EntityRenderers.register(ModEntities.LASER_ENTITY.get(), LaserRenderer::new);

            EntityRenderers.register(ModEntities.TARDIS.get(), TardisRenderer::new);

            EntityRenderers.register(ModEntities.ANGEL.get(), AngelRenderer::new);

            MenuScreens.register(ModMenuTypes.SIZE_MANIPULATOR_MENU.get(), SizeManipulatorScreen::new);
            MenuScreens.register(ModMenuTypes.KEYPAD_MENU.get(), KeypadScreen::new);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SIZE_MANIPULATOR_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.EQUALIZER_BLOCK.get(), RenderType.translucent());
        }
    }
}
