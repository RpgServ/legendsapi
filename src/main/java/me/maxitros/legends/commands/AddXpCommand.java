package me.maxitros.legends.commands;

import me.maxitros.legends.capabilities.ExpStats;
import me.maxitros.legends.capabilities.IExpStats;
import me.maxitros.legends.capabilities.ISkillsData;
import me.maxitros.legends.capabilities.providers.ExpStatsProvider;
import me.maxitros.legends.capabilities.providers.SkillsDataProvider;
import me.maxitros.legends.registry.CapaListener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class AddXpCommand extends CommandBase {
    @Override
    public String getName() {
        return "addxplvl";
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "addxplvl <player> <xp>";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if(iCommandSender.canUseCommand(2, "") && strings.length>=2)
        {
            EntityPlayerMP player = minecraftServer.getPlayerList().getPlayerByUsername(strings[1]);
            if(player!=null)
            {
                try {
                    IExpStats data = player.getCapability(ExpStatsProvider.EXP_STATS_CAPABILITY_CAP, null);
                    data.AddXP(Float.parseFloat(strings[1]));
                    CapaListener.SyncCapa(CapaListener.CapaType.ExpStats, player);
                }catch (Exception e)
                {
                    throw new CommandException(e.getMessage());
                }
            }
        }
    }
}
