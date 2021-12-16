package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SyncXPStatsPacket implements IMessage {

    int lvl = 1;
    float currentXP = 0;
    float nextLvlXP = 10;
    public SyncXPStatsPacket(){}
    public SyncXPStatsPacket(int lvl, float currentXP, float nextLvlXP){
        this.lvl=lvl;
        this.currentXP=currentXP;
        this.nextLvlXP=nextLvlXP;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        lvl = buf.readInt();
        currentXP = buf.readFloat();
        nextLvlXP = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(lvl);
        buf.writeFloat(currentXP);
        buf.writeFloat(nextLvlXP);
    }
    public static class Handler implements IMessageHandler<SyncXPStatsPacket, IMessage> {
        @Override
        public IMessage onMessage(SyncXPStatsPacket message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    ClientData.ClientCurrentLvl = message.lvl;
                    ClientData.ClientCurrentExp = message.currentXP;
                    ClientData.ClientNextLvlExp = message.nextLvlXP;
                }
            });

            return null;
        }

    }
}
