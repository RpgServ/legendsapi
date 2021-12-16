package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import me.maxitros.legends.ClientData;
import me.maxitros.legends.entities.magic.EntityMagicGrenade;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Optional;

public class SpawnClientGrenadePacket implements IMessage {
    String uuid;
    int effectRange;
    EnumParticleTypes particleFlightType;
    EnumParticleTypes particleDamageType;
    boolean hasRange;
    EntityMagicGrenade.GrenadeType grenadeType;

    public SpawnClientGrenadePacket(){}

    public SpawnClientGrenadePacket(String uuid, int effectRange, EnumParticleTypes particleFlightType, EnumParticleTypes particleDamageType, boolean hasRange, EntityMagicGrenade.GrenadeType grenadeType) {
        this.uuid = uuid;
        this.effectRange = effectRange;
        this.particleFlightType = particleFlightType;
        this.particleDamageType =particleDamageType;
        this.grenadeType = grenadeType;
        this.hasRange = hasRange;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        uuid = ByteBufUtils.readUTF8String(buf);
        effectRange = buf.readInt();
        particleFlightType = EnumParticleTypes.values()[buf.readInt()];
        particleDamageType = EnumParticleTypes.values()[buf.readInt()];
        grenadeType = EntityMagicGrenade.GrenadeType.values()[buf.readInt()];
        hasRange = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, uuid);
        buf.writeInt(effectRange);
        buf.writeInt(particleFlightType.ordinal());
        buf.writeInt(particleDamageType.ordinal());
        buf.writeInt(grenadeType.ordinal());
        buf.writeBoolean(hasRange);
    }
    public static class Handler implements IMessageHandler<SpawnClientGrenadePacket, IMessage> {
        @Override
        public IMessage onMessage(SpawnClientGrenadePacket message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
                return null;
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(new Runnable()
            {
                public void run() {
                    Optional<EntityMagicGrenade> ent =  minecraft.player.world.getEntities(EntityMagicGrenade.class,
                            x->x.getCachedUniqueIdString().equals(message.uuid)).stream().findFirst();
                    if(ent.isPresent()){
                        EntityMagicGrenade grenade = ent.get();
                        EntityMagicGrenade clientGrenade = new EntityMagicGrenade(minecraft.player.world, grenade.posX, grenade.posY, grenade.posZ);
                        clientGrenade.setVelocity(grenade.motionX, grenade.motionY, grenade.motionZ);
                        clientGrenade.setData(message.effectRange, message.particleFlightType, message.particleDamageType, message.hasRange, message.grenadeType);
                        minecraft.player.world.removeEntity(grenade);
                        minecraft.player.world.spawnEntity(clientGrenade);
                    }
                }
            });

            return null;
        }

    }
}
