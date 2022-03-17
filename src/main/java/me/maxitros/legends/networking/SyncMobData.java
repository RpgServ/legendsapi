package me.maxitros.legends.networking;

import com.google.common.base.Predicate;
import com.sun.jna.WString;
import io.netty.buffer.ByteBuf;
import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.IMobLvl;
import me.maxitros.legends.capabilities.providers.MobLvlProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.Sys;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class SyncMobData implements IMessage {

    int lvl;
    float hp;
    boolean isDefault;
    String mobUUID;

    public SyncMobData() {
    }

    public SyncMobData(int lvl, float hp, boolean isDefault, String mobUUID)
    {
        this.hp = hp;
        this.lvl = lvl;
        this.isDefault = isDefault;
        this.mobUUID = mobUUID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hp = buf.readFloat();
        lvl = buf.readInt();
        isDefault = buf.readBoolean();
        mobUUID = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(hp);
        buf.writeInt(lvl);
        buf.writeBoolean(isDefault);
        ByteBufUtils.writeUTF8String(buf, mobUUID);
    }
    public static class Handler implements IMessageHandler<SyncMobData, IMessage> {
        @Override
        public IMessage onMessage(SyncMobData message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    Optional<EntityLivingBase> ent = minecraft.world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                        @Override
                        public boolean apply(@Nullable EntityLivingBase input) {
                            if(input.getCachedUniqueIdString().equals(message.mobUUID))
                                return true;
                            return false;
                        }
                    }).stream().findAny();
                    if(ent.isPresent())
                    {
                        System.out.println("SYNCED");
                        IMobLvl mobLvl = ent.get().getCapability(MobLvlProvider.MOBLVL_CAP, null);
                        mobLvl.SetLvl(message.lvl);
                        mobLvl.SetHp(message.hp);
                        mobLvl.SetDefault(message.isDefault);
                    }
                }
            });

            return null;
        }

    }
}
