package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.entities.magic.EntityIceCube;
import me.maxitros.legends.entities.magic.EntityMagicArrow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Optional;

public class SyncIceCube implements IMessage {
    String uuid;
    int maxlifetime;


    public SyncIceCube(){}

    public SyncIceCube(String uuid, int maxlifetime) {
        this.uuid = uuid;
        this.maxlifetime = maxlifetime;
            }

    @Override
    public void fromBytes(ByteBuf buf) {
        uuid = ByteBufUtils.readUTF8String(buf);
        maxlifetime = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, uuid);
        buf.writeInt(maxlifetime);
    }
    public static class Handler implements IMessageHandler<SyncIceCube, IMessage> {
        @Override
        public IMessage onMessage(SyncIceCube message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    Optional<EntityIceCube> ent =  minecraft.player.world.getEntities(EntityIceCube.class,
                            x->x.getCachedUniqueIdString().equals(message.uuid)).stream().findFirst();
                    if(ent.isPresent()){
                        EntityIceCube cube = ent.get();
                        cube.SetLifeTime(message.maxlifetime);



                    }
                }
            });

            return null;
        }

    }
}
