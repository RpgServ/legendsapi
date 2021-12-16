package me.maxitros.legends.capabilities.providers;

import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.ISkillsTargetsCount;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SkillsTargetsProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ISkillsTargetsCount.class)
    public static final Capability<ISkillsTargetsCount> SKILLS_TARGETS_COUNT_CAPABILITY = null;

    private ISkillsTargetsCount instance = SKILLS_TARGETS_COUNT_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == SKILLS_TARGETS_COUNT_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == SKILLS_TARGETS_COUNT_CAPABILITY ? SKILLS_TARGETS_COUNT_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return SKILLS_TARGETS_COUNT_CAPABILITY.getStorage().writeNBT(SKILLS_TARGETS_COUNT_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        SKILLS_TARGETS_COUNT_CAPABILITY.getStorage().readNBT(SKILLS_TARGETS_COUNT_CAPABILITY, this.instance, null, nbt);
    }
}
