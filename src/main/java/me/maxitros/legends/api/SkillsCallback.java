package me.maxitros.legends.api;

import me.maxitros.legends.capabilities.IExpStats;
import net.minecraft.entity.player.EntityPlayerMP;

public class SkillsCallback implements ISkillsCallback {
    public boolean flag = false;
    public byte[] data;
    public EntityPlayerMP player;
    public SkillsCallback(byte[] data, EntityPlayerMP playerMP)
    {
        this.data = data;
        player = playerMP;
    }
    public void Proceed(byte[] data, boolean flag)
    {

    }

}
