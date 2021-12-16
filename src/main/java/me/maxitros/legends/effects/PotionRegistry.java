package me.maxitros.legends.effects;

import me.maxitros.legends.Legends;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionRegistry {
    public static StabbingWoundPotion StabbingWoundPotion = ((StabbingWoundPotion)((StabbingWoundPotion)(new StabbingWoundPotion(true, 3484199).setPotionName("effect.StabbingWoundPotion").setRegistryName(Legends.modId, "StabbingWoundPotion").setBeneficial())).setModIconIndex(1, 2));
    public static IncisionPotion IncisionPotion = ((IncisionPotion)((IncisionPotion)(new IncisionPotion(true, 3484199).setPotionName("effect.IncisionPotion").setRegistryName(Legends.modId, "IncisionPotion").setBeneficial())).setModIconIndex(1, 2));
    public static HackedPotion HackedPotion = ((HackedPotion)((HackedPotion)(new HackedPotion(true, 3484199).setPotionName("effect.HackedPotion").setRegistryName(Legends.modId, "HackedPotion").setBeneficial())).setModIconIndex(1, 2));

    public static EmpoisonedPotion EmpoisonedPotion = ((EmpoisonedPotion)((EmpoisonedPotion)(new EmpoisonedPotion(true, 3484199).setPotionName("effect.EmpoisonedPotion").setRegistryName(Legends.modId, "EmpoisonedPotion").setBeneficial())).setModIconIndex(1, 2));
    public static CowboyPotion CowboyPotion = ((CowboyPotion)((CowboyPotion)(new CowboyPotion(true, 3484199).setPotionName("effect.CowboyPotion").setRegistryName(Legends.modId, "CowboyPotion").setBeneficial())).setModIconIndex(1, 2));

    public static CrushedWoundPotion CrushedWoundPotion = ((CrushedWoundPotion)((CrushedWoundPotion)(new CrushedWoundPotion(true, 3484199).setPotionName("effect.CrushedWoundPotion").setRegistryName(Legends.modId, "CrushedWoundPotion").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15D, 2).setBeneficial())).setModIconIndex(1, 2));
    public static AmbalPotion AmbalPotion = ((AmbalPotion)((AmbalPotion)(new AmbalPotion(true, 3484199).setPotionName("effect.AmbalPotion").setRegistryName(Legends.modId, "AmbalPotion").registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.5D, 2).setBeneficial())).setModIconIndex(1, 2));
    public static StunningPotion StunningPotion = ((StunningPotion)((StunningPotion)(new StunningPotion(true, 3484199).setPotionName("effect.StunningPotion").setRegistryName(Legends.modId, "StunningPotion").setBeneficial())).setModIconIndex(1, 2));
    public static RepulsedPotion RepulsedPotion = ((RepulsedPotion)((RepulsedPotion)(new RepulsedPotion(true, 3484199).setPotionName("effect.RepulsedPotion").setRegistryName(Legends.modId, "RepulsedPotion").setBeneficial())).setModIconIndex(1, 2));
    public static MonkPotion MonkPotion = ((MonkPotion)((MonkPotion)(new MonkPotion(true, 3484199).setPotionName("effect.AmbalPotion").setRegistryName(Legends.modId, "AmbalPotion").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2).setBeneficial())).setModIconIndex(1, 2));

    public static FirePotion FirePotion = ((FirePotion)((FirePotion)(new FirePotion(true, 3484199).setPotionName("effect.FirePotion").setRegistryName(Legends.modId, "FirePotion").setBeneficial())).setModIconIndex(1, 2));
    public static FireFlowPotion FireFlowPotion = ((FireFlowPotion)((FireFlowPotion)(new FireFlowPotion(true, 3484199).setPotionName("effect.FireFlowPotion").setRegistryName(Legends.modId, "FireFlowPotion").setBeneficial())).setModIconIndex(1, 2));
    public static FireFlowResultPotion FireFlowResultPotion = ((FireFlowResultPotion)((FireFlowResultPotion)(new FireFlowResultPotion(true, 3484199).setPotionName("effect.FireFlowResultPotion").setRegistryName(Legends.modId, "FireFlowResultPotion").setBeneficial())).setModIconIndex(1, 2));
    public static FireFlow2Potion FireFlow2Potion = ((FireFlow2Potion)((FireFlow2Potion)(new FireFlow2Potion(true, 3484199).setPotionName("effect.FireFlow2Potion").setRegistryName(Legends.modId, "FireFlow2Potion").setBeneficial())).setModIconIndex(1, 2));
    public static FireRingPotion FireRingPotion = ((FireRingPotion)((FireRingPotion)(new FireRingPotion(true, 3484199).setPotionName("effect.FireRingPotion").setRegistryName(Legends.modId, "FireRingPotion").setBeneficial())).setModIconIndex(1, 2));
    public static FireWavePotion FireWavePotion = ((FireWavePotion)((FireWavePotion)(new FireWavePotion(true, 3484199).setPotionName("effect.FireWavePotion").setRegistryName(Legends.modId, "FireWavePotion").setBeneficial())).setModIconIndex(1, 2));

    public static IceFlowPotion IceFlowPotion = ((IceFlowPotion)((IceFlowPotion)(new IceFlowPotion(true, 3484199).setPotionName("effect.IceFlowPotion").setRegistryName(Legends.modId, "IceFlowPotion").setBeneficial())).setModIconIndex(1, 2));
    public static IceFlow2Potion IceFlow2Potion = ((IceFlow2Potion)((IceFlow2Potion)(new IceFlow2Potion(true, 3484199).setPotionName("effect.IceFlow2Potion").setRegistryName(Legends.modId, "IceFlow2Potion").setBeneficial())).setModIconIndex(1, 2));
    public static IceResultPotion IceResultPotion = ((IceResultPotion)((IceResultPotion)(new IceResultPotion(true, 3484199).setPotionName("effect.IceResultPotion").setRegistryName(Legends.modId, "IceResultPotion").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", -0.10000000298023224D, 2).setBeneficial())).setModIconIndex(1, 2));
    public static IceStunResultPotion IceStunResultPotion = ((IceStunResultPotion)((IceStunResultPotion)(new IceStunResultPotion(true, 3484199).setPotionName("effect.IceStunResultPotion").setRegistryName(Legends.modId, "IceStunResultPotion").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", -0.9D, 2).setBeneficial())).setModIconIndex(1, 2));
    public static IceGaitPotion IceGaitPotion = ((IceGaitPotion)((IceGaitPotion)(new IceGaitPotion(true, 3484199).setPotionName("effect.IceGaitPotion").setRegistryName(Legends.modId, "IceGaitPotion").setBeneficial())).setModIconIndex(1, 2));

    public static LightningResultPotion LightningResultPotion = ((LightningResultPotion)((LightningResultPotion)(new LightningResultPotion(true, 3484199).setPotionName("effect.LightningResultPotion").setRegistryName(Legends.modId, "LightningResultPotion").setBeneficial())).setModIconIndex(1, 2));
    public static LightningFlowPotion LightningFlowPotion = ((LightningFlowPotion)((LightningFlowPotion)(new LightningFlowPotion(true, 3484199).setPotionName("effect.LightningFlowPotion").setRegistryName(Legends.modId, "LightningFlowPotion").setBeneficial())).setModIconIndex(1, 2));
    public static LightingFlow2Potion LightingFlow2Potion = ((LightingFlow2Potion)((LightingFlow2Potion)(new LightingFlow2Potion(true, 3484199).setPotionName("effect.LightingFlow2Potion").setRegistryName(Legends.modId, "LightingFlow2Potion").setBeneficial())).setModIconIndex(1, 2));
    public static LightningSpeedPotion LightningSpeedPotion = ((LightningSpeedPotion)((LightningSpeedPotion)(new LightningSpeedPotion(true, 3484199).setPotionName("effect.LightningSpeedPotion").setRegistryName(Legends.modId, "LightningSpeedPotion").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", +0.30000000298023224D, 2).setBeneficial())).setModIconIndex(1, 2));

    public static void registerPotions(){
        ForgeRegistries.POTIONS.register(StabbingWoundPotion);
        ForgeRegistries.POTIONS.register(IncisionPotion);
        ForgeRegistries.POTIONS.register(HackedPotion);
        ForgeRegistries.POTIONS.register(EmpoisonedPotion);
        ForgeRegistries.POTIONS.register(CowboyPotion);
        ForgeRegistries.POTIONS.register(CrushedWoundPotion);
        ForgeRegistries.POTIONS.register(AmbalPotion);
        ForgeRegistries.POTIONS.register(StunningPotion);
        ForgeRegistries.POTIONS.register(RepulsedPotion);
        ForgeRegistries.POTIONS.register(FirePotion);
        ForgeRegistries.POTIONS.register(FireFlowPotion);
        ForgeRegistries.POTIONS.register(FireFlowResultPotion);
        ForgeRegistries.POTIONS.register(FireFlow2Potion);
        ForgeRegistries.POTIONS.register(FireRingPotion);
        ForgeRegistries.POTIONS.register(FireWavePotion);
        ForgeRegistries.POTIONS.register(IceResultPotion);
        ForgeRegistries.POTIONS.register(IceStunResultPotion);
        ForgeRegistries.POTIONS.register(IceGaitPotion);
        ForgeRegistries.POTIONS.register(LightningResultPotion);
        ForgeRegistries.POTIONS.register(IceFlowPotion);
        ForgeRegistries.POTIONS.register(IceFlow2Potion);
    }
}
