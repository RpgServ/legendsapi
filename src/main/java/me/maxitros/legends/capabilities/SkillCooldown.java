package me.maxitros.legends.capabilities;

import me.maxitros.legends.api.SkillsEnum;
import net.minecraft.entity.player.EntityPlayerMP;

public class SkillCooldown  implements  ISkillCooldown{
    protected byte[] cooldowns;

    public SkillCooldown(byte[] data)
    {
        cooldowns = data;
    }
    @Override
    public void SetCooldownData(byte[] data, EntityPlayerMP playerMP) {
        TrackingSkills.tryAdd(playerMP);
        SetCooldownData(data);
    }

    @Override
    public void SetCooldownData(byte[] data) {
        cooldowns = data;
    }

    @Override
    public byte[] GetCooldownData() {
        return cooldowns;
    }
    public static SkillReturn ProceedCooldowns(byte[] current)
    {
        boolean flag = false;
        for(int b = 0; b < SkillsEnum.Count; b++)
            if(current[b]>0) {
                flag = true;
                current[b]--;
            }
        return new SkillReturn(flag, current);
    }

    public static byte[] ProceedCooldown(SkillsEnum skill, byte[] current)
    {
        if(current[skill.ordinal()]>0)
            current[skill.ordinal()]--;
        return current;
    }
    public static byte[] UpdateCooldown(SkillsEnum skill, byte cooldown, byte[] current)
    {
        current[skill.ordinal()] = cooldown;
        return current;
    }
    public static int GetSkillCooldown(SkillsEnum skill, byte[] current)
    {
        return current[skill.ordinal()];
    }
    public static boolean HasCooldown(SkillsEnum skill, byte[] current)
    {
        return current[skill.ordinal()]>0;
    }
    public static class SkillReturn{
        public boolean hasCooldown = false;
        public byte[] data;
        public SkillReturn(boolean hasCooldown, byte[] data){
            this.data=data;
            this.hasCooldown = hasCooldown;
        }
    }
}
