package me.maxitros.legends.effects;

import me.maxitros.legends.api.Location;
import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.SpawnParticleLinePacket;
import me.maxitros.legends.networking.SpawnTrianglePartPacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Optional;

public class FireWavePotion extends AdvancedModPotion {

    protected FireWavePotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    public void performModEffect(AdvancedModPotionEffect effect, EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {
        if (duration > 0) {
            if(duration%4==0) {
                if (effect.data.size() == 0) {
                    float pitchDec = entityLivingBaseIn.rotationPitch;
                    float yawDec = (entityLivingBaseIn.rotationYaw);
                    pitchDec = -pitchDec;
                    yawDec = -yawDec;
                    float pitch =pitchDec *3.1419f/180f;
                    float yaw = yawDec*3.1419f/180f;


                    effect.data.add(entityLivingBaseIn.getLookVec().scale(0.2));
                    effect.data.add(entityLivingBaseIn.getPositionVector());
                    for (int i = 0; i < 4; i++) {
                        Vec3d p1, p2;
                        switch (i) {
                            case 0:
                                p1 = new Vec3d(-2.5, 0, 0);
                                p2 = new Vec3d(2.5, 3, 0.2);
                                break;
                            case 1:
                                p1 = new Vec3d(-1.75, 3, 0);
                                p2 = new Vec3d(1.75, 7, 0.2);
                                break;
                            case 2:
                                p1 = new Vec3d(-0.75, 7, 0);
                                p2 = new Vec3d(0.75, 11, 0.2);
                                break;
                            default:
                                p1 = new Vec3d(2.5, 0, 0);
                                p2 = new Vec3d(0, 11, 0);
                                break;
                        }
                        p1 = new Vec3d(
                                p1.x,
                                p1.y * Math.cos(pitch) + p1.z * Math.sin(pitch),
                                -p1.y * Math.sin(pitch) + p1.z * Math.cos(pitch));
                        p2 = new Vec3d(
                                p2.x,
                                p2.y * Math.cos(pitch) + p2.z * Math.sin(pitch),
                                -p2.y * Math.sin(pitch) + p2.z * Math.cos(pitch));
                        p1 = new Vec3d(p1.x * Math.cos(yaw) + p1.z * Math.sin(yaw),
                                p1.y,
                                -p1.x * Math.sin(yaw) + p1.z * Math.cos(yaw));
                        p2 = new Vec3d(p2.x * Math.cos(yaw) + p2.z * Math.sin(yaw),
                                p2.y,
                                -p2.x * Math.sin(yaw) + p2.z * Math.cos(yaw));


                        effect.data.add(p1);
                        effect.data.add(p2);
                    }


                } else {
                    Vec3d addVec = (Vec3d) effect.data.get(0);
                    Vec3d eyeVec = (Vec3d) effect.data.get(1);
                    Vec3d p1 = (Vec3d) effect.data.get(2);
                    Vec3d p2 = (Vec3d) effect.data.get(3);
                    Vec3d p3 = (Vec3d) effect.data.get(4);
                    Vec3d p4 = (Vec3d) effect.data.get(5);
                    Vec3d p5 = (Vec3d) effect.data.get(6);
                    Vec3d p6 = (Vec3d) effect.data.get(7);
                    Vec3d p7 = (Vec3d) effect.data.get(8);
                    Vec3d p8 = (Vec3d) effect.data.get(9);
                    p1 = p1.add(eyeVec);
                    p2 = p2.add(eyeVec);
                    p3 = p3.add(eyeVec);
                    p4 = p4.add(eyeVec);
                    p5 = p5.add(eyeVec);
                    p6 = p6.add(eyeVec);
                    p7 = p7.add(eyeVec);
                    p8 = p8.add(eyeVec);
                    AxisAlignedBB box1 = new AxisAlignedBB(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
                    AxisAlignedBB box2 = new AxisAlignedBB(p3.x, p3.y, p3.z, p4.x, p4.y, p4.z);
                    AxisAlignedBB box3 = new AxisAlignedBB(p5.x, p5.y, p5.z, p6.x, p6.y, p6.z);
                    for (EntityLivingBase base : entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box1, x -> !x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString()))) {
                        effect.entityHashSet.add(base);

                    }
                    for (EntityLivingBase base : entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box2, x -> !x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString()))) {
                        effect.entityHashSet.add(base);

                    }
                    for (EntityLivingBase base : entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box3, x -> !x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString()))) {
                        effect.entityHashSet.add(base);

                    }
                    if (entityLivingBaseIn instanceof EntityPlayerMP) {
                        EntityPlayerMP playerMP = (EntityPlayerMP) entityLivingBaseIn;
                        PacketHandler.INSTANCE.sendTo(new SpawnTrianglePartPacket(p1,p7,p8), playerMP);
                    }
                    PacketHandler.INSTANCE.sendToAllTracking(new SpawnTrianglePartPacket(p1,p7,p8), entityLivingBaseIn);
                    effect.data.set(1, eyeVec.add(addVec));
                }
            }
            if(duration%20==0)
            {
                for (EntityLivingBase entityLivingBase : effect.entityHashSet)
                {
                    entityLivingBase.attackEntityFrom(DamageSource.MAGIC, damage*((25f + 5f*(amplifier-1))/100f));
                    entityLivingBase.addPotionEffect(new ModPotionEffect(PotionRegistry.FireFlowResultPotion, 20*(3 + amplifier), amplifier, damage));
                }
                effect.entityHashSet = new HashSet<>();
            }
        }
    }
}
