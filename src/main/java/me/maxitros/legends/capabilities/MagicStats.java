package me.maxitros.legends.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

public class MagicStats implements IMagicStats{
    protected float currentMana;
    protected float maxMana;
    public MagicStats(float currentMana, float maxMana)
    {
        this.currentMana = currentMana;
        this.maxMana = 20;
    }

    public void SetMaxMana(float points, EntityPlayerMP playerMP) {
        SetMaxMana(points);
        TrackingStats.tryAdd(playerMP);
    }
    public void Fill(float points, EntityPlayerMP playerMP) {
        if(currentMana + points >= maxMana)
            Fill(playerMP);
        else
            currentMana += points;
    }
    public void SetCurrentMana(float points) {
        currentMana = points;
    }

    public void Fill(EntityPlayerMP playerMP) {
        Fill();
        TrackingStats.tryRemove(playerMP);
    }
    public void Consume(float points, EntityPlayerMP playerMP) {
        Consume(points);
        TrackingStats.tryAdd(playerMP);
    }
    @Override
    public void Consume(float points) {
        currentMana -= points;
    }

    @Override
    public void Fill() {
        currentMana = maxMana;
    }

    @Override
    public void Fill(float points) {
        if(currentMana + points >= maxMana)
            currentMana = maxMana;
        else
            currentMana += points;
    }

    @Override
    public void SetMaxMana(float points) {
        maxMana = points;
    }

    @Override
    public float GetCurrentMana() {
        return currentMana;
    }

    @Override
    public float GetMaxMana() {
        return maxMana;
    }
}
