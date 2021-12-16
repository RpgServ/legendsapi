package me.maxitros.legends.skills.talants;

import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.ISkillCooldown;
import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.SkillCooldown;
import me.maxitros.legends.capabilities.SkillsData;
import me.maxitros.legends.capabilities.providers.SkillCooldownProvider;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TalentEventHandler {
    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) event.getEntity();
            ISkillsData skillsData = playerMP.getCapability(SkillsDataProvider.SKILLS_CAP, null);
            ISkillCooldown cooldown = playerMP.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, null);
            if (event.getSource().isMagicDamage()
                    && SkillsData.HasSkill(SkillsEnum.Talent_Poisons, skillsData.GetSkillData())){
                int lvl = SkillsData.GetSkillLvl(SkillsEnum.Talent_Poisons, skillsData.GetSkillData());
                event.setAmount(event.getAmount()*(0.95f-0.05f*(lvl - 1)));
            }
            if ((event.getAmount() >= playerMP.getHealth() || playerMP.getHealth()<=playerMP.getMaxHealth()*0.01f)
                    && SkillsData.HasSkill(SkillsEnum.Talent_Survival, skillsData.GetSkillData())
                    && SkillCooldown.HasCooldown(SkillsEnum.Talent_Survival, cooldown.GetCooldownData())) {
                int lvl = SkillsData.GetSkillLvl(SkillsEnum.Talent_Survival, skillsData.GetSkillData());
                if (playerMP.getRNG().nextFloat() <= 25f) {
                    playerMP.heal(playerMP.getMaxHealth() * (0.3f + 0.1f * (lvl - 1)));
                    cooldown.SetCooldownData(SkillCooldown.UpdateCooldown(SkillsEnum.Talent_Survival, (byte) 120, skillsData.GetSkillData()),playerMP);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) event.getEntity();
            ISkillsData skillsData = playerMP.getCapability(SkillsDataProvider.SKILLS_CAP, null);
            if (SkillsData.HasSkill(SkillsEnum.Talent_Rehabilitation, skillsData.GetSkillData())) {
                int lvl = SkillsData.GetSkillLvl(SkillsEnum.Talent_Rehabilitation, skillsData.GetSkillData());
                event.setAmount(event.getAmount()*(1.1f+0.5f*(lvl-1)));
            }
        }
    }
}
