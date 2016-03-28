package me.su1414.leftmotd.bungee;

import me.su1414.leftmotd.utils.MOTDUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BCommands extends Command {

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
			sender.sendMessage("§a#/LeftMOTD list - §2Shows list of LeftMOTDs");
			sender.sendMessage("§a#/LeftMOTD add - §2Adds MOTD to config. §7(§8Not working yet!§7)");
			sender.sendMessage("§a#/LeftMOTD remove - §2Removes MOTD from config. §7(§8Not working yet!§7)");
			sender.sendMessage("§a#§e-----§6COMMANDS§e-----");
			return;
		}
		if (!sender.hasPermission("leftmotd.*")) {
			sender.sendMessage("§4You don't have permission.");
			return;
		}
		if (args[0].equalsIgnoreCase("reload")) {
			BLeftMOTD.getInst().reloadCfg();
			sender.sendMessage("§aReload complete.");
		} else if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage("§a#§e-----§6LeftMOTDs§e-----");
			for (int i = 0; i < MOTDUtils.getMotds().size(); i++) {
				sender.sendMessage("§7" + (i + 1) + ". " + MOTDUtils.getMotds().get(i));
			}
			sender.sendMessage("§a#§e-----§6LeftMOTDs§e-----");
		} else if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 2) {
				sender.sendMessage("§4Correct usage: §c/LeftMOTD add <MOTD with spaces>");
				return;
			}
			String motd = "";
			for (int i = 1; i < args.length; i++) {
				motd += args[i];
				motd += " ";
			}
			BLeftMOTD.getInst().addMOTD(motd);
			sender.sendMessage("§aAdded motd - " + motd);
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args.length < 2) {
				sender.sendMessage("§4Correct usage: §c/LeftMOTD remove <number>");
				return;
			}
			int i = 0;
			try {
				i = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage("§4" + args[1] + " is not a number!");
				return;
			}
			BLeftMOTD.getInst().removeMOTD(i - 1);
			sender.sendMessage("§aRemoved motd - " + i);
		}
	}

}
