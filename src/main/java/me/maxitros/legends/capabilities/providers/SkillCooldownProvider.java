package me.maxitros.legends.capabilities.providers;

import me.maxitros.legends.capabilities.IMagicStats;
import me.maxitros.legends.capabilities.ISkillCooldown;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SkillCooldownProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ISkillCooldown.class)
    public static final Capability<ISkillCooldown> SKILLSCOOLDOWN_CAP = null;

    private ISkillCooldown instance = SKILLSCOOLDOWN_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == SKILLSCOOLDOWN_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == SKILLSCOOLDOWN_CAP ? SKILLSCOOLDOWN_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return SKILLSCOOLDOWN_CAP.getStorage().writeNBT(SKILLSCOOLDOWN_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        SKILLSCOOLDOWN_CAP.getStorage().readNBT(SKILLSCOOLDOWN_CAP, this.instance, null, nbt);
    }
}
