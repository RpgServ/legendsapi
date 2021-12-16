package me.maxitros.legends.effects;

import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.SpawnParticleLinePacket;
import me.maxitros.legends.networking.SpawnRingPacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.List;

public class FireRingPotion extends AdvancedModPotion {

    protected FireRingPotion(boolean isBadEffectIn, int liquidColorIn) {
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

                Vec3d eyeVec = entityLivingBaseIn.getPositionVector().add(new Vec3d(0, entityLivingBaseIn.getEyeHeight()/2f,0));
                if(entityLivingBaseIn instanceof EntityPlayerMP) {
                    EntityPlayerMP playerMP = (EntityPlayerMP) entityLivingBaseIn;
                    PacketHandler.INSTANCE.sendTo(new SpawnRingPacket(eyeVec), playerMP);

                }
                PacketHandler.INSTANCE.sendToAllTracking(new SpawnRingPacket(eyeVec), entityLivingBaseIn);
                AxisAlignedBB box1 = new AxisAlignedBB(eyeVec.x-2.5,eyeVec.y-0.5,eyeVec.z-2.5,eyeVec.x+2.5,eyeVec.y+0.5,eyeVec.z+2.5);
                AxisAlignedBB box2 = new AxisAlignedBB(eyeVec.x-2,eyeVec.y-0.5,eyeVec.z-2,eyeVec.x+2,eyeVec.y+0.5,eyeVec.z+2);
                List<EntityLivingBase> box1ent = entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box1, x->!x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString()));
                List<EntityLivingBase> box2ent = entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, box2, x->!x.getCachedUniqueIdString().equals(entityLivingBaseIn.getCachedUniqueIdString()));
                box1ent.removeAll(box2ent);
                for(EntityLivingBase base : box1ent)
                {
                    effect.entityHashSet.add(base);
                }
            }
            if(duration%20==0)
            {
                for (EntityLivingBase entityLivingBase :  effect.entityHashSet)
                {
                    entityLivingBase.attackEntityFrom(DamageSource.MAGIC, damage*2);
                    entityLivingBase.addPotionEffect(new ModPotionEffect(PotionRegistry.FireFlowResultPotion, 20*(3 + amplifier), amplifier, damage));
                }
                effect.entityHashSet = new HashSet<>();
            }

        }
    }
}
