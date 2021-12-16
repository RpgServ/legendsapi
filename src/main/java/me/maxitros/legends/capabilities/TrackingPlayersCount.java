package me.maxitros.legends.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TrackingPlayersCount {
    public static CopyOnWriteArrayList<TrackingElement> TrackingPlayersCountQueue = new CopyOnWriteArrayList<TrackingElement>();
    public static void TryAdd(TrackingElement element, AfterAddCallback callback)
    {
        ((Runnable) () -> {
            Optional<TrackingElement> existed = TrackingPlayersCountQueue.stream().filter(x->x.uuid == element.uuid).findFirst();
            if (existed.isPresent()) {
                existed.get().time = element.time;
                return;
            }
            callback.Call();
            TrackingPlayersCountQueue.add(element);
        }).run();
    }
    public static class AfterAddCallback{
        public EntityPlayerMP playerMP;
        public AfterAddCallback(EntityPlayerMP playerMP)
        {
            this.playerMP = playerMP;
        }
        public void Call()
        {

        }
    }

    public static class TrackingElement{
        public String uuid;
        public int time;
        public TrackingCallback callback;
        public TrackingElement(String uuid, int time, TrackingCallback callback)
        {
            this.uuid = uuid;
            this.time = time;
            this.callback = callback;
        }
    }
    public static class TrackingCallback{
        public EntityPlayerMP playerMP;
        public TrackingCallback(EntityPlayerMP playerMP)
        {
            this.playerMP = playerMP;
        }
        public void Call()
        {

        }
    }
}
