package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.ClientData;
import me.maxitros.legends.capabilities.IMagicStats;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SyncMagicStatsPacket implements IMessage {

    float currentMana;
    float maxMana;
    public SyncMagicStatsPacket(){}
    public SyncMagicStatsPacket(float currentMana, float maxMana){
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        currentMana = buf.readFloat();
        maxMana = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(currentMana);
        buf.writeFloat(maxMana);
    }
    public static class Handler implements IMessageHandler<SyncMagicStatsPacket, IMessage> {
        @Override
        public IMessage onMessage(SyncMagicStatsPacket message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    ClientData.ClientMana = message.currentMana;
                    ClientData.ClientMaxMana = message.maxMana;
                }
            });

            return null;
        }

    }
}
