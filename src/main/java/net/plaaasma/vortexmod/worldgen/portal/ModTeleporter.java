package net.plaaasma.vortexmod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.plaaasma.vortexmod.block.ModBlocks;

import java.util.function.Function;

public class ModTeleporter implements ITeleporter {
    public static Vec3 thisPos = Vec3.ZERO;
    public static Direction thisDir = Direction.EAST;

    public ModTeleporter(Vec3 pos) {
        thisPos = pos;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);

        entity.teleportTo(thisPos.x(), thisPos.y(), thisPos.z());

        return entity;
    }

    @Override
    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
        return false;
    }
}
