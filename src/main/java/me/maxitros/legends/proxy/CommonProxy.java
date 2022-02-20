package me.maxitros.legends.proxy;

import me.maxitros.legends.Legends;
import me.maxitros.legends.api.LvlXPData;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.*;
import me.maxitros.legends.effects.PotionRegistry;
import me.maxitros.legends.entities.magic.*;
import me.maxitros.legends.items.ItemRegistry;
import me.maxitros.legends.networking.PacketHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        registerEntities();
        CapabilityManager.INSTANCE.register(IMagicStats.class, new Capability.IStorage<IMagicStats>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IMagicStats> capability, IMagicStats instance, EnumFacing side)
                    {
                        NBTTagCompound compound = new NBTTagCompound();
                        compound.setFloat("currentMana", instance.GetCurrentMana());
                        compound.setFloat("maxMana", instance.GetMaxMana());
                        return compound;
                    }

                    @Override
                    public void readNBT(Capability<IMagicStats> capability, IMagicStats instance, EnumFacing side, NBTBase nbt)
                    {
                        ((MagicStats)instance).SetCurrentMana(((NBTTagCompound)nbt).getFloat("currentMana"));
                        ((MagicStats)instance).SetMaxMana(((NBTTagCompound)nbt).getFloat("maxMana"));
                    }
                },
                () -> new MagicStats(100,100));
        CapabilityManager.INSTANCE.register(IExpStats.class, new Capability.IStorage<IExpStats>(){
                    @Override
                    public NBTBase writeNBT(Capability<IExpStats> capability, IExpStats instance, EnumFacing side)
                    {
                        NBTTagCompound compound = new NBTTagCompound();
                        compound.setFloat("currentXp", instance.GetCurrentXP());
                        compound.setFloat("nextXp", instance.GetNextLvlXP());
                        compound.setInteger("lvl", instance.GetCurrentLvl());
                        return compound;
                    }

                    @Override
                    public void readNBT(Capability<IExpStats> capability, IExpStats instance, EnumFacing side, NBTBase nbt)
                    {
                        NBTTagCompound compound = (NBTTagCompound) nbt;
                        ((ExpStats)instance).SetCurrentXP(compound.getFloat("currentXp"));
                        ((ExpStats)instance).SetNextLvlXP(compound.getFloat("nextXp"));
                        ((ExpStats)instance).SetCurrentLvl(compound.getInteger("lvl"));
                    }
                },
                () -> new ExpStats(0, 0, LvlXPData.GetXpToNextLvl(1)));
        CapabilityManager.INSTANCE.register(ISkillsData.class, new Capability.IStorage<ISkillsData>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<ISkillsData> capability, ISkillsData instance, EnumFacing side)
                    {
                        NBTTagCompound compound = new NBTTagCompound();
                        compound.setByteArray("skillValues", instance.GetSkillData());
                        compound.setInteger("activeSkill", instance.GetActiveSkill().ordinal());
                        compound.setFloat("baseDamage", instance.GetBaseDamage());
                        compound.setFloat("addDamage", instance.GetAdditionalDamage());
                        return compound;
                    }

                    @Override
                    public void readNBT(Capability<ISkillsData> capability, ISkillsData instance, EnumFacing side, NBTBase nbt)
                    {
                        NBTTagCompound compound = (NBTTagCompound) nbt;
                        ((SkillsData)instance).SetSkillData(compound.getByteArray("skillValues"));
                        ((SkillsData)instance).SetActiveSkill(SkillsEnum.values()[compound.getInteger("activeSkill")]);
                        ((SkillsData)instance).SetBaseDamage(compound.getFloat("baseDamage"));
                        ((SkillsData)instance).SetAdditionalDamage(compound.getFloat("addDamage"));
                    }
                },
                () -> new SkillsData(new byte[SkillsEnum.Count], SkillsEnum.EMPTY, 0.5f, 0));
        CapabilityManager.INSTANCE.register(ISkillCooldown.class, new Capability.IStorage<ISkillCooldown>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<ISkillCooldown> capability, ISkillCooldown instance, EnumFacing side)
                    {
                        return new NBTTagByteArray(instance.GetCooldownData());
                    }

                    @Override
                    public void readNBT(Capability<ISkillCooldown> capability, ISkillCooldown instance, EnumFacing side, NBTBase nbt)
                    {
                        ((SkillCooldown)instance).SetCooldownData(((NBTTagByteArray)nbt).getByteArray());
                    }
                },
                () -> new SkillCooldown(new byte[SkillsEnum.Count]));
        CapabilityManager.INSTANCE.register(ISkillsTargetsCount.class, new Capability.IStorage<ISkillsTargetsCount>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<ISkillsTargetsCount> capability, ISkillsTargetsCount instance, EnumFacing side)
                    {
                        return new NBTTagByteArray(instance.getTargetsCount());
                    }

                    @Override
                    public void readNBT(Capability<ISkillsTargetsCount> capability, ISkillsTargetsCount instance, EnumFacing side, NBTBase nbt)
                    {
                        ((SkillsTargetsCount)instance).setTargetsCount(((NBTTagByteArray)nbt).getByteArray());
                    }
                },
                () -> new SkillsTargetsCount(new byte[SkillsEnum.Count]));
        PacketHandler.registerMessages(Legends.modId);
        PotionRegistry.registerPotions();
        ItemRegistry.RegisterItems();

    }

    private void registerEntities() {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocation(Legends.modId, "MagicArrow"), EntityMagicArrow.class, "MagicArrow", id++, Legends.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Legends.modId, "MagicGrenade"), EntityMagicGrenade.class, "MagicGrenade", id++, Legends.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Legends.modId, "EntityFallingCubes"), EntityFallingCubes.class, "EntityFallingCubes", id++, Legends.instance, 64, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Legends.modId, "EntityFallingCube"), EntityFallingCube.class, "EntityFallingCube", id++, Legends.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Legends.modId, "EntityIceCube"), EntityIceCube.class, "EntityIceCube", id++, Legends.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Legends.modId, "EntityMine"), EntityMine.class, "EntityMine", id++, Legends.instance, 64, 3, true);
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

}
