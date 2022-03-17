package me.maxitros.legends.networking;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Set;

public class PacketHandler {
    private static int packetId = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int nextID() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(SyncMagicStatsPacket.Handler.class, SyncMagicStatsPacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncSkillData.Handler.class, SyncSkillData.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncXPStatsPacket.Handler.class, SyncXPStatsPacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncSkillCooldown.Handler.class, SyncSkillCooldown.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(ProceedActiveSpellPacket.Handler.class, ProceedActiveSpellPacket.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(SpawnClientGrenadePacket.Handler.class, SpawnClientGrenadePacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SpawnClientArrowPacket.Handler.class, SpawnClientArrowPacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SpawnParticleLinePacket.Handler.class, SpawnParticleLinePacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SpawnRingPacket.Handler.class, SpawnRingPacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SpawnTrianglePartPacket.Handler.class, SpawnTrianglePartPacket.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncIceCube.Handler.class, SyncIceCube.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncMine.Handler.class, SyncMine.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncMobData.Handler.class, SyncMobData.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(RequestSyncMobData.Handler.class, RequestSyncMobData.class, nextID(), Side.SERVER);
    }
}
