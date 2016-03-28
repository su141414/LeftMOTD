package me.su1414.leftmotd.bungee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.mcstats.MetricsBungee;

import me.su1414.leftmotd.utils.ColorUtils;
import me.su1414.leftmotd.utils.MOTDUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class BLeftMOTD extends Plugin implements Listener {

	private static Configuration cfg;
	private static BLeftMOTD inst;

	@Override
	public void onEnable() {
		inst = this;
		getProxy().getPluginManager().registerCommand(this, new BCommands());
		boolean m = true;
		getLogger().log(Level.INFO, "Connecting to Metrics...");
		try {
			MetricsBungee mb = new MetricsBungee(this);
			mb.start();
		} catch (IOException e1) {
			getLogger().log(Level.WARNING, "Can't connect to Metrics");
			m = false;
		}

		if (m)
			getLogger().log(Level.INFO, "Connected to Metrics");
		this.getProxy().getPluginManager().registerListener(this, this);
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		ColorUtils.setBungee();
		reloadCfg();
	}

	@EventHandler(priority = 100)
	public void onPing(ProxyPingEvent e) {
		ServerPing ping = e.getResponse();

		ping.getVersion().setProtocol(3);

		if (cfg.getBoolean("customSlots")) {
			ping.getVersion().setName((MOTDUtils.getMOTDToSend(true, ping.getVersion().getName(), 0, 0)));
		} else {
			ping.getVersion().setName((MOTDUtils.getMOTDToSend(false, "", BungeeCord.getInstance().getOnlineCount(),
					ping.getPlayers().getMax())));
		}
	}

	public static BLeftMOTD getInst() {
		return inst;
	}
	
	public void addMOTD(String motd){
		List<String> toSave = new ArrayList<>();
		for(String s : MOTDUtils.getMotds()) {
			toSave.add(s.replace("§", "&"));
		}
		toSave.add(motd);
		cfg.set("leftMOTDs", toSave);
		try {
			saveCfg();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadCfg();
	}
	
	public void removeMOTD(int i){
		MOTDUtils.getMotds().remove(i);
		List<String> toSave = new ArrayList<>();
		for(String s : MOTDUtils.getMotds()) {
			toSave.add(s.replace("§", "&"));
		}
		cfg.set("leftMOTDs", toSave);
		try {
			saveCfg();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadCfg();
	}

	public void reloadCfg() {
		File f = new File(getDataFolder(), "config.yml");
		if (!f.exists()) {
			saveDefaultConfig();
		} else {
			try {
				cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
			} catch (IOException e) {
				cfg = null;
			}
		}
		
		if (cfg == null || MOTDUtils.CFG_VER != cfg.getInt("cfg-version"))
			newCfg();

		MOTDUtils.setMotds(ColorUtils.color(cfg.getStringList("leftMOTDs")));
		MOTDUtils.setSpaces(cfg.getInt("spaces"));
	}

	public void newCfg() {
		getLogger().log(Level.WARNING, "Oops! You have an old config version! I'm generating new...");
		File cfgFile = new File(getDataFolder(), "config.yml");
		if (cfgFile.exists()) {
			File old = new File(getDataFolder(), "oldConfig$" + System.currentTimeMillis() + ".yml");
			cfgFile.renameTo(old);
			getLogger().log(Level.INFO, "Saved old config to " + old.getName());
			if (cfgFile.exists()) {
				cfgFile.delete();
			}
		}
		saveDefaultConfig();
		try {
			cfg = ConfigurationProvider.getProvider(YamlConfiguration.class)
					.load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getLogger().log(Level.INFO, "Generated new config to config.yml");
	}
	
	public void saveCfg() throws IOException{
		File f = new File(getDataFolder(), "config.yml");
		if(!f.exists())
			f.createNewFile();
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, f);
	}

	public void saveDefaultConfig() {
		File f = new File(getDataFolder(), "config.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
				cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
				cfg.set("cfg-version", MOTDUtils.CFG_VER);
				cfg.set("customSlots", false);
				cfg.set("spaces", 5);
				cfg.set("leftMOTDs", Arrays.asList("&eWelcome --->", "&bCustom left MOTDs <3"));
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
