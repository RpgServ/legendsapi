package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class MonkPotion extends ModPotion {

    protected MonkPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void performModEffect(EntityLivingBase entityLivingBaseIn, int amplifier, int duration, float damage) {

    }
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier)
    {
        return modifier.getAmount() * (double)(1+ 0.5d*(amplifier-1));
    }
}
