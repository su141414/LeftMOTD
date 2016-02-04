package me.su1414.leftmotd.utils;

import java.util.List;

public class ColorUtils {

	private static boolean bungee = false;

	public static List<String> color(List<String> strings) {
		if (bungee)
			return CUBungee.color(strings);
		return CUBukkit.color(strings);
	}
	
	public static String strip(String s) {
		if (bungee)
			return CUBungee.strip(s);
		return CUBukkit.strip(s);
	}

	public static void setBungee() {
		bungee = true;
	}
}
