package me.maxitros.legends;

import me.maxitros.legends.api.SkillsEnum;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientData {
    public static float ClientMana = 0;
    public static float ClientMaxMana = 0;
    public static float ClientCurrentExp = 0;
    public static float ClientNextLvlExp = 0;
    public static int ClientCurrentLvl = 0;
    public static byte[] Skills = new byte[SkillsEnum.Count];
    public static SkillsEnum CurrentSkill;
    public static float CurrentDamage = 0;
    public static float AdditionalDamage = 0;
    public static byte[] SkillsCooldowns = new byte[SkillsEnum.Count];
}
