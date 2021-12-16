package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SyncSkillData implements IMessage {

    byte[] skills = new byte[SkillsEnum.Count];
    SkillsEnum active;
    float baseDamage;
    float additionalDamage;
    public SyncSkillData(){}
    public SyncSkillData(byte[] skills, SkillsEnum active, float baseDamage, float additionalDamage){
        this.skills = skills;
        this.active = active;
        this.baseDamage = baseDamage;
        this.additionalDamage = additionalDamage;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        buf.readBytes(skills);
        active = SkillsEnum.values()[buf.readInt()];
        baseDamage = buf.readFloat();
        additionalDamage = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(skills);
        buf.writeInt(active.ordinal());
        buf.writeFloat(baseDamage);
        buf.writeFloat(additionalDamage);
    }
    public static class Handler implements IMessageHandler<SyncSkillData, IMessage> {
        @Override
        public IMessage onMessage(SyncSkillData message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    if(message.active==null)
                        ClientData.CurrentSkill = SkillsEnum.EMPTY;
                    else
                        ClientData.CurrentSkill = message.active;
                    ClientData.Skills = message.skills;
                    ClientData.CurrentDamage = message.baseDamage;
                    ClientData.AdditionalDamage = message.additionalDamage;
                }
            });

            return null;
        }

    }
}
