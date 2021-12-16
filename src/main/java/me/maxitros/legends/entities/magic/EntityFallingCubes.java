package me.maxitros.legends.entities.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class EntityFallingCubes extends Entity {

    private int x, y, z;

    private int lifetime = 240;
    private final Random random = new Random();
    private String entId;
    private byte lvl;
    private float damage;


    public EntityFallingCubes(World worldIn) {
        super(worldIn);
    }
    public EntityFallingCubes(World worldIn,double x, double y, double z, String entId, byte lvl, float damage) {
        super(worldIn);
        this.entId= entId;
        this.lvl = lvl;
        this.damage = damage;
        this.preventEntitySpawning = true;
        this.setSize(0.1F, 0.1F);
        this.setPosition(x, y + (double)((1.0F - this.height) / 2.0F), z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.setNoGravity(true);

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
        if(lifetime%20<=12&&!this.world.isRemote)
        {
            float x = random.nextInt(7)-3.5f + (float) this.posX;
            float z = random.nextInt(7)-3.5f+  (float) this.posZ;
            this.world.spawnEntity(new EntityFallingCube(this.world, x, this.posY+5,z, entId, lvl,damage));
        }
        lifetime--;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        entId = compound.getString("entId");
        lvl = compound.getByte("lvl");
        damage = compound.getFloat("damage");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setString("entId",entId);
        compound.setByte("lvl", lvl);
        compound.setFloat("damage",damage);
    }
}
