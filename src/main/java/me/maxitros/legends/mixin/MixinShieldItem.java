package me.maxitros.legends.mixin;

import me.maxitros.legends.effects.PotionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemShield.class)
public class MixinShieldItem {
    @Inject(method = "onItemRightClick", at = @At("HEAD"), cancellable = true)
    private void onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        if (playerIn.getActivePotionEffects().stream().anyMatch(x->x.getPotion().equals(PotionRegistry.RepulsedPotion))) {
            cir.setReturnValue(new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn)));
        }

    }
}
