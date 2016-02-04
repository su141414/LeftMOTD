package me.su1414.leftmotd;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsBukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.su1414.leftmotd.utils.ColorUtils;
import me.su1414.leftmotd.utils.MOTDUtils;

public class LeftMOTD extends JavaPlugin{
	
	private ProtocolManager protocolManager;

	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	public void onEnable() {
		boolean m = true;
		getLogger().log(Level.INFO, "Connecting to Metrics...");
		try {
			MetricsBukkit mb = new MetricsBukkit(this);
			mb.start();
		} catch (IOException e1) {
			getLogger().log(Level.WARNING, "Can't connect to Metrics");
			m = false;
		}

		if (m)
			getLogger().log(Level.INFO, "Connected to Metrics");
		saveDefaultConfig();
		MOTDUtils.setMotds(ColorUtils.color(getConfig().getStringList("leftMOTDs")));
		protocolManager.addPacketListener(new Listeners(this).getPacketAdapter());
	}
	
}
