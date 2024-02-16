package net.plaaasma.vortexmod.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
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

    // TARDIS PARTS
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
    public static final RegistryObject<Block> MONITOR_BLOCK = registerBlock("monitor_block",
            () -> new MonitorBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> ANGEL_BLOCK = registerBlock("weeping_angel",
            () -> new AngelBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    // ROUNDELS
    public static final RegistryObject<Block> OAK_ROUNDEL = registerBlock("oak_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_ROUNDEL = registerBlock("spruce_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> ACACIA_ROUNDEL = registerBlock("acacia_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> BIRCH_ROUNDEL = registerBlock("birch_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> CHERRY_ROUNDEL = registerBlock("cherry_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> DARK_OAK_ROUNDEL = registerBlock("dark_oak_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> JUNGLE_ROUNDEL = registerBlock("jungle_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> MANGROVE_ROUNDEL = registerBlock("mangrove_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> CRIMSON_ROUNDEL = registerBlock("crimson_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> WARPED_ROUNDEL = registerBlock("warped_roundel_block",
            () -> new RoundelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));

    // Skaro Blocks
    public static final RegistryObject<Block> SKARO_SAND = registerBlock("skaro_sand_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND).noOcclusion()));
    public static final RegistryObject<Block> SKARO_SAND_STONE = registerBlock("skaro_sand_stone_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        if (name.equals("door_block") || name.equals("tardis_sign_block") || name.contains("roundel")) {
            return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        } else {
            return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
