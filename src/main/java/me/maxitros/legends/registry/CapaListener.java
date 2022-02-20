package me.maxitros.legends.registry;


import me.maxitros.legends.ClientData;
import me.maxitros.legends.Legends;
import me.maxitros.legends.api.ISkillsCallback;
import me.maxitros.legends.api.SkillsCallback;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.*;
import me.maxitros.legends.capabilities.providers.*;
import me.maxitros.legends.networking.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CapaListener {
    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event)
    {

        if(!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayerMP){
            EntityPlayerMP playerMP = (EntityPlayerMP) event.getEntity();
            IMagicStats stats = playerMP.getCapability(MagicStatsProvider.MAGIC_CAP, EnumFacing.UP);
            if(stats.GetCurrentMana()<stats.GetMaxMana())
                TrackingStats.tryAdd(playerMP);

            ISkillCooldown cooldown = playerMP.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, EnumFacing.UP);
            if(SkillCooldown.ProceedCooldowns(cooldown.GetCooldownData()).hasCooldown)
                TrackingSkills.tryAdd(playerMP);

            ISkillsTargetsCount count = playerMP.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, EnumFacing.UP);
            count.setTargetsCount(new byte[SkillsEnum.Count]);

            IExpStats expStats  = playerMP.getCapability(ExpStatsProvider.EXP_STATS_CAPABILITY_CAP, EnumFacing.UP);
            expStats.SetPlayer(playerMP.getName());
            SyncAllData(playerMP);
        }


    }
    static int tick = 0;
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if(tick==10||tick==20)
        {
            if(Legends.server!=null) {
                for (EntityPlayerMP playerMP : TrackingSkills.playerMPS) {
                    SkillsCallback callback = new SkillsCallback(playerMP.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, EnumFacing.UP).GetCooldownData(), playerMP) {
                        @Override
                        public void Proceed(byte[] data, boolean flag) {
                            ISkillCooldown cooldown = playerMP.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, EnumFacing.UP);
                            cooldown.SetCooldownData(data);
                            PacketHandler.INSTANCE.sendTo(new SyncSkillCooldown(cooldown.GetCooldownData()), playerMP);
                            if (!flag)
                                TrackingStats.tryRemove(this.player);
                        }
                    };
                    ((Runnable) () -> {
                        SkillCooldown.SkillReturn skillReturn = SkillCooldown.ProceedCooldowns(callback.data);
                        callback.Proceed(skillReturn.data, skillReturn.hasCooldown);
                    }).run();
                }
            }
        }
        if(tick==20)
        {
            if(Legends.server!=null){
                for(EntityPlayerMP playerMP : TrackingStats.playerMPS) {
                    IMagicStats stats = playerMP.getCapability(MagicStatsProvider.MAGIC_CAP, null);
                    stats.Fill(10);
                    PacketHandler.INSTANCE.sendTo(new SyncMagicStatsPacket(stats.GetCurrentMana(), stats.GetMaxMana()), playerMP);
                }
                ((Runnable) () -> {
                    int l = TrackingPlayersCount.TrackingPlayersCountQueue.size();
                    for (int i = 0; i<l; i++) {
                        TrackingPlayersCount.TrackingElement element = TrackingPlayersCount.TrackingPlayersCountQueue.get(i);
                        if (element.time == 0) {
                            element.callback.Call();
                            l--;
                            i--;
                            continue;
                        }
                        element.time--;
                    }
                }).run();
            }
            tick = 0;
        }
        tick++;
    }
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        if( event.getEntityPlayer() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP)event.getEntityPlayer();
            IMagicStats mana = player.getCapability(MagicStatsProvider.MAGIC_CAP, EnumFacing.UP);
            IMagicStats oldMana = event.getOriginal().getCapability(MagicStatsProvider.MAGIC_CAP, EnumFacing.UP);
            mana.SetMaxMana(oldMana.GetMaxMana(), player);
            mana.Fill(oldMana.GetCurrentMana(), player);

            ISkillsData skillsData = player.getCapability(SkillsDataProvider.SKILLS_CAP, EnumFacing.UP);
            ISkillsData oldSkillsData = event.getOriginal().getCapability(SkillsDataProvider.SKILLS_CAP, EnumFacing.UP);
            skillsData.SetSkillData(oldSkillsData.GetSkillData());
            skillsData.SetActiveSkill(oldSkillsData.GetActiveSkill());

            IExpStats expStats = player.getCapability(ExpStatsProvider.EXP_STATS_CAPABILITY_CAP, EnumFacing.UP);
            IExpStats oldExpStats = event.getOriginal().getCapability(ExpStatsProvider.EXP_STATS_CAPABILITY_CAP, EnumFacing.UP);
            expStats.SetCurrentLvl(oldExpStats.GetCurrentLvl());
            expStats.SetCurrentXP(oldExpStats.GetCurrentXP());
            expStats.SetNextLvlXP(oldExpStats.GetNextLvlXP());

            ISkillCooldown skillCooldown = player.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, EnumFacing.UP);
            ISkillCooldown oldSkillCooldown = event.getOriginal().getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, EnumFacing.UP);
            skillCooldown.SetCooldownData(oldSkillCooldown.GetCooldownData(), player);
            SyncAllData(player);

            ISkillsTargetsCount skillTargets = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, EnumFacing.UP);
            ISkillsTargetsCount oldSkillTargets = event.getOriginal().getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, EnumFacing.UP);
            skillTargets.setTargetsCount(oldSkillTargets.getTargetsCount());

        }
    }
    public static void SyncAllData(EntityPlayerMP playerMP)
    {
        SyncCapa(CapaType.MagicStats, playerMP);
        SyncCapa(CapaType.SkillData, playerMP);
        SyncCapa(CapaType.ExpStats, playerMP);
        SyncCapa(CapaType.SkillCooldown, playerMP);
    }
    public static void SyncCapa(CapaType capability, EntityPlayerMP playerMP)
    {
        switch (capability)
        {
            case MagicStats: {
                IMagicStats stats = playerMP.getCapability(MagicStatsProvider.MAGIC_CAP, EnumFacing.UP);
                PacketHandler.INSTANCE.sendTo(new SyncMagicStatsPacket(stats.GetCurrentMana(), stats.GetMaxMana()), playerMP);
                break;
            }
            case SkillData: {
                ISkillsData stats = playerMP.getCapability(SkillsDataProvider.SKILLS_CAP, EnumFacing.UP);
                PacketHandler.INSTANCE.sendTo(new SyncSkillData(stats.GetSkillData(), stats.GetActiveSkill(), stats.GetBaseDamage(), stats.GetBaseDamage()), playerMP);
                break;
            }
            case ExpStats:{
                IExpStats stats = playerMP.getCapability(ExpStatsProvider.EXP_STATS_CAPABILITY_CAP, EnumFacing.UP);
                PacketHandler.INSTANCE.sendTo(new SyncXPStatsPacket(stats.GetCurrentLvl(), stats.GetCurrentXP(), stats.GetNextLvlXP()), playerMP);
                break;
            }
            case SkillCooldown:{
                ISkillCooldown stats = playerMP.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, EnumFacing.UP);
                PacketHandler.INSTANCE.sendTo(new SyncSkillCooldown(stats.GetCooldownData()), playerMP);
                break;
            }
        }
    }
    public enum CapaType
    {
        MagicStats, SkillData, ExpStats, SkillCooldown
    }
}
