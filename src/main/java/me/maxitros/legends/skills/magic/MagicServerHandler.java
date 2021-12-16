package me.maxitros.legends.skills.magic;

import me.maxitros.legends.effects.ModPotionEffect;
import me.maxitros.legends.effects.PotionRegistry;
import me.maxitros.legends.entities.magic.EntityMagicArrow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MagicServerHandler {
    @SubscribeEvent
    public void onArrowDamage(LivingHurtEvent event)
    {
        if(event.getSource().damageType.equals("arrow"))
        {
            if(event.getSource().getTrueSource() instanceof EntityPlayerMP && event.getEntity() instanceof EntityLivingBase)
            {
                EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();
                EntityLivingBase target = (EntityLivingBase) event.getEntity();
                if(event.getSource().getImmediateSource() instanceof EntityMagicArrow)
                {
                    EntityMagicArrow arrow = (EntityMagicArrow) event.getSource().getImmediateSource();
                    //target.attackEntityFrom(DamageSource.GENERIC, (0.5f + 0.1f * (arrow.getLvl())) * arrow.getModDamage());
                    //target.addPotionEffect(new ModPotionEffect(PotionRegistry.FirePotion, 20 * (3 + arrow.getLvl() * (1 - 1)), arrow.getLvl(), arrow.getModDamage()));
                    target.attackEntityFrom(DamageSource.GENERIC, arrow.getModDamage());
                    target.addPotionEffect(arrow.getEffect());
                }
            }
        }
    }
}
