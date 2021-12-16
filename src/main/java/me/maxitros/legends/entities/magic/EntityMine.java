package me.maxitros.legends.entities.magic;

import me.maxitros.legends.effects.ModPotionEffect;
import me.maxitros.legends.effects.PotionRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class EntityMine extends Entity {

    private int lifetime = 240;
    private final Random random = new Random();
    private String entId;
    private byte lvl;
    private float damage;


    public EntityMine(World worldIn) {
        super(worldIn);
        this.lifetime = 240;
        this.setSize(1F, 0.05F);
    }
    public void SetLifeTime(int time)
    {
        this.lifetime = time;
    }
    public EntityMine(World worldIn,double x, double y, double z, String entId, byte lvl, float damage, int lifetime) {
        super(worldIn);
        this.entId= entId;
        this.lvl = lvl;
        this.damage = damage;
        this.preventEntitySpawning = true;
        this.setSize(1F, 0.05F);
        this.setPosition(x, y+0.05, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.setNoGravity(true);
        this.lifetime = lifetime;
        this.setEntityBoundingBox(new AxisAlignedBB(x, y, z,x+1,y+0.05,z+1));

    }


    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {

    }
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.getEntityBoundingBox();
    }

    @Override
    protected void entityInit() {


    }




    @Override
    public void onUpdate() {
        super.onUpdate();
        if(lifetime==0)
        {
            this.setDead();
        }
        if(entId!=null&&entId.length()>0) {
            Optional<EntityLivingBase> base = this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.posX-0.5,this.posY,this.posZ-0.5,this.posX+0.5,this.posY+0.5,this.posZ+0.5), x -> !x.getCachedUniqueIdString().equals(entId)).stream().findAny();
            if (base.isPresent()) {
                if(!world.isRemote)
                    base.get().addPotionEffect(new ModPotionEffect(PotionRegistry.IceStunResultPotion, 20 * (10 + 10 * (lvl - 1)), lvl, 0));
                this.setDead();
            }
        }
        lifetime--;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        lifetime = compound.getInteger("lifetime");
        entId = compound.getString("entId");
        lvl = compound.getByte("lvl");
        damage = compound.getFloat("damage");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("lifetime",lifetime);
        compound.setString("entId",entId);
        compound.setByte("lvl", lvl);
        compound.setFloat("damage",damage);
    }
}
