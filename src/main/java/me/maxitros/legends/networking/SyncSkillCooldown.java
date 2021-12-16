package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SyncSkillCooldown implements IMessage {

    byte[] cooldowns = new byte[SkillsEnum.Count];

    public SyncSkillCooldown(){}
    public SyncSkillCooldown(byte[] cooldowns){
        this.cooldowns = cooldowns;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        buf.readBytes(cooldowns);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(cooldowns);
    }
    public static class Handler implements IMessageHandler<SyncSkillCooldown, IMessage> {
        @Override
        public IMessage onMessage(SyncSkillCooldown message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    ClientData.SkillsCooldowns = message.cooldowns;
                }
            });

            return null;
        }

    }
}
