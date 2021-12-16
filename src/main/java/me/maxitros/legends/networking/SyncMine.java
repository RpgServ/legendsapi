package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.entities.magic.EntityIceCube;
import me.maxitros.legends.entities.magic.EntityMine;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public class SyncMine implements IMessage {
    String uuid;
    int maxlifetime;


    public SyncMine(){}

    public SyncMine(String uuid, int maxlifetime) {
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
    public static class Handler implements IMessageHandler<SyncMine, IMessage> {
        @Override
        public IMessage onMessage(SyncMine message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    Optional<EntityMine> ent =  minecraft.player.world.getEntities(EntityMine.class,
                            x->x.getCachedUniqueIdString().equals(message.uuid)).stream().findFirst();
                    if(ent.isPresent()){
                        EntityMine cube = ent.get();
                        cube.SetLifeTime(message.maxlifetime);
                    }
                }
            });

            return null;
        }

    }
}
