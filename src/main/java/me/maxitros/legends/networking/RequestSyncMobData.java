package me.maxitros.legends.networking;

import com.google.common.base.Predicate;
import io.netty.buffer.ByteBuf;
import me.maxitros.legends.capabilities.IMobLvl;
import me.maxitros.legends.capabilities.providers.MobLvlProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class RequestSyncMobData implements IMessage {

    String mobUUID;

    public RequestSyncMobData() {
    }

    public RequestSyncMobData(String mobUUID)
    {
        this.mobUUID = mobUUID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        mobUUID = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, mobUUID);
    }
    public static class Handler implements IMessageHandler<RequestSyncMobData, IMessage> {
        @Override
        public IMessage onMessage(RequestSyncMobData message, MessageContext ctx) {
            System.out.println(message.mobUUID);
            Optional<EntityLivingBase> ent = ctx.getServerHandler().player.world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                @Override
                public boolean apply(@Nullable EntityLivingBase input) {
                    if(input.getCachedUniqueIdString().equals(message.mobUUID))
                        return true;
                    return false;
                }
            }).stream().findAny();
            if(ent.isPresent())
            {
                IMobLvl mobLvl = ent.get().getCapability(MobLvlProvider.MOBLVL_CAP, null);
                PacketHandler.INSTANCE.sendTo(new SyncMobData(mobLvl.GetLvl(), mobLvl.GetHp(), mobLvl.IsDefault(), message.mobUUID), ctx.getServerHandler().player);
            }
            return null;
        }

    }
}
