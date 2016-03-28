package me.su1414.leftmotd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.su1414.leftmotd.utils.MOTDUtils;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§7LeftMOTD - §8MOTD on the left side of server.");
			sender.sendMessage("§7Plugin created by su1414.");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			sender.sendMessage("§a#/LeftMOTD reload - §2Reloads the config file.");
			sender.sendMessage("§a#/LeftMOTD list - §2Shows list of LeftMOTDs");
			sender.sendMessage("§a#/LeftMOTD add - §2Adds MOTD to config.");
			sender.sendMessage("§a#/LeftMOTD remove - §2Removes MOTD from config.)");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			return false;
		}
		if (!sender.hasPermission("leftmotd.*")) {
			sender.sendMessage("§4You don't have permission.");
			return false;
		}
		if (args[0].equalsIgnoreCase("reload")) {
			LeftMOTD.getInst().reloadCfg();
			sender.sendMessage("§aReload complete.");
		} else if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage("§a#§e-----§6LeftMOTDs§e-----");
			for(int i = 0; i < MOTDUtils.getMotds().size(); i++) {
				sender.sendMessage("§7" + (i + 1) + ". " + MOTDUtils.getMotds().get(i));
			}
			sender.sendMessage("§a#§e-----§6LeftMOTDs§e-----");
		} else if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 2) {
				sender.sendMessage("§4Correct usage: §c/LeftMOTD add <MOTD with spaces>");
				return false;
			}
			String motd = "";
			for (int i = 1; i < args.length; i++) {
				motd += args[i];
				motd += " ";
			}
			LeftMOTD.getInst().addMOTD(motd);
			sender.sendMessage("§aAdded motd - " + motd);
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args.length < 2) {
				sender.sendMessage("§4Correct usage: §c/LeftMOTD remove <number>");
				return false;
			}
			int i = 0;
			try {
				i = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage("§4" + args[1] + " is not a number!");
				return false;
			}
			LeftMOTD.getInst().removeMOTD(i-1);
			sender.sendMessage("§aRemoved motd - " + i);
		}

		return false;
	}
}
