package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.plaaasma.vortexmod.entities.ModEntities;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RiftEntity extends Mob {
    public RiftEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Integer.MAX_VALUE)
                .add(Attributes.ARMOR_TOUGHNESS, Integer.MAX_VALUE)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.FOLLOW_RANGE, Integer.MAX_VALUE)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE)
                .add(Attributes.ATTACK_DAMAGE, 0D);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource != this.damageSources().genericKill()) {
            return false;
        }
        else {
            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected void pushEntities() { }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public void tick() {
        java.util.Random random = new java.util.Random();

        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.level().getGameTime() % 400 == 0) {
                List<Entity> entities = this.level().getEntities(this, new AABB(
                        this.blockPosition().getX() - 128, this.blockPosition().getY() - 128, this.blockPosition().getZ() - 128,
                        this.blockPosition().getX() + 128, this.blockPosition().getY() + 128, this.blockPosition().getZ() + 128
                ));

                int angelCount = 0;
                for (Entity entity : entities) {
                    if (entity instanceof AngelEntity) {
                        angelCount += 1;
                    }
                }

                if (angelCount < 3) {
                    AngelEntity angelEntity = ModEntities.ANGEL.get().spawn(serverLevel, new BlockPos(this.blockPosition().getX() + random.nextInt(-2, 2), this.blockPosition().getY(), this.blockPosition().getZ() + random.nextInt(-2, 2)), MobSpawnType.NATURAL);
                    serverLevel.addFreshEntity(angelEntity);
                }
            }
        }
        else {
            List<Vector3f> points = generateCrackPoints();
            Vector3f point = points.get(random.nextInt(0, points.size()));

            this.level().addParticle(
                    ParticleTypes.ENCHANT,
                    this.getX() + point.x(), this.getY() + point.y(), this.getZ() + point.z(),
                    0, 0, 0
            );
        }

        super.tick();
    }

    private float randNegToPos(Random random) {
        return random.nextFloat() / 2;
    }

    public List<Vector3f> generateCrackPoints() {
        Random random = new Random(this.getUUID().hashCode());
        boolean x_pos = false;
        boolean z_pos = false;

        if (random.nextFloat() < 0.5) {
            x_pos = true;
        }
        if (random.nextFloat() < 0.5) {
            z_pos = true;
        }

        List<Vector3f> points = new ArrayList<>();
        points.add(new Vector3f(x_pos ? randNegToPos(random) : -randNegToPos(random),
                (randNegToPos(random) - 0.25f) + 1.5f,
                z_pos ? randNegToPos(random) : -randNegToPos(random)));

        for (int i = 0; i < 4; i++) {
            Vector3f lastVector = points.get(i);
            points.add(new Vector3f(lastVector.x() + (x_pos ? randNegToPos(random) : -randNegToPos(random)),
                    lastVector.y() + (randNegToPos(random) - 0.25f),
                    lastVector.z() + (z_pos ? randNegToPos(random) : -randNegToPos(random))));
        }

        return points;
    }
}
