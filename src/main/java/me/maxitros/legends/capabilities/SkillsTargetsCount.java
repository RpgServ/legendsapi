package me.maxitros.legends.capabilities;

import me.maxitros.legends.api.SkillsEnum;

public class SkillsTargetsCount implements ISkillsTargetsCount {
    protected byte[] targetsCount;

    public SkillsTargetsCount(byte[] data)
    {
        targetsCount = data;
    }


    @Override
    public byte[] getTargetsCount() {
        return targetsCount;
    }

    @Override
    public void setTargetsCount(byte[] targetsCount) {
        this.targetsCount = targetsCount;
    }
    public static byte[] DecTargetsCount(SkillsEnum skill, byte[] current) {
        if(current[skill.ordinal()]>0)
            current[skill.ordinal()] -= 1;
        return current;
    }
    public static byte[] AddTargetsCount(SkillsEnum skill, byte[] current)
    {
        current[skill.ordinal()] += 1;
        return current;
    }

    public static byte[] UpdateTargetsCount(SkillsEnum skill, byte targets, byte[] current)
    {
        current[skill.ordinal()] = targets;
        return current;
    }
    public static int GetSkillTargetsCount(SkillsEnum skill, byte[] current)
    {
        return current[skill.ordinal()];
    }
}
