package me.maxitros.legends.capabilities;

import me.maxitros.legends.api.SkillsEnum;

public interface ISkillsData {
    public void SetSkillData(byte[] data);
    public byte[] GetSkillData();
    public SkillsEnum GetActiveSkill();
    public void SetActiveSkill(SkillsEnum skill);
    public float GetFullDamage();
    public void SetAdditionalDamage(float damage);
    public float GetAdditionalDamage();
    public void SetBaseDamage(float damage);
    public float GetBaseDamage();
}
