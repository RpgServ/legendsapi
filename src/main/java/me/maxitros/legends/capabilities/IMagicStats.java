package me.maxitros.legends.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

public interface IMagicStats {
    public void SetMaxMana(float points, EntityPlayerMP playerMP);
    public void Fill(float points, EntityPlayerMP playerMP);
    public void Fill(EntityPlayerMP playerMP);
    public void Consume(float points, EntityPlayerMP playerMP);
    public void Consume(float points);
    public void Fill();
    public void Fill(float points);
    public void SetMaxMana(float points);
    public void SetCurrentMana(float points);

    public float GetCurrentMana();
    public float GetMaxMana();
}
