package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ModPotionEffect extends PotionEffect {
    private final ModPotion potion;
    private final float damage;
    public ModPotionEffect(ModPotion potionIn, float damage) {
        super(potionIn);
        potion = potionIn;
        this.damage = damage;
    }

    public ModPotionEffect(ModPotion potionIn, int durationIn, float damage) {
        super(potionIn, durationIn);
        potion = potionIn;
        this.damage = damage;
    }

    public ModPotionEffect(ModPotion potionIn, int durationIn, int amplifierIn, float damage) {
        super(potionIn, durationIn, amplifierIn);
        potion = potionIn;
        this.damage = damage;
    }

    public ModPotionEffect(ModPotion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn, float damage) {
        super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
        potion = potionIn;
        this.damage = damage;
    }

    public ModPotionEffect(ModPotionEffect other) {
        super(other);
        potion = other.getModPotion();
        this.damage = other.getModDamage();
    }
    public ModPotion getModPotion(){
        return  potion;
    }
    public float getModDamage(){
        return damage;
    }

    @Override
    public boolean onUpdate(EntityLivingBase entityIn) {
        return super.onUpdate(entityIn);
    }

    @Override
    public void performEffect(EntityLivingBase entityIn) {
        potion.performModEffect(entityIn, this.getAmplifier(), this.getDuration(), this.damage);
    }
}
