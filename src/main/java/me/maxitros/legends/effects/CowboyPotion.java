package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class CowboyPotion extends ModPotion {

    protected CowboyPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {

    }
}
