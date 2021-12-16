package me.maxitros.legends.mixin;

import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.SkillsData;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemShield;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity{

    @Shadow protected abstract boolean isPlayer();

    @Shadow public abstract boolean isServerWorld();

    @Shadow @Nullable public abstract <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing);

    @Inject(method = "getJumpUpwardsMotion", at = @At("HEAD"), cancellable = true)
    protected void getJumpUpwardsMotion(CallbackInfoReturnable<Float> cir)
    {
        if(this.isPlayer())
        {
            if(!this.world.isRemote)
            {
                ISkillsData skillsData = this.getCapability(SkillsDataProvider.SKILLS_CAP, null);
                int lvl = SkillsData.GetSkillLvl(SkillsEnum.Talent_Jumper, skillsData.GetSkillData());
                if(lvl>0)
                {
                    cir.setReturnValue(0.42F*(1.05f+0.05f*(lvl-1)));
                }
            }
            else
            {
                int lvl = SkillsData.GetSkillLvl(SkillsEnum.Talent_Jumper, ClientData.Skills);
                if(lvl>0)
                {
                    cir.setReturnValue(0.42F*(1.05f+0.05f*(lvl-1)));
                }
            }
        }
    }
}
