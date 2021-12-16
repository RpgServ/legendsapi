package me.maxitros.legends.capabilities.providers;

import me.maxitros.legends.capabilities.IMagicStats;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MagicStatsProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IMagicStats.class)
    public static final Capability<IMagicStats> MAGIC_CAP = null;

    private IMagicStats instance = MAGIC_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == MAGIC_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == MAGIC_CAP ? MAGIC_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return MAGIC_CAP.getStorage().writeNBT(MAGIC_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        MAGIC_CAP.getStorage().readNBT(MAGIC_CAP, this.instance, null, nbt);
    }
}

