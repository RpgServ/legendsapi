package me.maxitros.legends.effects;

import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.SpawnParticleLinePacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

public class LightningFlowPotion extends AdvancedModPotion {

    protected LightningFlowPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    public void performModEffect(AdvancedModPotionEffect effect, EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {
        if (duration > 0) {
            if(duration%4==0)
            {
                Vec3d addVec = entityLivingBaseIn.getLookVec();
                Vec3d eyeVec = entityLivingBaseIn.getPositionVector().add(new Vec3d(0, entityLivingBaseIn.getEyeHeight(),0));
                if(entityLivingBaseIn instanceof EntityPlayerMP) {
                    EntityPlayerMP playerMP = (EntityPlayerMP) entityLivingBaseIn;

                    PacketHandler.INSTANCE.sendTo(new SpawnParticleLinePacket(eyeVec, addVec, (byte) 5, EnumParticleTypes.END_ROD), playerMP);

                }
                Vec3d len = eyeVec.add(addVec.scale(5));
                PacketHandler.INSTANCE.sendToAllTracking(new SpawnParticleLinePacket(eyeVec, addVec, (byte) 5, EnumParticleTypes.END_ROD), entityLivingBaseIn);
                Vec3d p1 = new Vec3d(-0.5,-0.5, 0);
                Vec3d p2 = new Vec3d(+0.5,+0.5, 5);
                float pitchDec = entityLivingBaseIn.rotationPitch;
                float yawDec = (entityLivingBaseIn.rotationYaw);
                pitchDec = -pitchDec;
                yawDec = -yawDec;
                float pitch =pitchDec *3.1419f/180f;
                float yaw = yawDec*3.1419f/180f;

                p1 = new Vec3d(
                        p1.x,
                        p1.y*Math.cos(pitch)+p1.z*Math.sin(pitch),
                        -p1.y*Math.sin(pitch)+p1.z*Math.cos(pitch));
                p2 = new Vec3d(
                        p2.x,
                        p2.y*Math.cos(pitch)+p2.z*Math.sin(pitch),
                        -p2.y*Math.sin(pitch)+p2.z*Math.cos(pitch));
                p1 = new Vec3d(p1.x*Math.cos(yaw)+p1.z*Math.sin(yaw),
                        p1.y,
                        -p1.x*Math.sin(yaw)+p1.z*Math.cos(yaw));
                p2 = new Vec3d(p2.x*Math.cos(yaw)+p2.z*Math.sin(yaw),
                        p2.y,
                        -p2.x*Math.sin(yaw)+p2.z*Math.cos(yaw));


                p1 = p1.add(eyeVec);
                p2 = p2.add(eyeVec);
                AxisAlignedBB box = new AxisAlignedBB(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);

                for(EntityLivingBase base : entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box, x->!x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString())))
                {
                    effect.entityHashSet.add(base);

                }
            }
            if(duration%20==0)
            {
                for (EntityLivingBase entityLivingBase : effect.entityHashSet)
                {
                    entityLivingBase.attackEntityFrom(DamageSource.GENERIC, damage*((50f + 5f*(amplifier-1))/100f));
                    entityLivingBase.addPotionEffect(new ModPotionEffect(PotionRegistry.LightningResultPotion, 20*(2 + amplifier), amplifier, damage));
                }
                effect.entityHashSet = new HashSet<>();
            }

        }
    }
}
