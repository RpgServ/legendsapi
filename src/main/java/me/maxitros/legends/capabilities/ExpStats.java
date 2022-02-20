package me.maxitros.legends.capabilities;

import me.maxitros.legends.Legends;
import me.maxitros.legends.api.LvlXPData;

public class ExpStats implements IExpStats{
    protected int skillPoints;
    protected int lvl;
    protected float currentXP;
    protected float nextLvlXP;
    protected String playerName;
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
        while (currentXP >= nextLvlXP && lvl<=70)
        {
            lvl++;
            if(Legends.server!=null && playerName != null)
            {
                Legends.server.getCommandManager().executeCommand(Legends.server, "tmoney deposit krone "+playerName+" 100");
            }
            nextLvlXP = LvlXPData.GetXpToNextLvl(lvl);
        }
    }

    @Override
    public void SetCurrentXP(float xp) {
        currentXP = xp;
        while (currentXP >= nextLvlXP && lvl<=70)
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

    @Override
    public int GetSkillPoints() {
        return skillPoints;
    }

    @Override
    public void SetSkillPoints(int points) {
        skillPoints = points;
    }

    @Override
    public void SetPlayer(String player) {
        playerName = player;
    }
}
