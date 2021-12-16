package me.maxitros.legends.capabilities.providers;

import me.maxitros.legends.capabilities.IExpStats;
import me.maxitros.legends.capabilities.IMagicStats;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ExpStatsProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IExpStats.class)
    public static final Capability<IExpStats> EXP_STATS_CAPABILITY_CAP = null;

    private IExpStats instance = EXP_STATS_CAPABILITY_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == EXP_STATS_CAPABILITY_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == EXP_STATS_CAPABILITY_CAP ? EXP_STATS_CAPABILITY_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return EXP_STATS_CAPABILITY_CAP.getStorage().writeNBT(EXP_STATS_CAPABILITY_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        EXP_STATS_CAPABILITY_CAP.getStorage().readNBT(EXP_STATS_CAPABILITY_CAP, this.instance, null, nbt);
    }
}
