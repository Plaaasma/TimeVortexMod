package net.plaaasma.vortexmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VortexMod.MODID);

    public static final RegistryObject<CreativeModeTab> VORTEX_TAB = CREATIVE_MODE_TABS.register("vortex_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.INTERFACE_BLOCK.get()))
                    .title(Component.translatable("creativetab.vortex_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CHEESE.get());
                        pOutput.accept(ModItems.SIZE_UPGRADE.get());
                        pOutput.accept(ModItems.EUCLIDEAN_UPGRADE.get());
                        pOutput.accept(ModItems.WRENCH.get());
                        pOutput.accept(ModBlocks.INTERFACE_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.THROTTLE_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.COORDINATE_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.KEYPAD_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.SIZE_MANIPULATOR_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.EQUALIZER_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.SCANNER_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.GROUNDING_BLOCK.get().asItem());
                        pOutput.accept(ModBlocks.DOOR_BLOCK.get().asItem());
                        pOutput.accept(ModItems.BLUE_TRADER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.ORANGE_TRADER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.PURPLE_TRADER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.BLACK_TRADER_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
