package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;

public class RepulsedPotion extends ModPotion {

    protected RepulsedPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    public void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {
    }
}
