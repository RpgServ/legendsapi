package me.maxitros.legends.entities.magic;

import me.maxitros.legends.effects.ModPotionEffect;
import me.maxitros.legends.networking.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class EntityMagicGrenade extends EntityThrowable {

    final Random random = new Random();
    private final int lvl;
    private final float damage;
    private final ModPotionEffect effect;
    private int effectRange;
    private EnumParticleTypes particleFlightType;
    private EnumParticleTypes particleDamageType;
    private boolean hasRange;
    private EntityMagicGrenade.GrenadeType type;
    public EntityMagicGrenade(World worldIn) {
        super(worldIn);
        lvl = 1;
        damage = 0.5f;
        effect = null;
        effectRange = 0;
        particleFlightType = EnumParticleTypes.SNOWBALL;
        particleDamageType = EnumParticleTypes.SNOWBALL;
        hasRange = false;
        type = GrenadeType.Fireball;
    }

    public EntityMagicGrenade(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        lvl = 1;
        damage = 0.5f;
        effect = null;
        effectRange = 0;
        particleFlightType = EnumParticleTypes.SNOWBALL;
        particleDamageType = EnumParticleTypes.SNOWBALL;
        hasRange = false;
        type = GrenadeType.Fireball;
    }

    public EntityMagicGrenade(World worldIn, EntityLivingBase shooter, int lvl, float damage, ModPotionEffect effect, int effectRange, EnumParticleTypes particleFlightType, EnumParticleTypes particleDamageType, boolean hasRange,  EntityMagicGrenade.GrenadeType type) {
        super(worldIn, shooter);
        this.lvl = lvl;
        this.damage = damage;
        this.effect = effect;
        this.effectRange = effectRange;
        this.particleFlightType = particleFlightType;
        this.hasRange = hasRange;
        this.type = type;
        this.particleDamageType = particleDamageType;
    }
    public void setData(int effectRange, EnumParticleTypes particleFlightType, EnumParticleTypes particleDamageType, boolean hasRange,  EntityMagicGrenade.GrenadeType type)
    {
        this.effectRange = effectRange;
        this.particleFlightType = particleFlightType;
        this.hasRange = hasRange;
        this.type = type;
        this.particleDamageType = particleDamageType;
    }
    public int getEffectRange()
    {
        return effectRange;
    }
    public EnumParticleTypes getParticleFlightType()
    {
        return particleFlightType;
    }
    public EnumParticleTypes getParticleDamageType()
    {
        return particleDamageType;
    }
    public boolean isHasRange()
    {
        return hasRange;
    }
    public GrenadeType getGrenadeType(){
        return type;
    }
    public int getLvl(){
        return lvl;
    }
    public float getModDamage(){
        return damage;
    }

    @Override
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        super.shoot(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                this.world.spawnParticle(particleDamageType, this.posX, this.posY, this.posZ, random.nextFloat()-0.5*(hasRange?effectRange:1), random.nextFloat()-0.5*(hasRange?effectRange:1), random.nextFloat()-0.5*(hasRange?effectRange:1));
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.inGround&& this.world.isRemote)
        {
            this.world.spawnParticle(particleFlightType,
                    this.posX+(random.nextFloat()-0.5),
                    this.posY+(random.nextFloat()-0.5),
                    this.posZ+(random.nextFloat()-0.5),
                    (random.nextFloat()-0.5)*0.2,
                    (random.nextFloat()-0.5)*0.2,
                    (random.nextFloat()-0.5)*0.2);
            this.world.spawnParticle(particleFlightType,
                    this.posX+(random.nextFloat()-0.5),
                    this.posY+(random.nextFloat()-0.5),
                    this.posZ+(random.nextFloat()-0.5),
                    (random.nextFloat()-0.5)*0.2,
                    (random.nextFloat()-0.5)*0.2,
                    (random.nextFloat()-0.5)*0.2);
        }
    }

    protected void onImpact(RayTraceResult result)
    {
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
        if (!this.world.isRemote)
        {
            if(hasRange) {
                float addrange = (float)lvl/2f;
                for (EntityLivingBase player : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(
                        this.posX-effectRange-addrange,
                        this.posY-effectRange-addrange,
                        this.posZ-effectRange-addrange,
                        this.posX+effectRange+addrange,
                        this.posY+effectRange+addrange,
                        this.posZ+effectRange+addrange))) {
                    if(effect!=null)
                        player.addPotionEffect(effect);
                    player.attackEntityFrom(DamageSource.GENERIC, getModDamage());
                }
            }
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
    public enum GrenadeType{
        Fireball,
        SnowBall,
        LightingBall
    }
}
