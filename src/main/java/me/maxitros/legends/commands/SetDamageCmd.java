package me.maxitros.legends.commands;

import me.maxitros.legends.capabilities.IMagicStats;
import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.providers.MagicStatsProvider;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import me.maxitros.legends.registry.CapaListener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class SetDamageCmd extends CommandBase {
    @Override
    public String getName() {
        return "setdamage";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "setdamage <damage>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof EntityPlayerMP))
            return;
        if(args.length<1) {
            throw new CommandException(this.getUsage(sender));
        }
        EntityPlayerMP playerMP = (EntityPlayerMP)sender;
        try {
            ISkillsData data = playerMP.getCapability(SkillsDataProvider.SKILLS_CAP, null);
            data.SetBaseDamage(Float.parseFloat(args[0]));
            CapaListener.SyncCapa(CapaListener.CapaType.SkillData, playerMP);
        }catch (Exception e)
        {
            throw new CommandException(e.getMessage());
        }
    }
}
