package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.entities.magic.EntityMagicGrenade;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Optional;

public class SpawnParticleLinePacket implements IMessage {
    Vec3d pos1, pos2;
    byte range;
    EnumParticleTypes type;

    public SpawnParticleLinePacket(){}

    public SpawnParticleLinePacket(Vec3d pos1,Vec3d pos2, byte range, EnumParticleTypes type) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.range = range;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos1 = new Vec3d(buf.readDouble(), buf.readDouble(),buf.readDouble());
        this.pos2 = new Vec3d(buf.readDouble(), buf.readDouble(),buf.readDouble());
        this.range = buf.readByte();
        this.type = EnumParticleTypes.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(pos1.x);
        buf.writeDouble(pos1.y);
        buf.writeDouble(pos1.z);
        buf.writeDouble(pos2.x);
        buf.writeDouble(pos2.y);
        buf.writeDouble(pos2.z);
        buf.writeByte(range);
        buf.writeInt(type.ordinal());
    }
    public static class Handler implements IMessageHandler<SpawnParticleLinePacket, IMessage> {
        @Override
        public IMessage onMessage(SpawnParticleLinePacket message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    for (int i = 0; i<5;i++)
                    {
                        minecraft.world.spawnParticle(
                                message.type,
                                message.pos1.x,
                                message.pos1.y,
                                message.pos1.z,
                                (minecraft.player.getRNG().nextFloat()-0.5)*0.1,
                                (minecraft.player.getRNG().nextFloat()-0.5)*0.1,
                                (minecraft.player.getRNG().nextFloat()-0.5)*0.1);
                        message.pos1 = message.pos1.add(message.pos2);

                    }
                }
            });

            return null;
        }

    }
}
