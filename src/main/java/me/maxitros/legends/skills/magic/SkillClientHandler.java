package me.maxitros.legends.skills.magic;

import me.maxitros.legends.ClientData;
import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.SkillCooldown;
import me.maxitros.legends.networking.PacketHandler;
import me.maxitros.legends.networking.ProceedActiveSpellPacket;
import me.maxitros.legends.networking.SyncSkillCooldown;
import me.maxitros.legends.skills.keys.KeybindsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class SkillClientHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event){
        if(KeybindsRegister.USEMAGIC_KEY.isKeyDown())
        {
            if(ClientData.CurrentSkill!=null
                    && ClientData.CurrentSkill!= SkillsEnum.EMPTY)
            {
                PacketHandler.INSTANCE.sendToServer(new ProceedActiveSpellPacket(ClientData.CurrentSkill));
            }
        }
        if(KeybindsRegister.PREVSKILL_KEY.isKeyDown())
        {
            ArrayList<SkillsEnum> skillsEnums = new ArrayList<>();
            SkillsEnum[] allSkills = SkillsEnum.values();
            for(int i = 0; i < ClientData.Skills.length; i++)
            {
                if(ClientData.Skills[i]>0&&SkillsEnum.ActiveSkills.contains(allSkills[i]))
                {
                    skillsEnums.add(allSkills[i]);
                }
            }
            if(skillsEnums.size()>0)
            {
                int i = 0;
                if(ClientData.CurrentSkill != null)
                {
                    i = skillsEnums.indexOf(ClientData.CurrentSkill);
                }
                i--;
                if(i==-1)
                {
                    i = skillsEnums.size()-1;
                }
                if(i>=0) {
                    ClientData.CurrentSkill = skillsEnums.get(i);
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString("ВЫБРАН СКИЛЛ "+ClientData.CurrentSkill.name()));
                }

            }
        }
        if(KeybindsRegister.NEXTSKILL_KEY.isKeyDown())
        {
            ArrayList<SkillsEnum> skillsEnums = new ArrayList<>();
            SkillsEnum[] allSkills = SkillsEnum.values();
            for(int i = 0; i< ClientData.Skills.length; i++)
            {

                if(ClientData.Skills[i]>0&&SkillsEnum.ActiveSkills.contains(allSkills[i]))
                {
                    skillsEnums.add(allSkills[i]);
                }
            }


            if(skillsEnums.size()>0)
            {
                int i = 0;

                if(ClientData.CurrentSkill != null)
                {
                    i = skillsEnums.indexOf(ClientData.CurrentSkill);
                }
                i++;
                if(i==skillsEnums.size())
                {
                    i = 0;
                }
                if(i>=0) {
                    ClientData.CurrentSkill = skillsEnums.get(i);
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString("ВЫБРАН СКИЛЛ " + ClientData.CurrentSkill.name()));
                }
            }

        }
    }
}
