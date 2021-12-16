package me.maxitros.legends.capabilities;

import me.maxitros.legends.api.LvlXPData;

public class ExpStats implements IExpStats{
    protected int lvl;
    protected float currentXP;
    protected float nextLvlXP;
    public ExpStats(int lvl, float currentXP, float nextLvlXP)
    {
        this.lvl = lvl;
        this.currentXP = currentXP;
        this.nextLvlXP = nextLvlXP;
    }
    @Override
    public void SetNextLvlXP(float xp) {
        nextLvlXP = xp;
    }

    @Override
    public float GetNextLvlXP() {
        return nextLvlXP;
    }

    @Override
    public void AddXP(float xp) {
        currentXP += xp;
        while (currentXP >= nextLvlXP)
        {
            lvl++;
            nextLvlXP = LvlXPData.GetXpToNextLvl(lvl);
        }
    }

    @Override
    public void SetCurrentXP(float xp) {
        currentXP = xp;
        while (currentXP >= nextLvlXP)
        {
            lvl++;
            nextLvlXP = LvlXPData.GetXpToNextLvl(lvl);
        }
    }

    @Override
    public float GetCurrentXP() {
        return currentXP;
    }

    @Override
    public void SetCurrentLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public int GetCurrentLvl() {
        return lvl;
    }
}
