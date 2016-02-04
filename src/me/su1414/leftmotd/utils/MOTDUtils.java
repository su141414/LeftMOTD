package me.su1414.leftmotd.utils;

import java.util.List;
import java.util.Random;

public class MOTDUtils {

	private static List<String> motds;
	private static final Random RAND = new Random();
	
	public static String getMOTDToSend(boolean customSlots, String versionName, int players, int maxPlayers) {
		String MOTD = motds.get(RAND.nextInt(motds.size()));
		String s = "";
		s += MOTD;
		if (customSlots) {
			for (int i = 0; i < 95 - ColorUtils.strip(versionName).length(); i++) {
				s += " ";
			}

			s += versionName;
		} else {
			String slots = "§7" + players + "§8/§7" + maxPlayers;
			for (int i = 0; i < 95 - ColorUtils.strip(slots).length(); i++) {
				s += " ";
			}
			s += slots;
		}
		return s;
	}
	
	public static void setMotds(List<String> motds) {
		MOTDUtils.motds = motds;
	}

}
