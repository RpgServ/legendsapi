package me.maxitros.legends.networking;


import net.minecraft.util.math.Vec2f;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.vecmath.Point3d;
import java.util.Random;

public class SpawnTrianglePartPacket implements IMessage {
    Vec3d pos1, pos2, pos3;

    public SpawnTrianglePartPacket(){}

    public SpawnTrianglePartPacket(Vec3d pos1, Vec3d pos2,Vec3d pos3) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos1 = new Vec3d(buf.readDouble(), buf.readDouble(),buf.readDouble());
        this.pos2 = new Vec3d(buf.readDouble(), buf.readDouble(),buf.readDouble());
        this.pos3 = new Vec3d(buf.readDouble(), buf.readDouble(),buf.readDouble());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(pos1.x);
        buf.writeDouble(pos1.y);
        buf.writeDouble(pos1.z);
        buf.writeDouble(pos2.x);
        buf.writeDouble(pos2.y);
        buf.writeDouble(pos2.z);
        buf.writeDouble(pos3.x);
        buf.writeDouble(pos3.y);
        buf.writeDouble(pos3.z);
    }
    public static class Handler implements IMessageHandler<SpawnTrianglePartPacket, IMessage> {
        @Override
        public IMessage onMessage(SpawnTrianglePartPacket message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    for (int i = 0; i<15;i++)
                    {
                        Vec3d pos = getPoint(
                                minecraft.player.getRNG().nextDouble(),
                                minecraft.player.getRNG().nextDouble(),
                                message.pos1.x,
                                message.pos1.y,
                                message.pos2.x,
                                message.pos2.y,
                                message.pos3.x,
                                message.pos3.y,
                                message.pos1.z,
                                message.pos2.z,
                                message.pos3.z);
                        minecraft.world.spawnParticle(EnumParticleTypes.FLAME,
                                pos.x,
                                pos.y,
                                pos.z,
                                (minecraft.player.getRNG().nextFloat()-0.5)*0.1,
                                (minecraft.player.getRNG().nextFloat()-0.5)*0.1,
                                (minecraft.player.getRNG().nextFloat()-0.5)*0.1);

                    }
                }
            });

            return null;
        }
        private Vec3d getPoint(double a, double b, double Ax, double Ay, double Bx, double By, double Cx, double Cy, double Az, double Bz, double Cz)
        {
            double c = 0;
            double px, py, pz;
            if (a + b > 1)
            {
                a = 1 - a;
                b = 1 - b;
            }
            c = 1 - a - b;

            px = (a * Ax) + (b * Bx) + (c * Cx);
            py = (a * Ay) + (b * By) + (c * Cy);
            pz = (a * Az) + (b * Bz) + (c * Cz);
            return new Vec3d(px, py, pz);
        }


    }
}