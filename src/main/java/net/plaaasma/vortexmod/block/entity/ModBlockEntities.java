package net.plaaasma.vortexmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VortexMod.MODID);

    public static final RegistryObject<BlockEntityType<VortexInterfaceBlockEntity>> VORTEX_INTERFACE_BE =
            BLOCK_ENTITIES.register("vortex_interface_be", () ->
                    BlockEntityType.Builder.of(VortexInterfaceBlockEntity::new,
                            ModBlocks.INTERFACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoordinateDesignatorBlockEntity>> COORDINATE_DESIGNATOR_BE =
            BLOCK_ENTITIES.register("coordinate_designator_be", () ->
                    BlockEntityType.Builder.of(CoordinateDesignatorBlockEntity::new,
                            ModBlocks.COORDINATE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<KeypadBlockEntity>> TARDIS_KEYPAD_BE =
            BLOCK_ENTITIES.register("tardis_keypad_be", () ->
                    BlockEntityType.Builder.of(KeypadBlockEntity::new,
                            ModBlocks.KEYPAD_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SizeManipulatorBlockEntity>> SIZE_MANIPULATOR_BE =
            BLOCK_ENTITIES.register("size_manipulator_be", () ->
                    BlockEntityType.Builder.of(SizeManipulatorBlockEntity::new,
                            ModBlocks.SIZE_MANIPULATOR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EqualizerBlockEntity>> EQUALIZER_BE =
            BLOCK_ENTITIES.register("equalizer_be", () ->
                    BlockEntityType.Builder.of(EqualizerBlockEntity::new,
                            ModBlocks.EQUALIZER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TardisBlockEntity>> TARDIS_BE =
            BLOCK_ENTITIES.register("tardis_be", () ->
                    BlockEntityType.Builder.of(TardisBlockEntity::new,
                            ModBlocks.TARDIS_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ScannerBlockEntity>> SCANNER_BE =
            BLOCK_ENTITIES.register("scanner_be", () ->
                    BlockEntityType.Builder.of(ScannerBlockEntity::new,
                            ModBlocks.SCANNER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<BiometricBlockEntity>> BIOMETRIC_BLOCK_BE =
            BLOCK_ENTITIES.register("biometric_be", () ->
                    BlockEntityType.Builder.of(BiometricBlockEntity::new,
                            ModBlocks.BIOMETRIC_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MonitorBlockEntity>> MONITOR_BE =
            BLOCK_ENTITIES.register("monitor_be", () ->
                    BlockEntityType.Builder.of(MonitorBlockEntity::new,
                            ModBlocks.MONITOR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<AngelBlockEntity>> ANGEL_BE =
            BLOCK_ENTITIES.register("angel_be", () ->
                    BlockEntityType.Builder.of(AngelBlockEntity::new,
                            ModBlocks.ANGEL_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
