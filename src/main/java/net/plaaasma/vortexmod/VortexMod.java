package net.plaaasma.vortexmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.*;
import net.plaaasma.vortexmod.item.ModCreativeModeTabs;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.worldgen.biome.surface.ModSurfaceRules;
import net.plaaasma.vortexmod.screen.ModMenuTypes;
import net.plaaasma.vortexmod.screen.SizeManipulatorScreen;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VortexMod.MODID)
public class VortexMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "vortexmod";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    private final Pattern pattern = Pattern.compile("^TC:\\s*(-?\\d+(\\.\\d+)?\\s+){2}-?\\d+(\\.\\d+)?$");

    public VortexMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);

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

    @SubscribeEvent
    public void onChatMessage(ServerChatEvent event) {
        Player ePlayer = event.getPlayer();
        Level pLevel = ePlayer.level();
        String raw_message = event.getRawText();
        Matcher matcher = pattern.matcher(raw_message);
        BlockPos ePlayerPos = ePlayer.blockPosition();

        if (matcher.matches()) {
            String[] parts = raw_message.substring(4).trim().split("\\s+");
            int x = (int) Float.parseFloat(parts[0]);
            int y = (int) Float.parseFloat(parts[1]);
            int z = (int) Float.parseFloat(parts[2]);

            boolean core_found = false;

            BlockPos corePos = ePlayerPos;

            for (int _x = -5; _x <= 5 && !core_found; _x++) {
                for (int _y = -5; _y <= 5 && !core_found; _y++) {
                    for (int _z = -5; _z <= 5 && !core_found; _z++) {
                        BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                        BlockState blockState = pLevel.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.INTERFACE_BLOCK.get()) {
                            core_found = true;
                            corePos = currentPos;
                        }
                    }
                }
            }

            boolean has_keypad = false;

            for (int _x = -3; _x <= 3 && !has_keypad; _x++) {
                for (int _y = -3; _y <= 3 && !has_keypad; _y++) {
                    for (int _z = -3; _z <= 3 && !has_keypad; _z++) {
                        BlockPos currentPos = ePlayerPos.offset(_x, _y, _z);

                        BlockState blockState = pLevel.getBlockState(currentPos);
                        if (blockState.getBlock() == ModBlocks.KEYPAD_BLOCK.get()) {
                            has_keypad = true;
                        }
                    }
                }
            }
            if (core_found && has_keypad) {
                ModBlocks.needsUpdating.put(corePos.getX() + " " + corePos.getY() + " " + corePos.getZ(), x + " " + y + " " + z);
                ePlayer.displayClientMessage(Component.literal("Updating designator coordinates to: " + x + " " + y + " " + z), true);
            }
            event.setCanceled(true);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            MenuScreens.register(ModMenuTypes.SIZE_MANIPULATOR_MENU.get(), SizeManipulatorScreen::new);
        }
    }
}
