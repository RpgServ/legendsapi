package me.maxitros.legends.capabilities.providers;

import me.maxitros.legends.capabilities.IMagicStats;
import me.maxitros.legends.capabilities.IMobLvl;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MobLvlProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IMobLvl.class)
    public static final Capability<IMobLvl> MOBLVL_CAP = null;

    private IMobLvl instance = MOBLVL_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == MOBLVL_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == MOBLVL_CAP ? MOBLVL_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return MOBLVL_CAP.getStorage().writeNBT(MOBLVL_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        MOBLVL_CAP.getStorage().readNBT(MOBLVL_CAP, this.instance, null, nbt);
    }
}
