package me.maxitros.legends.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

public interface IExpStats {
    public void SetNextLvlXP(float xp);
    public float GetNextLvlXP();
    public void AddXP(float xp);
    public void SetCurrentXP(float xp);
    public float GetCurrentXP();
    public void SetCurrentLvl(int lvl);
    public int GetCurrentLvl();
}
