package me.maxitros.legends.capabilities;

import me.maxitros.legends.api.SkillsEnum;
import net.minecraft.entity.player.EntityPlayerMP;

public interface ISkillCooldown {
    public void SetCooldownData(byte[] data, EntityPlayerMP playerMP);
    public void SetCooldownData(byte[] data);
    public byte[] GetCooldownData();
}
