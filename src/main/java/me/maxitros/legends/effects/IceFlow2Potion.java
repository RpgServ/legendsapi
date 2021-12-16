package me.maxitros.legends.effects;

import me.maxitros.legends.api.Location;
import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.SpawnParticleLinePacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class IceFlow2Potion extends AdvancedModPotion {

    protected IceFlow2Potion(boolean isBadEffectIn, int liquidColorIn) {
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
                if(effect.active==null) {
                    Vec3d addVec = entityLivingBaseIn.getLookVec();
                    Vec3d eyeVec = entityLivingBaseIn.getPositionVector().add(new Vec3d(0, entityLivingBaseIn.getEyeHeight(), 0));
                    if (entityLivingBaseIn instanceof EntityPlayerMP) {
                        EntityPlayerMP playerMP = (EntityPlayerMP) entityLivingBaseIn;
                        PacketHandler.INSTANCE.sendTo(new SpawnParticleLinePacket(eyeVec, addVec, (byte) 5, EnumParticleTypes.SNOWBALL), playerMP);

                    }
                    PacketHandler.INSTANCE.sendToAllTracking(new SpawnParticleLinePacket(eyeVec, addVec, (byte) 5, EnumParticleTypes.SNOWBALL), entityLivingBaseIn);
                    Vec3d p1 = new Vec3d(-0.5, -0.5, 0);
                    Vec3d p2 = new Vec3d(+0.5, +0.5, 5);
                    float pitchDec = entityLivingBaseIn.rotationPitch;
                    float yawDec = (entityLivingBaseIn.rotationYaw);
                    if (yawDec >= -90 && yawDec <= 90)
                        pitchDec = -pitchDec;
                    float pitch = pitchDec * 3.1419f / 180f;
                    float yaw = yawDec * 3.1419f / 180f;
                    yaw = yaw + -yaw * 2;
                    p1 = new Vec3d(p1.x * Math.cos(yaw) + p1.z * Math.sin(yaw),
                            p1.y,
                            -p1.x * Math.sin(yaw) + p1.z * Math.cos(yaw));
                    p2 = new Vec3d(p2.x * Math.cos(yaw) + p2.z * Math.sin(yaw),
                            p2.y,
                            -p2.x * Math.sin(yaw) + p2.z * Math.cos(yaw));

                    p1 = new Vec3d(
                            p1.x,
                            p1.y * Math.cos(pitch) + p1.z * Math.sin(pitch),
                            -p1.y * Math.sin(pitch) + p1.z * Math.cos(pitch));
                    p2 = new Vec3d(
                            p2.x,
                            p2.y * Math.cos(pitch) + p2.z * Math.sin(pitch),
                            -p2.y * Math.sin(pitch) + p2.z * Math.cos(pitch));
                    p1 = p1.add(eyeVec);
                    p2 = p2.add(eyeVec);
                    AxisAlignedBB box = new AxisAlignedBB(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
                    Optional<EntityLivingBase> opt = entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box, x -> !x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString())).stream().findAny();
                    if(opt.isPresent())
                    {
                        effect.active = opt.get();
                        effect.target = effect.active ;


                    }
                }
                else
                {
                    Vec3d entPos =  effect.active.getPositionVector().add(new Vec3d(0, entityLivingBaseIn.getEyeHeight()/2f, 0));
                    Vec3d eyeVec = entityLivingBaseIn.getPositionVector().add(new Vec3d(0, entityLivingBaseIn.getEyeHeight(), 0));
                    Vec3d addVec = entPos.subtract(eyeVec).normalize();
                    if (entityLivingBaseIn instanceof EntityPlayerMP) {
                        EntityPlayerMP playerMP = (EntityPlayerMP) entityLivingBaseIn;
                        PacketHandler.INSTANCE.sendTo(new SpawnParticleLinePacket(eyeVec, addVec, (byte) 5, EnumParticleTypes.SNOWBALL), playerMP);

                    }
                    PacketHandler.INSTANCE.sendToAllTracking(new SpawnParticleLinePacket(eyeVec, addVec, (byte) 5, EnumParticleTypes.SNOWBALL), entityLivingBaseIn);
                    Vec3d p1 = new Vec3d(-0.5, -0.5, 0);
                    Vec3d p2 = new Vec3d(+0.5, +0.5, 5);
                    //double len = addVec.lengthVector();
                    Location l = new Location(entityLivingBaseIn.world, eyeVec.x,eyeVec.y,eyeVec.z);
                    l.setDirection(addVec);

                    float yawDec = l.getYaw();
                    float pitchDec = l.getPitch();
                    pitchDec = -pitchDec;
                    yawDec = -yawDec;
                    float pitch = pitchDec * 3.1419f / 180f;
                    float yaw = yawDec * 3.1419f / 180f;


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


                    p1 = p1.add(eyeVec);
                    p2 = p2.add(eyeVec);
                    AxisAlignedBB box = new AxisAlignedBB(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
                    Optional<EntityLivingBase> opt = entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box, x -> x.getCachedUniqueIdString().equals( effect.active.getCachedUniqueIdString())).stream().findAny();
                    if(opt.isPresent())
                    {
                        effect.target = opt.get();

                    }
                }
            }
            if(duration%20 == 0&&effect.target!=null&&!effect.target.isDead)
            {
                effect.target.attackEntityFrom(DamageSource.GENERIC, damage*((25f + 5f*(amplifier-1))/100f));
                effect.target.addPotionEffect(new ModPotionEffect(PotionRegistry.IceStunResultPotion, 20*(5 + amplifier), amplifier, damage));
                effect.target = null;
            }

        }
    }
}
