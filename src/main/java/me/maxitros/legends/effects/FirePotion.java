package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class FirePotion extends ModPotion {

    protected FirePotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    public void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {
        if (duration > 0 && duration%20 == 0)
        {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, damage*((5f)/100f));
        }
    }
}
