package me.su1414.leftmotd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§7LeftMOTD - §8MOTD on the left side of server.");
			sender.sendMessage("§7Plugin created by su1414.");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			sender.sendMessage("§a#/LeftMOTD reload - §2Reloads the config file.");
			sender.sendMessage("§a#/LeftMOTD add - §2Adds MOTD to config. §7(§8Not working yet!§7)");
			sender.sendMessage("§a#/LeftMOTD remove - §2Removes MOTD from config. §7(§8Not working yet!§7)");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			return false;
		}
		if (!sender.hasPermission("leftmotd.*")) {
			sender.sendMessage("§4You don't have permission.");
			return false;
		}
		if(args[0].equalsIgnoreCase("reload")) {
			LeftMOTD.getInst().reloadCfg();
			sender.sendMessage("§aReload complete.");
		}
		
		return false;
	}
}
