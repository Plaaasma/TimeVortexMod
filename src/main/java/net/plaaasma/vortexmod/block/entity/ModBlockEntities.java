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
    public static final RegistryObject<BlockEntityType<ThrottleBlockEntity>> VORTEX_THROTTLE_BE =
            BLOCK_ENTITIES.register("vortex_throttle_be", () ->
                    BlockEntityType.Builder.of(ThrottleBlockEntity::new,
                            ModBlocks.THROTTLE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<KeypadBlockEntity>> TARDIS_KEYPAD_BE =
            BLOCK_ENTITIES.register("tardis_keypad_be", () ->
                    BlockEntityType.Builder.of(KeypadBlockEntity::new,
                            ModBlocks.KEYPAD_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SizeManipulatorBlockEntity>> SIZE_MANIPULATOR_BE =
            BLOCK_ENTITIES.register("size_manipulator_be", () ->
                    BlockEntityType.Builder.of(SizeManipulatorBlockEntity::new,
                            ModBlocks.SIZE_MANIPULATOR_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
