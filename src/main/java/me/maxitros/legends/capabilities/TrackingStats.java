package me.maxitros.legends.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;

public class TrackingStats {
    public static ArrayList<EntityPlayerMP> playerMPS = new ArrayList<>();

    public static void tryAdd(EntityPlayerMP playerMP) {
        if(!playerMPS.contains(playerMP))
        {
            playerMPS.add(playerMP);
        }
    }

    public static void tryRemove(EntityPlayerMP playerMP) {
        if(playerMPS.contains(playerMP))
            playerMPS.remove(playerMP);
    }
}
