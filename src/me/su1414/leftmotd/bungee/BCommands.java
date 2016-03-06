package me.su1414.leftmotd.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BCommands extends Command{

	public BCommands() {
		super("LeftMOTD", "leftmotd.*", "lmotd", "lm");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§7LeftMOTD - §8MOTD on the left side of server.");
			sender.sendMessage("§7Plugin created by su1414.");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			sender.sendMessage("§a#/LeftMOTD reload - §2Reloads the config file.");
			sender.sendMessage("§a#/LeftMOTD add - §2Adds MOTD to config. §7(§8Not working yet!§7)");
			sender.sendMessage("§a#/LeftMOTD remove - §2Removes MOTD from config. §7(§8Not working yet!§7)");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			return;
		}
		if (!sender.hasPermission("leftmotd.*")) {
			sender.sendMessage("§4You don't have permission.");
			return;
		}
		if(args[0].equalsIgnoreCase("reload")) {
			BLeftMOTD.getInst().reloadCfg();
			sender.sendMessage("§aReload complete.");
		}
	}

}
