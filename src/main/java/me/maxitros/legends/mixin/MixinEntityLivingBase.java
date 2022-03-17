package me.maxitros.legends.mixin;

import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.IExpStats;
import me.maxitros.legends.capabilities.IMobLvl;
import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.SkillsData;
import me.maxitros.legends.capabilities.providers.ExpStatsProvider;
import me.maxitros.legends.capabilities.providers.MobLvlProvider;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.RequestSyncMobData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.EnumFacing;
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

    @Shadow public abstract void fall(float distance, float damageMultiplier);

    @Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
    public final void getMaxHealth(CallbackInfoReturnable<Float> cir)
    {
        if(this.isPlayer()) {
            if(!this.world.isRemote)
            {
                IExpStats expStats = this.getCapability(ExpStatsProvider.EXP_STATS_CAPABILITY_CAP, null);
                cir.setReturnValue((float) (20+(expStats.GetCurrentLvl()-1)));
            }
            else
            {
                cir.setReturnValue((float) (20+(ClientData.ClientCurrentLvl-1)));
            }
        }
        else
        {
            IMobLvl mobLvl = this.getCapability(MobLvlProvider.MOBLVL_CAP, null);
            if(!mobLvl.IsDefault())
                cir.setReturnValue(mobLvl.GetHp());
        }
    }

    boolean firstupd = true;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void onUpdate(CallbackInfo ci)
    {
        if(!this.isPlayer() &&firstupd) {
            if (this.world.isRemote) {
                PacketHandler.INSTANCE.sendToServer(new RequestSyncMobData(this.getCachedUniqueIdString()));
                firstupd = false;
            }
        }
    }


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
