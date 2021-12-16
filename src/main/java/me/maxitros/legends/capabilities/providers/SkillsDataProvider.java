package me.maxitros.legends.capabilities.providers;

import me.maxitros.legends.capabilities.IMagicStats;
import me.maxitros.legends.capabilities.ISkillsData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SkillsDataProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ISkillsData.class)
    public static final Capability<ISkillsData> SKILLS_CAP = null;

    private ISkillsData instance = SKILLS_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == SKILLS_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == SKILLS_CAP ? SKILLS_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return SKILLS_CAP.getStorage().writeNBT(SKILLS_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        SKILLS_CAP.getStorage().readNBT(SKILLS_CAP, this.instance, null, nbt);
    }
}
