package me.maxitros.legends.entities.magic;

import me.maxitros.legends.effects.ModPotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class EntityMagicArrow extends EntityArrow {


    final Random random = new Random();
    private final int lvl;
    private final float damage;
    private final ModPotionEffect effect;
    private int effectRange;
    private EnumParticleTypes particleFlightType;
    private EnumParticleTypes particleDamageType;
    private boolean hasRange;
    private EntityMagicArrow.ArrowType type;
    public EntityMagicArrow(World worldIn) {
        super(worldIn);
        lvl = 1;
        damage = 0.5f;
        effect = null;
        effectRange = 0;
        particleFlightType = EnumParticleTypes.SNOWBALL;
        particleDamageType = EnumParticleTypes.SNOWBALL;
        hasRange = false;
        type = ArrowType.FireArrow;
    }

    public EntityMagicArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        lvl = 1;
        damage = 0.5f;
        effect = null;
        effectRange = 0;
        particleFlightType = EnumParticleTypes.SNOWBALL;
        particleDamageType = EnumParticleTypes.SNOWBALL;
        hasRange = false;
        type = ArrowType.FireArrow;
    }
    public void setData(int effectRange, EnumParticleTypes particleFlightType, EnumParticleTypes particleDamageType, boolean hasRange,  EntityMagicArrow.ArrowType type)
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
    public EntityMagicArrow(World worldIn, EntityLivingBase shooter, int lvl, float damage, ModPotionEffect effect, int effectRange, EnumParticleTypes particleFlightType, EnumParticleTypes particleDamageType, boolean hasRange, ArrowType type) {
        super(worldIn, shooter);
        this.lvl = lvl;
        this.damage = damage;
        this.effect = effect;
        this.effectRange = effectRange;
        this.particleFlightType = particleFlightType;
        this.particleDamageType = particleDamageType;
        this.hasRange = hasRange;
        this.type = type;
        pickupStatus = PickupStatus.CREATIVE_ONLY;

    }

    public ModPotionEffect getEffect()
    {
        return effect;
    }
    public EntityMagicArrow.ArrowType getGrenadeType(){
        return type;
    }
    public int getLvl(){
        return lvl;
    }
    public float getModDamage(){
        return damage;
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public void setEnchantmentEffectsFromEntity(EntityLivingBase p_190547_1_, float p_190547_2_) {
    }
//public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.inGround&&this.world.isRemote)
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

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        super.onHit(raytraceResultIn);
        this.setDead();
        if(world.isRemote)
        {
            for(int i = 0; i<8; i++) {
                this.world.spawnParticle(particleDamageType,
                        this.posX + (random.nextFloat() - 0.5),
                        this.posY + (random.nextFloat() - 0.5),
                        this.posZ + (random.nextFloat() - 0.5),
                        (random.nextFloat() - 0.5) * 0.2,
                        (random.nextFloat() - 0.5) * 0.2,
                        (random.nextFloat() - 0.5) * 0.2);
            }
        }
    }

    @Override
    public boolean getIsCritical() {
        return false;
    }
    public enum ArrowType{
        FireArrow,
        IceArrow,
        LightningArrow,
        LightningArrow2
    }

}
