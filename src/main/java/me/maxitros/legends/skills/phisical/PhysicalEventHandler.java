package me.maxitros.legends.skills.phisical;

import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.*;
import me.maxitros.legends.capabilities.providers.SkillCooldownProvider;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import me.maxitros.legends.capabilities.providers.SkillsTargetsProvider;
import me.maxitros.legends.effects.ModPotion;
import me.maxitros.legends.effects.ModPotionEffect;


import me.maxitros.legends.effects.PotionRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;

public class PhysicalEventHandler {
    @SubscribeEvent
    public void onDamage(AttackEntityEvent event)
    {
        if(event.getEntityPlayer() instanceof EntityPlayerMP && event.getTarget() instanceof EntityLivingBase)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            EntityLivingBase target = (EntityLivingBase) event.getTarget();
            ISkillsData skillsData = player.getCapability(SkillsDataProvider.SKILLS_CAP, null);
            ISkillsTargetsCount targetsCount  = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, null);
            ISkillCooldown cooldown  = player.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, null);
            ProccedSkill(skillsData, targetsCount, player, target, SkillsEnum.Skill_Physically_Cut_Swordsman, 5,
                    1, PotionRegistry.StabbingWoundPotion,5);
            ProccedSkill(skillsData, targetsCount, player, target, SkillsEnum.Skill_Physically_Cut_Kendo, 4,
                    1, PotionRegistry.IncisionPotion,3);
            ProccedCooldownSkill(skillsData, cooldown, player, target, SkillsEnum.Skill_Physically_Cut_Witcher,
                    1f,0.1f,(byte) 10,(byte)1);
            ProccedSkill(skillsData, targetsCount, player, target, SkillsEnum.Skill_Physically_Cut_Samurai, 3,
                    1, PotionRegistry.HackedPotion,2);
            ProccedCooldownSkill(skillsData, cooldown, player, target, SkillsEnum.Skill_Physically_Cut_Musketeer,
                    2f,0.1f,(byte) 10,(byte)1);


            ProccedSkill(skillsData, targetsCount, player, target, SkillsEnum.Skill_Physically_Fraction_Blacksmith, 5,
                    1, PotionRegistry.CrushedWoundPotion,1);
            ProccedCooldownEffectPositiveSkill(skillsData, cooldown, player, target, SkillsEnum.Skill_Physically_Fraction_Ambal, (byte) 10,
                    (byte) 0, 5,1, PotionRegistry.AmbalPotion);
            ProccedCooldownAndPotionSkill(skillsData, targetsCount, cooldown, player, target, SkillsEnum.Skill_Physically_Fraction_Bandit,
                    0f,0f,(byte) 40,(byte)2, 10,
                    1, PotionRegistry.StunningPotion,1);
            ProccedCooldownAndPotionSkill(skillsData, targetsCount, cooldown, player, target, SkillsEnum.Skill_Physically_Fraction_Hooligan,
                    0f,0f,(byte) 15,(byte)2, 10,
                    1, PotionRegistry.RepulsedPotion,1);
            ProccedCooldownEffectPositiveSkill(skillsData, cooldown, player, target, SkillsEnum.Skill_Physically_Fraction_Monk, (byte) 60,
                    (byte) 2, 10,0, PotionRegistry.MonkPotion);
        }
    }
    @SubscribeEvent
    public void onArrowDamage(LivingHurtEvent event)
    {
        if(event.getSource().damageType.equals("arrow"))
        {
            if(event.getSource().getTrueSource() instanceof EntityPlayerMP && event.getEntity() instanceof EntityLivingBase)
            {
                EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();
                EntityLivingBase target = (EntityLivingBase) event.getEntity();
                ISkillsData skillsData = player.getCapability(SkillsDataProvider.SKILLS_CAP, null);
                ISkillsTargetsCount targetsCount  = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, null);
                ISkillCooldown cooldown  = player.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, null);
                ProccedCooldownAndPotionSkill(skillsData, targetsCount, cooldown, player, target, SkillsEnum.Skill_Physically_Shot_Assassin	,
                        0f,0f,(byte) 4,(byte)0, 5,
                        1, PotionRegistry.EmpoisonedPotion,5);
                ProccedCooldownSkill(skillsData, cooldown, player, target, SkillsEnum.Skill_Physically_Shot_Hunter,
                        2f,0.1f,(byte) 40,(byte)1);
                ProccedCooldownEffectPositiveSkill(skillsData, cooldown, player, target, SkillsEnum.Skill_Physically_Shot_Cowboy,
                        (byte) 10, (byte)1, 5, 0, PotionRegistry.CowboyPotion);
            }
        }
    }


    @SubscribeEvent
    public void onArrow(ArrowLooseEvent event)
    {
        Optional<PotionEffect> effect = event.getEntityPlayer().getActivePotionEffects().stream().filter(x->x.getPotion().equals(PotionRegistry.CowboyPotion)).findAny();
        if(effect.isPresent()){
            event.setCharge(Math.round(event.getCharge()*((100f+25+(effect.get().getAmplifier()-1)*5)/100f)));
        }
    }
    public static void ProccedCooldownAndPotionSkill(ISkillsData skillsData, ISkillsTargetsCount targetsCount, ISkillCooldown cooldown, EntityPlayerMP player, EntityLivingBase target, SkillsEnum skill, float baseDamage, float nextDamage, byte cooldownCount, byte nextCooldownCount, int baseDuration, int nextDuration, ModPotion potion, int maxTargets)
    {
        if (SkillsData.GetSkillLvl(skill, skillsData.GetSkillData())>0
                && !SkillCooldown.HasCooldown(skill, cooldown.GetCooldownData())
                && SkillsTargetsCount.GetSkillTargetsCount(skill, targetsCount.getTargetsCount())<maxTargets)
        {
            byte lvl = SkillsData.GetSkillLvl(skill, skillsData.GetSkillData());
            cooldown.SetCooldownData(SkillCooldown.UpdateCooldown(skill, (byte) (cooldownCount-(lvl-1)*nextCooldownCount), cooldown.GetCooldownData()));
            int duration = 20*(baseDuration+lvl*(nextDuration-1));
            target.addPotionEffect(new ModPotionEffect(potion, duration, lvl, skillsData.GetFullDamage()));
            TrackingPlayersCount.TryAdd(new TrackingPlayersCount.TrackingElement(target.getCachedUniqueIdString()+skill.name(), duration/20,
                    new TrackingPlayersCount.TrackingCallback(player){
                        @Override
                        public void Call() {
                            ISkillsTargetsCount targetsCount  = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, null);
                            targetsCount.setTargetsCount(SkillsTargetsCount.DecTargetsCount(skill, targetsCount.getTargetsCount()));
                        }
                    }), new TrackingPlayersCount.AfterAddCallback(player){
                @Override
                public void Call() {
                    ISkillsTargetsCount targetsCount  = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, null);
                    targetsCount.setTargetsCount(SkillsTargetsCount.AddTargetsCount(skill, targetsCount.getTargetsCount()));
                }
            });
        }
    }
    public static void ProccedCooldownEffectPositiveSkill(ISkillsData skillsData, ISkillCooldown cooldown, EntityPlayerMP player, EntityLivingBase target, SkillsEnum skill, byte cooldownCount, byte nextCooldownCount, int baseDuration, int nextDuration, ModPotion potion)
    {
        if (SkillsData.GetSkillLvl(skill, skillsData.GetSkillData())>0
                && !SkillCooldown.HasCooldown(skill, cooldown.GetCooldownData()))
        {
            byte lvl = SkillsData.GetSkillLvl(skill, skillsData.GetSkillData());
            int duration = 20*(baseDuration+lvl*(nextDuration));
            player.addPotionEffect(new ModPotionEffect(potion, duration, lvl, skillsData.GetFullDamage()));
            cooldown.SetCooldownData(SkillCooldown.UpdateCooldown(skill, (byte) (cooldownCount-(lvl-1)*nextCooldownCount), cooldown.GetCooldownData()));
        }
    }
    public static void ProccedSkill(ISkillsData skillsData, ISkillsTargetsCount targetsCount, EntityPlayerMP player, EntityLivingBase target, SkillsEnum skill, int baseDuration, int nextDuration, ModPotion potion, int maxTargets)
    {
        if (SkillsData.GetSkillLvl(skill, skillsData.GetSkillData())>0
                && SkillsTargetsCount.GetSkillTargetsCount(skill, targetsCount.getTargetsCount())<maxTargets)
        {
            byte lvl = SkillsData.GetSkillLvl(skill, skillsData.GetSkillData());
            int duration = 20*(baseDuration+lvl*(nextDuration-1));
            target.addPotionEffect(new ModPotionEffect(potion, duration, lvl, skillsData.GetFullDamage()));
            TrackingPlayersCount.TryAdd(new TrackingPlayersCount.TrackingElement(target.getCachedUniqueIdString()+skill.name(), duration/20,
                    new TrackingPlayersCount.TrackingCallback(player){
                        @Override
                        public void Call() {
                            ISkillsTargetsCount targetsCount  = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, null);
                            targetsCount.setTargetsCount(SkillsTargetsCount.DecTargetsCount(skill, targetsCount.getTargetsCount()));
                        }
                    }), new TrackingPlayersCount.AfterAddCallback(player){
                @Override
                public void Call() {
                    ISkillsTargetsCount targetsCount  = player.getCapability(SkillsTargetsProvider.SKILLS_TARGETS_COUNT_CAPABILITY, null);
                    targetsCount.setTargetsCount(SkillsTargetsCount.AddTargetsCount(skill, targetsCount.getTargetsCount()));
                }
            });
        }
    }
    public static void ProccedCooldownSkill(ISkillsData skillsData, ISkillCooldown cooldown, EntityPlayerMP player, EntityLivingBase target, SkillsEnum skill, float baseDamage, float nextDamage, byte cooldownCount, byte nextCooldownCount)
    {
        if (SkillsData.GetSkillLvl(skill, skillsData.GetSkillData())>0
                && !SkillCooldown.HasCooldown(skill, cooldown.GetCooldownData()))
        {
            byte lvl = SkillsData.GetSkillLvl(skill, skillsData.GetSkillData());
            target.attackEntityFrom(DamageSource.GENERIC, skillsData.GetFullDamage()*(baseDamage+(lvl-1)*nextDamage));
            cooldown.SetCooldownData(SkillCooldown.UpdateCooldown(skill, (byte) (cooldownCount-(lvl-1)*nextCooldownCount), cooldown.GetCooldownData()));
        }
    }
}
