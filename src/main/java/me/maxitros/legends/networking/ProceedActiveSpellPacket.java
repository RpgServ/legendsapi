package me.maxitros.legends.networking;

import io.netty.buffer.ByteBuf;
import jdk.nashorn.internal.ir.Block;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.*;
import me.maxitros.legends.capabilities.providers.SkillCooldownProvider;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import me.maxitros.legends.effects.AdvancedModPotionEffect;
import me.maxitros.legends.effects.ModPotionEffect;
import me.maxitros.legends.effects.PotionRegistry;
import me.maxitros.legends.entities.magic.*;
import me.maxitros.legends.registry.CapaListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Arrays;

public class ProceedActiveSpellPacket implements IMessage {

    SkillsEnum skill;
    public ProceedActiveSpellPacket(){}
    public ProceedActiveSpellPacket(SkillsEnum skill){
        this.skill = skill;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        skill = SkillsEnum.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(skill.ordinal());
    }
    public static class Handler implements IMessageHandler<ProceedActiveSpellPacket, IMessage> {
        @Override
        public IMessage onMessage(ProceedActiveSpellPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(ProceedActiveSpellPacket message, MessageContext ctx) {
            if(SkillsEnum.ActiveSkills.contains(message.skill)){
                EntityPlayerMP player = ctx.getServerHandler().player;
                ISkillsData skillsData = player.getCapability(SkillsDataProvider.SKILLS_CAP, null);
                ISkillCooldown cooldown  = player.getCapability(SkillCooldownProvider.SKILLSCOOLDOWN_CAP, null);

                if(SkillsData.HasSkill(message.skill, skillsData.GetSkillData())
                        && !SkillCooldown.HasCooldown(message.skill, cooldown.GetCooldownData())){
                    if(player.getActivePotionEffects().stream().filter(x->x.getPotion().equals(PotionRegistry.StunningPotion)).findAny().isPresent())
                        return;
                    byte lvl = SkillsData.GetSkillLvl(message.skill, skillsData.GetSkillData());
                    switch (message.skill)
                    {

                        //region FireSkills
                        case Skill_Mag_Fire_Arrow:{
                            procced_Skill_Mag_Fire_Arrow(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)6, (byte)0, lvl, player);
                        }
                        break;
                            case Skill_Mag_Fire_Ball:{
                            procced_Skill_Mag_Fire_Ball(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Fire_Flow:{
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.FireFlowPotion, 20*5, lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Fire_Flow2:{
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.FireFlow2Potion, 20*10, lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)20, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Fire_Rain:{
                            player.world.spawnEntity(new EntityFallingCubes(player.world, player.posX, player.posY, player.posZ, player.getCachedUniqueIdString(), lvl,skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)40, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Fire_Ring:{

                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.FireRingPotion, 20*10, lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)40, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Fire_Wave:{

                            if(player.isPotionActive(PotionRegistry.FireWavePotion))
                                player.removeActivePotionEffect(PotionRegistry.FireWavePotion);
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.FireWavePotion, 20*10, lvl, skillsData.GetFullDamage()));
                        }
                        break;
                        //endregion
                        //region IceSkills
                        case Skill_Mag_Ice_Arrow:{
                            procced_Skill_Mag_Ice_Arrow(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)6, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Ice_Ball	:{
                            procced_Skill_Mag_Ice_Ball(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Ice_Blizzard	:{
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.IceFlowPotion, 20*(5+lvl-1), lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Ice_Blizzard2		:{
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.IceFlow2Potion, 20*(10+lvl-1), lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)20, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Ice_Screen:{
                            int flag = getFlagByYaw(player.rotationYaw);
                            BlockPos pos = player.getPosition();
                            Vec3d[][] blocks = new Vec3d[4][5];
                            switch (flag){
                                case 0:
                                    for (int i = -2;i<=2;i++)
                                        blocks[0][i+2] = new Vec3d(pos.getX()+i, pos.getY(), pos.getZ()+3);
                                    break;
                                case 1:
                                    for (int i = 0;i<=4;i++)
                                        blocks[0][i] = new Vec3d(pos.getX()-i, pos.getY(), pos.getZ()+4-i);
                                    break;
                                case 2:
                                    for (int i = -2;i<=2;i++)
                                        blocks[0][i+2] = new Vec3d(pos.getX()-3, pos.getY(), pos.getZ()+i);
                                    break;
                                case 3:
                                    for (int i = 0;i<=4;i++)
                                        blocks[0][i] = new Vec3d(pos.getX()-4+i, pos.getY(), pos.getZ()-i);
                                    break;
                                case 4:
                                    for (int i = -2;i<=2;i++)
                                        blocks[0][i+2] = new Vec3d(pos.getX()+i, pos.getY(), pos.getZ()-3);
                                    break;
                                case 5:
                                    for (int i = 0;i<=4;i++)
                                        blocks[0][i] = new Vec3d(pos.getX()+i, pos.getY(), pos.getZ()-4+i);
                                    break;
                                case 6:
                                    for (int i = -2;i<=2;i++)
                                        blocks[0][i+2] = new Vec3d(pos.getX()+3, pos.getY(), pos.getZ()+i);
                                    break;
                                case 7:
                                    for (int i = 0;i<=4;i++)
                                        blocks[0][i] = new Vec3d(pos.getX()+4-i, pos.getY(), pos.getZ()+i);
                                    break;
                            }

                            for (int i = 0;i<4;i++){
                                for (int n = 0; n<5;n++)
                                {
                                    EntityIceCube cube = new EntityIceCube(
                                            player.world,
                                            blocks[i][n].x,
                                            blocks[i][n].y,
                                            blocks[i][n].z,
                                            Blocks.SNOW.getDefaultState(),
                                            20*(3+lvl*2),
                                            player.getCachedUniqueIdString(),
                                            lvl,
                                            skillsData.GetFullDamage());
                                    player.world.spawnEntity(cube);
                                    PacketHandler.INSTANCE.sendToAllAround(new SyncIceCube(cube.getCachedUniqueIdString(), 20*(3+lvl*2)), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 50));
                                    if(i<=2)
                                        blocks[i+1][n] = blocks[i][n].addVector(0,1,0);
                                }
                            }
                            proceedCooldown(message.skill, cooldown, (byte)6, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Ice_Cocoon:{
                            BlockPos pos = player.getPosition();
                            for(int y = 0;y<=4;y++)
                            {
                                for (int x = -2;x<= 2;x++)
                                {
                                    for (int z = -2;z<= 2;z++)
                                    {
                                        if(ContainCocon(x,z) || y == 4)
                                        {
                                            EntityIceCube cube = new EntityIceCube(
                                                    player.world,
                                                    x + pos.getX(),
                                                    y + pos.getY(),
                                                    z + pos.getZ(),
                                                    Blocks.SNOW.getDefaultState(),
                                                    20*(3+lvl*2),
                                                    player.getCachedUniqueIdString(),
                                                    lvl,
                                                    skillsData.GetFullDamage());
                                            player.world.spawnEntity(cube);
                                            PacketHandler.INSTANCE.sendToAllTracking(new SyncIceCube(cube.getCachedUniqueIdString(), 20*(3+lvl*2)), cube);
                                        }
                                    }
                                }
                            }
                            proceedCooldown(message.skill, cooldown, (byte)20, (byte)0, lvl, player);

                        }
                        break;
                        case Skill_Mag_Ice_Mine	:{
                            EntityMine mine = new EntityMine(player.world, player.posX, player.posY, player.posZ, player.getCachedUniqueIdString(), lvl, skillsData.GetFullDamage(),(60+(lvl-1)*10)*20);
                            player.world.spawnEntity(mine);
                            PacketHandler.INSTANCE.sendToAllTracking(new SyncMine(player.getCachedUniqueIdString(),(60+(lvl-1)*10)*20), mine);
                            proceedCooldown(message.skill, cooldown, (byte)40, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Ice_Gait:{
                            player.addPotionEffect(new ModPotionEffect(PotionRegistry.IceGaitPotion, 20*(15+lvl-1), lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)16, (byte)0, lvl, player);
                        }
                        break;
                        //endregion
                        case Skill_Mag_Lighning_Arrow:{
                            procced_Skill_Mag_Lighning_Arrow(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)6, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Lighning_Ball:{
                            procced_Skill_Mag_Lighning_Ball(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Lighning_Flow:{
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.LightningFlowPotion, 20*(5+lvl-1), lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Lighning_Flow2:{
                            player.addPotionEffect(new AdvancedModPotionEffect(PotionRegistry.LightingFlow2Potion, 20*(10+lvl-1), lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)20, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Lighning_Speed:{
                            player.addPotionEffect(new ModPotionEffect(PotionRegistry.LightningSpeedPotion, 20*(15+lvl-1), lvl, skillsData.GetFullDamage()));
                            proceedCooldown(message.skill, cooldown, (byte)16, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Lighning_Carryover	:{
                            Vec3d eyes = player.getLookVec().normalize();
                            Vec3d pos = player.getPositionVector().add(new Vec3d(0,player.eyeHeight,0));
                            Vec3d target = pos.add(eyes.scale(30));
                            RayTraceResult raytrace =  player.world.rayTraceBlocks(pos, target);
                            if(raytrace!=null&&raytrace.typeOfHit== RayTraceResult.Type.BLOCK)
                            {
                                Vec3d hit = raytrace.hitVec;
                                player.attemptTeleport(hit.x, hit.y+1, hit.z);
                            }
                            else
                            {
                                player.attemptTeleport(target.x, target.y, target.z);
                            }
                            proceedCooldown(message.skill, cooldown, (byte)20, (byte)0, lvl, player);
                        }
                        break;
                        case Skill_Mag_Lighning_Shot:{
                            procced_Skill_Mag_Lighning_Shot(player, lvl ,skillsData.GetFullDamage());
                            proceedCooldown(message.skill, cooldown, (byte)10, (byte)0, lvl, player);
                        }
                        break;
                    }
                }else
                {
                    player.sendMessage(new TextComponentString("СКИЛЛ "+message.skill.name()+ " В КД"));
                }
                skillsData.SetActiveSkill(message.skill);
                CapaListener.SyncCapa(CapaListener.CapaType.SkillData, player);
            }


        }

        //region Balls
        private void procced_Skill_Mag_Fire_Ball(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicGrenade arrow = new EntityMagicGrenade(player.world, player, SkillLvl, (1.5f + 0.1f * (SkillLvl-1)) * damage,
                    null,
                    1, EnumParticleTypes.FLAME, EnumParticleTypes.FLAME,
                    true, EntityMagicGrenade.GrenadeType.Fireball);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  0.75F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientGrenadePacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }
        private void procced_Skill_Mag_Lighning_Ball(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicGrenade arrow = new EntityMagicGrenade(player.world, player, SkillLvl, (1.5f + 0.1f * (SkillLvl-1)) * damage,
                    new ModPotionEffect(PotionRegistry.LightningResultPotion, 20 * (3 + (SkillLvl-1))),
                    1, EnumParticleTypes.END_ROD, EnumParticleTypes.END_ROD,
                    true, EntityMagicGrenade.GrenadeType.LightingBall);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  0.75F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientGrenadePacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }
        private void procced_Skill_Mag_Ice_Ball(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicGrenade arrow = new EntityMagicGrenade(player.world, player, SkillLvl, (1.5f + 0.1f * (SkillLvl-1)) * damage,
                    new ModPotionEffect(PotionRegistry.IceResultPotion, 20 * (3 + (SkillLvl-1)),damage),
                    1, EnumParticleTypes.SNOWBALL, EnumParticleTypes.SNOWBALL,
                    true, EntityMagicGrenade.GrenadeType.Fireball);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  0.75F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientGrenadePacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }
        //endregion

        private void proceedCooldown(SkillsEnum skill, ISkillCooldown cooldown, byte baseTime, byte addTime, byte lvl, EntityPlayerMP playerMP) {
            cooldown.SetCooldownData(SkillCooldown.UpdateCooldown(skill, (byte) (baseTime - addTime*(lvl-1)), cooldown.GetCooldownData()), playerMP);
        }

        private void procced_Skill_Mag_Fire_Arrow(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicArrow arrow = new EntityMagicArrow(player.world, player, SkillLvl, (0.5f + 0.1f * (SkillLvl-1)) * damage,
                    new ModPotionEffect(PotionRegistry.FirePotion, 20 * (3 + (SkillLvl-1)), SkillLvl, damage),
                    0, EnumParticleTypes.FLAME, EnumParticleTypes.FLAME,
                    false, EntityMagicArrow.ArrowType.FireArrow);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  3.0F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientArrowPacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }
        private void procced_Skill_Mag_Ice_Arrow(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicArrow arrow = new EntityMagicArrow(player.world, player, SkillLvl, (0.5f + 0.1f * (SkillLvl-1)) * damage,
                    new ModPotionEffect(PotionRegistry.IceResultPotion, 20 * (3 + (SkillLvl-1)), SkillLvl, damage),
                    0, EnumParticleTypes.SNOWBALL, EnumParticleTypes.SNOWBALL,
                    false, EntityMagicArrow.ArrowType.IceArrow);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  3.0F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientArrowPacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }
        private void procced_Skill_Mag_Lighning_Arrow(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicArrow arrow = new EntityMagicArrow(player.world, player, SkillLvl, (0.5f + 0.1f * (SkillLvl-1)) * damage,
                    new ModPotionEffect(PotionRegistry.LightningResultPotion, 20 * (3 + (SkillLvl-1)), SkillLvl, damage),
                    0, EnumParticleTypes.END_ROD, EnumParticleTypes.END_ROD,
                    false, EntityMagicArrow.ArrowType.LightningArrow);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  3.0F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientArrowPacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }
        private void procced_Skill_Mag_Lighning_Shot(EntityPlayerMP player, byte SkillLvl, float damage) {
            EntityMagicArrow arrow = new EntityMagicArrow(player.world, player, SkillLvl, (2f + 0.1f * (SkillLvl-1)) * damage,
                    new ModPotionEffect(PotionRegistry.LightningResultPotion, 20 * (3 + (SkillLvl-1)), SkillLvl, damage),
                    0, EnumParticleTypes.ENCHANTMENT_TABLE, EnumParticleTypes.ENCHANTMENT_TABLE,
                    false, EntityMagicArrow.ArrowType.LightningArrow2);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F,  3.0F, 1.0F);
            player.world.spawnEntity(arrow);
            PacketHandler.INSTANCE.sendTo(new SpawnClientArrowPacket(arrow.getCachedUniqueIdString(), arrow.getEffectRange(), arrow.getParticleFlightType(), arrow.getParticleDamageType(), arrow.isHasRange(), arrow.getGrenadeType()), player);
        }

        private int getFlagByYaw(float yaw)
        {
            if(yaw>=337.5||yaw<22.5)
            {
                //z poz
                return  0;
            }
            if(yaw>=22.5&&yaw<67.5)
            {
                //z poz x neg
                return  1;
            }
            if(yaw>=67.5&&yaw<112.5)
            {
                //x neg
                return  2;
            }
            if(yaw>=112.5&&yaw<157.5)
            {
                //z neg x neg
                return  3;
            }
            if(yaw>=157.5&&yaw<202.5)
            {
                //z neg
                return  4;
            }

            if(yaw>=202.5&&yaw<247.5)
            {
                //z neg x poz
                return  5;
            }
            if(yaw>=247.5&&yaw<292.5)
            {
                //z neg x poz
                return  6;
            }
            return 7;
        }
        private boolean ContainCocon(int x, int z)
        {
            return Cocon.contains(new Vec3d(x,0,z));

        }
        private static final ArrayList<Vec3d> Cocon = new ArrayList<>(Arrays.asList(

                new Vec3d(1,0,2),
                new Vec3d(0,0,2),
                new Vec3d(-1,0,2),

                new Vec3d(2,0,2),
                new Vec3d(2,0,1),
                new Vec3d(2,0,0),
                new Vec3d(2,0,-1),

                new Vec3d(2,0,-2),
                new Vec3d(1,0,-2),
                new Vec3d(0,0,-2),
                new Vec3d(-1,0,-2),

                new Vec3d(-2,0,2),
                new Vec3d(-2,0,1),
                new Vec3d(-2,0,0),
                new Vec3d(-2,0,-1),
                new Vec3d(-2,0,-2)
        ));
    }
}
