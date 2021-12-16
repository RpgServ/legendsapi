package me.maxitros.legends.commands;

import me.maxitros.legends.api.SkillsEnum;
import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.SkillsData;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import me.maxitros.legends.registry.CapaListener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class SetSkillCmd extends CommandBase {
    @Override
    public String getName() {
        return "setskilllvl";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "setSkilllvl <SkillName> <Lvl>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof EntityPlayerMP))
            return;
        if(args.length<2) {
            throw new CommandException(this.getUsage(sender));
        }
        EntityPlayerMP playerMP = (EntityPlayerMP)sender;
        try {
            ISkillsData data = playerMP.getCapability(SkillsDataProvider.SKILLS_CAP, null);
            data.SetSkillData(SkillsData.UpdateSkill(SkillsEnum.valueOf(args[0]), Byte.parseByte(args[1]), data.GetSkillData()));
            CapaListener.SyncCapa(CapaListener.CapaType.SkillData, playerMP);
        }catch (Exception e)
        {
            throw new CommandException(e.getMessage());
        }
    }
}
