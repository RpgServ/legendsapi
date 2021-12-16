package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public abstract class ModPotion extends Potion {


    protected ModPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    public abstract void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage);
    public Potion setModIconIndex(int p_76399_1_, int p_76399_2_) {
        return super.setIconIndex(p_76399_1_, p_76399_2_);
    }
}
