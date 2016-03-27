package me.su1414.leftmotd;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsBukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.su1414.leftmotd.utils.ColorUtils;
import me.su1414.leftmotd.utils.MOTDUtils;

public class LeftMOTD extends JavaPlugin {

	private ProtocolManager protocolManager;
	private static LeftMOTD inst;
	private YamlConfiguration config;

	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

	public void onEnable() {
		inst = this;
		getCommand("LeftMOTD").setExecutor(new Commands());
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
		reloadCfg();
		protocolManager.addPacketListener(new Listeners(this).getPacketAdapter());
	}

	public static LeftMOTD getInst() {
		return inst;
	}

	public void reloadCfg() {
		config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
		if (config == null || MOTDUtils.CFG_VER != config.getInt("cfg-version"))
			newCfg();
		reloadConfig();
		MOTDUtils.setMotds(ColorUtils.color(getConfig().getStringList("leftMOTDs")));
	}

	public void newCfg() {
		getLogger().log(Level.WARNING, "Oops! You have an old config version! I'm generating new...");
		File cfg = new File(getDataFolder(), "config.yml");
		if (cfg.exists()) {
			File old = new File(getDataFolder(), "oldConfig$" + System.currentTimeMillis() + ".yml");
			cfg.renameTo(old);
			getLogger().log(Level.INFO, "Saved old config to " + old.getName());
			if (cfg.exists()) {
				cfg.delete();
			}
		}
		saveDefaultConfig();
		config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
		getLogger().log(Level.INFO, "Generated new config to config.yml");
	}
	
	

}
