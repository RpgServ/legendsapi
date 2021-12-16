package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class HackedPotion extends ModPotion {

    protected HackedPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    public void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {
        if (duration > 0 && duration%20 == 0)
        {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, damage*((50f+10*(amplifier-1))/100f));
        }
    }
}
