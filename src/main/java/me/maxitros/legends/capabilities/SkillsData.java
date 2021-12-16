package me.maxitros.legends.capabilities;

import me.maxitros.legends.api.SkillsEnum;

public class SkillsData implements ISkillsData{
    protected byte[] skills;
    protected SkillsEnum active;
    protected float baseDamage;
    protected float additionalDamage;

    public SkillsData(byte[] skills, SkillsEnum active, float baseDamage, float additionalDamage)
    {
        this.skills = skills;
        this.active = active;
        this.baseDamage = baseDamage;
        this.additionalDamage = additionalDamage;
    }
    @Override
    public void SetSkillData(byte[] data) {
        skills=data;
    }

    @Override
    public byte[] GetSkillData() {
        return skills;
    }

    @Override
    public SkillsEnum GetActiveSkill() {
        return active;
    }

    @Override
    public void SetActiveSkill(SkillsEnum skill) {
        active = skill;
    }

    @Override
    public float GetFullDamage() {
        return baseDamage+additionalDamage;
    }

    @Override
    public void SetAdditionalDamage(float damage) {
        additionalDamage = damage;
    }

    @Override
    public float GetAdditionalDamage() {
        return additionalDamage;
    }

    @Override
    public void SetBaseDamage(float damage) {
        baseDamage = damage;
    }

    @Override
    public float GetBaseDamage() {
        return baseDamage;
    }


    public static byte[] UpdateSkill(SkillsEnum skill, byte lvl, byte[] current)
    {
        current[skill.ordinal()] = lvl;
        return current;
    }
    public static byte GetSkillLvl(SkillsEnum skill, byte[] current)
    {
        return current[skill.ordinal()];
    }
    public static boolean HasSkill(SkillsEnum skill, byte[] current)
    {
        return current[skill.ordinal()]>0;
    }
}
