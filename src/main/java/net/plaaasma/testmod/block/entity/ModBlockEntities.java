package net.plaaasma.testmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.testmod.TestMod;
import net.plaaasma.testmod.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TestMod.MODID);

    public static final RegistryObject<BlockEntityType<TpStationBlockEntity>> TP_STATION_BE =
            BLOCK_ENTITIES.register("tp_station_be", () ->
                    BlockEntityType.Builder.of(TpStationBlockEntity::new,
                            ModBlocks.TELEPORTATION_STATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
