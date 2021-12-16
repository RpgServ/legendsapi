package me.maxitros.legends.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AdvancedModPotionEffect extends PotionEffect {
    private final AdvancedModPotion potion;
    private final float damage;
    public EntityLivingBase active, target;
    public List<Object> data = new ArrayList<>();
    HashSet<EntityLivingBase> entityHashSet = new HashSet<>();
    public AdvancedModPotionEffect(AdvancedModPotion potionIn, float damage) {
        super(potionIn);
        potion = potionIn;
        this.damage = damage;
    }

    public AdvancedModPotionEffect(AdvancedModPotion potionIn, int durationIn, float damage) {
        super(potionIn, durationIn);
        potion = potionIn;
        this.damage = damage;
    }

    public AdvancedModPotionEffect(AdvancedModPotion potionIn, int durationIn, int amplifierIn, float damage) {
        super(potionIn, durationIn, amplifierIn);
        potion = potionIn;
        this.damage = damage;
    }

    public AdvancedModPotionEffect(AdvancedModPotion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn, float damage) {
        super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
        potion = potionIn;
        this.damage = damage;
    }

    public AdvancedModPotionEffect(AdvancedModPotionEffect other) {
        super(other);
        potion = other.getModPotion();
        this.damage = other.getModDamage();
    }
    public AdvancedModPotion getModPotion(){
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
        potion.performModEffect(this, entityIn, this.getAmplifier(), this.getDuration(), this.damage);
    }
}
