package me.maxitros.legends.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class SetLvlCmd extends CommandBase {
    @Override
    public String getName() {
        return "setlvl";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "setlvl <lvl>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

    }
}
