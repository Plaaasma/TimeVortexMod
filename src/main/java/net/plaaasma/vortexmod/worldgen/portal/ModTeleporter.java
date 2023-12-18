package net.plaaasma.vortexmod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.plaaasma.vortexmod.block.ModBlocks;

import java.util.function.Function;

public class ModTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;

    public ModTeleporter(BlockPos pos) {
        thisPos = pos;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);

        entity.teleportTo(thisPos.getX(), thisPos.getY(), thisPos.getZ());

        return entity;
    }
}
