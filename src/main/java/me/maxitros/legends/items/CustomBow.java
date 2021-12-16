package me.maxitros.legends.items;

import me.maxitros.legends.ClientData;
import me.maxitros.legends.Legends;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.SkillsData;
import me.maxitros.legends.effects.PotionRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Optional;

public class CustomBow extends ItemBow {

    public CustomBow(){
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.addPropertyOverride(new ResourceLocation(Legends.modId,"custompull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {

                    Optional<PotionEffect> effect =   entityIn.getActivePotionEffects().stream().filter(x->x.getPotion().equals(PotionRegistry.CowboyPotion)).findAny();
                    if(effect.isPresent()){
                        return !(entityIn.getActiveItemStack().getItem() instanceof CustomBow) ? 0.0F : (((float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount())/20.0F) * ((125f+((effect.get().getAmplifier()-1)*5))/100f));
                    }
                    return !(entityIn.getActiveItemStack().getItem() instanceof CustomBow) ? 0.0F : ((float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount())/20.0F);
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation(Legends.modId,"custompulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return super.getMaxItemUseDuration(stack);
    }
}
