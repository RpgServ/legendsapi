package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;

public class LightningSpeedPotion extends ModPotion{

    protected LightningSpeedPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    @Override
    public void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {

    }
}
