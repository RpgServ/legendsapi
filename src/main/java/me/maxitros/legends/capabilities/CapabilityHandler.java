package me.maxitros.legends.capabilities;

import me.maxitros.legends.Legends;
import me.maxitros.legends.capabilities.providers.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

    public static final ResourceLocation MAGIC_CAP = new ResourceLocation(Legends.modId, "magicstats");
    public static final ResourceLocation EXP_CAP = new ResourceLocation(Legends.modId, "expstats");
    public static final ResourceLocation SKILLS_CAP = new ResourceLocation(Legends.modId, "skillsdata");
    public static final ResourceLocation SKILLSCOOLDOWN_CAP = new ResourceLocation(Legends.modId, "skillcooldownprovider");
    public static final ResourceLocation SKILLSTARGETS_CAP = new ResourceLocation(Legends.modId, "skillplayercountprovider");
    public static final ResourceLocation MOBLVL_CAP = new ResourceLocation(Legends.modId, "moblvlprovider");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent event)
    {
        if(event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(MAGIC_CAP, new MagicStatsProvider());
            event.addCapability(EXP_CAP, new ExpStatsProvider());
            event.addCapability(SKILLS_CAP, new SkillsDataProvider());
            event.addCapability(SKILLSCOOLDOWN_CAP, new SkillCooldownProvider());
            event.addCapability(SKILLSTARGETS_CAP, new SkillsTargetsProvider());
        }
        if(event.getObject() instanceof EntityLivingBase)
        {
            event.addCapability(MOBLVL_CAP, new MobLvlProvider());
        }
    }
}
