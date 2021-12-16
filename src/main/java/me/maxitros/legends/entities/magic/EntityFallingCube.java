package me.maxitros.legends.entities.magic;

import com.google.common.collect.Lists;
import me.maxitros.legends.effects.ModPotionEffect;
import me.maxitros.legends.effects.PotionRegistry;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityFallingCube extends Entity
{
    private IBlockState fallTile = Blocks.MAGMA.getDefaultState();
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean dontSetBlock;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0F;
    public NBTTagCompound tileEntityData;
    private String entId;
    private byte lvl;
    private float damage;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.<BlockPos>createKey(EntityFallingCube.class, DataSerializers.BLOCK_POS);

    public EntityFallingCube(World worldIn)
    {
        super(worldIn);
    }

    public EntityFallingCube(World worldIn, double x, double y, double z, String entId, byte lvl, float damage)
    {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        y=y+5;
        this.setPosition(x, y + (double)((1.0F - this.height) / 2.0F), z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.setOrigin(new BlockPos(this));
        this.entId= entId;
        this.lvl = lvl;
        this.damage = damage;
        this.dontSetBlock = true;
        this.hurtEntities = true;
        this.shouldDropItem = false;
        this.onGround = false;
    }

    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    public void setOrigin(BlockPos p_184530_1_)
    {
        this.dataManager.set(ORIGIN, p_184530_1_);
    }

    @SideOnly(Side.CLIENT)
    public BlockPos getOrigin()
    {
        return (BlockPos)this.dataManager.get(ORIGIN);
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataManager.register(ORIGIN, BlockPos.ORIGIN);
    }

    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }


    @Override
    public void onRemovedFromWorld() {
        if(!world.isRemote)
        {

        }
        super.onRemovedFromWorld();

    }

    int deadTimer=50;

    public void onUpdate()
    {
        deadTimer--;
        if(deadTimer<=0)
            this.setDead();
        Block block = this.fallTile.getBlock();

        if (this.fallTile.getMaterial() == Material.AIR)
        {
            this.setDead();
        }
        else
        {
            if(this.onGround)
                this.setDead();
           // if(this.world.isRemote&& this.prevPosY == this.posY)
             //   this.setDead();
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;

            if (this.fallTime++ == 0)
            {
                BlockPos blockpos = new BlockPos(this);

                if (this.world.getBlockState(blockpos).getBlock() == block)
                {
                    this.world.setBlockToAir(blockpos);
                }
                else if (!this.world.isRemote)
                {
                 //   this.setDead();
                    return;
                }
            }

            if (!this.hasNoGravity())
            {
                this.motionY -= 0.03999999910593033D;
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            if (!this.world.isRemote)
            {
                BlockPos blockpos1 = new BlockPos(this);
                boolean flag = this.fallTile.getBlock() == Blocks.CONCRETE_POWDER;
                boolean flag1 = flag && this.world.getBlockState(blockpos1).getMaterial() == Material.WATER;
                double d0 = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;

                if (flag && d0 > 1.0D)
                {
                    RayTraceResult raytraceresult = this.world.rayTraceBlocks(new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ), new Vec3d(this.posX, this.posY, this.posZ), true);

                    if (raytraceresult != null && this.world.getBlockState(raytraceresult.getBlockPos()).getMaterial() == Material.WATER)
                    {
                        blockpos1 = raytraceresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if (!this.onGround && !flag1)
                {
                    if (this.fallTime > 100 && !this.world.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600)
                    {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                        }

                        this.setDead();
                    }
                }
                else
                {
                    IBlockState iblockstate = this.world.getBlockState(blockpos1);

                    if (this.world.isAirBlock(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))) //Forge: Don't indent below.
                        if (!flag1 && BlockFalling.canFallThrough(this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))))
                        {
                            this.onGround = false;
                            return;
                        }

                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= -0.5D;

                    if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION)
                    {
                       // this.setDead();



                    }
                }
            }

            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
        }
    }

    public void fall(float distance, float damageMultiplier)
    {
                List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));

                DamageSource damagesource = DamageSource.FALLING_BLOCK;
                for (Entity entity : list)
                {
                    if(entity.getCachedUniqueIdString()!=entId) {

                        if (entity instanceof EntityLivingBase) {
                            ((EntityLivingBase) entity).addPotionEffect(new ModPotionEffect(PotionRegistry.FireFlowResultPotion, 20 * (3 + lvl), lvl, damage));
                        }
                        entity.attackEntityFrom(damagesource, damage * 0.3f);
                    }
                }



    }

    @Override
    public void setDead() {

        super.setDead();

    }

    public static void registerFixesFallingBlock(DataFixer fixer)
    {
    }

    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setString("entId",entId);
        compound.setByte("lvl", lvl);
        compound.setFloat("damage",damage);
        Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.AIR;
        ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(block);
        compound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
        compound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
        compound.setInteger("Time", this.fallTime);
        compound.setBoolean("DropItem", this.shouldDropItem);
        compound.setBoolean("HurtEntities", this.hurtEntities);
        compound.setFloat("FallHurtAmount", this.fallHurtAmount);
        compound.setInteger("FallHurtMax", this.fallHurtMax);

        if (this.tileEntityData != null)
        {
            compound.setTag("TileEntityData", this.tileEntityData);
        }
    }

    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        entId = compound.getString("entId");
        lvl = compound.getByte("lvl");
        damage = compound.getFloat("damage");
        int i = compound.getByte("Data") & 255;

        if (compound.hasKey("Block", 8))
        {
            this.fallTile = Block.getBlockFromName(compound.getString("Block")).getStateFromMeta(i);
        }
        else if (compound.hasKey("TileID", 99))
        {
            this.fallTile = Block.getBlockById(compound.getInteger("TileID")).getStateFromMeta(i);
        }
        else
        {
            this.fallTile = Block.getBlockById(compound.getByte("Tile") & 255).getStateFromMeta(i);
        }

        this.fallTime = compound.getInteger("Time");
        Block block = this.fallTile.getBlock();

        if (compound.hasKey("HurtEntities", 99))
        {
            this.hurtEntities = compound.getBoolean("HurtEntities");
            this.fallHurtAmount = compound.getFloat("FallHurtAmount");
            this.fallHurtMax = compound.getInteger("FallHurtMax");
        }
        else if (block == Blocks.ANVIL)
        {
            this.hurtEntities = true;
        }

        if (compound.hasKey("DropItem", 99))
        {
            this.shouldDropItem = compound.getBoolean("DropItem");
        }

        if (compound.hasKey("TileEntityData", 10))
        {
            this.tileEntityData = compound.getCompoundTag("TileEntityData");
        }

        if (block == null || block.getDefaultState().getMaterial() == Material.AIR)
        {
            this.fallTile = Blocks.SAND.getDefaultState();
        }
    }

    public void setHurtEntities(boolean p_145806_1_)
    {
        this.hurtEntities = p_145806_1_;
    }

    public void addEntityCrashInfo(CrashReportCategory category)
    {
        super.addEntityCrashInfo(category);

        if (this.fallTile != null)
        {
            Block block = this.fallTile.getBlock();
            category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
            category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(this.fallTile)));
        }
    }

    @SideOnly(Side.CLIENT)
    public World getWorldObj()
    {
        return this.world;
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

    @Nullable
    public IBlockState getBlock()
    {
        return this.fallTile;
    }

    public boolean ignoreItemEntityData()
    {
        return true;
    }
}
