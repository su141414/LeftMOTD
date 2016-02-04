package me.su1414.leftmotd.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class CUBukkit {

	public static List<String> color(List<String> strings) {
		List<String> toReturn = new ArrayList<>();
		for (String s : strings) {
			toReturn.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return toReturn;
	}

	public static String strip(String s) {
		return ChatColor.stripColor(s);
	}

}
