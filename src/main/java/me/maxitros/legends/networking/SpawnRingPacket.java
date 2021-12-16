package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SpawnRingPacket implements IMessage {
    Vec3d pos1;

    public SpawnRingPacket(){}

    public SpawnRingPacket(Vec3d pos1) {
        this.pos1 = pos1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos1 = new Vec3d(buf.readDouble(), buf.readDouble(),buf.readDouble());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(pos1.x);
        buf.writeDouble(pos1.y);
        buf.writeDouble(pos1.z);
    }
    public static class Handler implements IMessageHandler<SpawnRingPacket, IMessage> {
        @Override
        public IMessage onMessage(SpawnRingPacket message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    for (int i = 0; i<4;i++)
                    {
                        for(int n =  0;n<5;n++) {
                            double cx = 0, cz = 0;
                            switch (i){
                                case 0: cx =   message.pos1.x - 2.5; cz = message.pos1.z - 2.5 + n; break;
                                case 1: cx =   message.pos1.x + 2.5; cz = message.pos1.z - 2.5 + n; break;
                                case 2: cx =   message.pos1.x - 2.5 + n; cz = message.pos1.z - 2.5; break;
                                case 3: cx =   message.pos1.x - 2.5 + n; cz = message.pos1.z + 2.5; break;
                            }
                            minecraft.world.spawnParticle(EnumParticleTypes.FLAME,
                                    cx + minecraft.player.getRNG().nextFloat()*0.1,
                                    message.pos1.y,
                                    cz + minecraft.player.getRNG().nextFloat()*0.1,
                                    (minecraft.player.getRNG().nextFloat() - 0.5) * 0.1,
                                    (minecraft.player.getRNG().nextFloat() - 0.5) * 0.1,
                                    (minecraft.player.getRNG().nextFloat() - 0.5) * 0.1);
                        }

                    }
                }
            });

            return null;
        }

    }
}
