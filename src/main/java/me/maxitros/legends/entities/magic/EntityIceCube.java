package me.maxitros.legends.entities.magic;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class EntityIceCube extends Entity {
    private IBlockState fallTile;
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean dontSetBlock;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0F;
    int maxlifetime;
    int lifetime;
    private String entId;
    private byte lvl;
    private float damage;
    public NBTTagCompound tileEntityData;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.<BlockPos>createKey(EntityIceCube.class, DataSerializers.BLOCK_POS);

    public EntityIceCube(World worldIn) {
        super(worldIn);
        this.setEntityBoundingBox(new AxisAlignedBB(this.posX, this.posY, this.posZ,this.posX+1,this.posY+1,this.posZ+1));
        this.maxlifetime = 20;
        this.lifetime = 20;
    }
    public void SetLifeTime(int time)
    {
        this.maxlifetime = time;
        this.lifetime = time;
    }

    public EntityIceCube(World worldIn, double x, double y, double z, IBlockState fallingBlockState, int lifetime, String entId, byte lvl, float damage) {
        super(worldIn);
        this.fallTile = fallingBlockState;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.setPosition(x, y + (double) ((1.0F - this.height) / 2.0F), z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.setOrigin(new BlockPos(this));
        this.maxlifetime = lifetime;
        this.lifetime = maxlifetime;
        this.entId= entId;
        this.lvl = lvl;
        this.damage = damage;
        this.noClip = false;
        this.setEntityBoundingBox(new AxisAlignedBB(x, y, z,x+1,y+1,z+1));
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    public void setOrigin(BlockPos p_184530_1_) {
        this.dataManager.set(ORIGIN, p_184530_1_);
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
    public void applyEntityCollision(Entity entityIn) {
        if (!entityIn.noClip && !this.noClip)
        {

            double d0 = entityIn.posX - this.posX;
            double d1 = entityIn.posZ - this.posZ;
            double d2 = MathHelper.absMax(d0, d1);

            if (d2 >= 0.009999999776482582D)
            {

                entityIn.addVelocity(-entityIn.motionX, -entityIn.motionY, -entityIn.motionZ);

            }
        }
    }

    @SideOnly(Side.CLIENT)
    public BlockPos getOrigin() {
        return (BlockPos) this.dataManager.get(ORIGIN);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
        this.dataManager.register(ORIGIN, BlockPos.ORIGIN);
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }


    private HashSet<EntityLivingBase> entities = new HashSet<>();
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        if(!world.isRemote) {
            if (maxlifetime - lifetime < 10) {
                for(EntityLivingBase base: world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox(), x->!x.getCachedUniqueIdString().equals(entId)))
                    entities.add(base);

            }
            if(maxlifetime-lifetime==10)
            {
                for(EntityLivingBase base: entities)
                    base.attackEntityFrom(DamageSource.GENERIC, damage);
            }
        }
        if(lifetime<=0)
            this.setDead();
        lifetime--;
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

    }

    public void fall(float distance, float damageMultiplier) {

    }

    public static void registerFixesFallingBlock(DataFixer fixer) {
    }

    @Override
    protected void doBlockCollisions() {
        super.doBlockCollisions();
    }

    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("lifetime",lifetime);
        compound.setInteger("maxlifetime",maxlifetime);
        compound.setString("entId",entId);
        compound.setByte("lvl", lvl);
        compound.setFloat("damage",damage);
        Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.AIR;
        ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(block);
        compound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
        compound.setByte("Data", (byte) block.getMetaFromState(this.fallTile));
        compound.setInteger("Time", this.fallTime);
        compound.setBoolean("DropItem", this.shouldDropItem);
        compound.setBoolean("HurtEntities", this.hurtEntities);
        compound.setFloat("FallHurtAmount", this.fallHurtAmount);
        compound.setInteger("FallHurtMax", this.fallHurtMax);

        if (this.tileEntityData != null) {
            compound.setTag("TileEntityData", this.tileEntityData);
        }
    }

    protected void readEntityFromNBT(NBTTagCompound compound) {
        lifetime = compound.getInteger("lifetime");
        maxlifetime = compound.getInteger("maxlifetime");
        int i = compound.getByte("Data") & 255;
        entId = compound.getString("entId");
        lvl = compound.getByte("lvl");
        damage = compound.getFloat("damage");
        if (compound.hasKey("Block", 8)) {
            this.fallTile = Block.getBlockFromName(compound.getString("Block")).getStateFromMeta(i);
        } else if (compound.hasKey("TileID", 99)) {
            this.fallTile = Block.getBlockById(compound.getInteger("TileID")).getStateFromMeta(i);
        } else {
            this.fallTile = Block.getBlockById(compound.getByte("Tile") & 255).getStateFromMeta(i);
        }

        this.fallTime = compound.getInteger("Time");
        Block block = this.fallTile.getBlock();

        if (compound.hasKey("HurtEntities", 99)) {
            this.hurtEntities = compound.getBoolean("HurtEntities");
            this.fallHurtAmount = compound.getFloat("FallHurtAmount");
            this.fallHurtMax = compound.getInteger("FallHurtMax");
        } else if (block == Blocks.ANVIL) {
            this.hurtEntities = true;
        }

        if (compound.hasKey("DropItem", 99)) {
            this.shouldDropItem = compound.getBoolean("DropItem");
        }

        if (compound.hasKey("TileEntityData", 10)) {
            this.tileEntityData = compound.getCompoundTag("TileEntityData");
        }

        if (block == null || block.getDefaultState().getMaterial() == Material.AIR) {
            this.fallTile = Blocks.SAND.getDefaultState();
        }
    }

    public void setHurtEntities(boolean p_145806_1_) {
        this.hurtEntities = p_145806_1_;
    }

    public void addEntityCrashInfo(CrashReportCategory category) {
        super.addEntityCrashInfo(category);

        if (this.fallTile != null) {
            Block block = this.fallTile.getBlock();
            category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
            category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(this.fallTile)));
        }
    }

    @SideOnly(Side.CLIENT)
    public World getWorldObj() {
        return this.world;
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    @Nullable
    public IBlockState getBlock() {
        return this.fallTile;
    }

    public boolean ignoreItemEntityData() {
        return true;
    }
}
