package net.plaaasma.vortexmod.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.custom.*;
import net.plaaasma.vortexmod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VortexMod.MODID);

    public static final RegistryObject<Block> THROTTLE_BLOCK = registerBlock("throttle_block",
            () -> new ThrottleBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> INTERFACE_BLOCK = registerBlock("interface_block",
            () -> new VortexInterfaceBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> COORDINATE_BLOCK = registerBlock("coordinate_block",
            () -> new CoordinateDesignatorBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> KEYPAD_BLOCK = registerBlock("keypad_block",
            () -> new KeypadBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> SIZE_MANIPULATOR_BLOCK = registerBlock("size_manipulator_block",
            () -> new SizeManipulatorBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> EQUALIZER_BLOCK = registerBlock("equalizer_block",
            () -> new EqualizerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> TARDIS_BLOCK = registerBlock("tardis_block",
            () -> new TardisBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> DOOR_BLOCK = registerBlock("door_block",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> SCANNER_BLOCK = registerBlock("scanner_block",
            () -> new ScannerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> GROUNDING_BLOCK = registerBlock("grounding_block",
            () -> new GroundingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> BIOMETRIC_BLOCK = registerBlock("biometric_module",
            () -> new BiometricBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> TARDIS_SIGN_BLOCK = registerBlock("tardis_sign_block",
            () -> new TardisSignBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        if (name.equals("door_block") || name.equals("tardis_sign_block")) {
            return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        } else {
            return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
