package me.su1414.leftmotd.bungee;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
	
	public void reloadCfg(){
		File f = new File(getDataFolder(), "config.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
				cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
				cfg.set("customSlots", false);
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

		
		MOTDUtils.setMotds(ColorUtils.color(cfg.getStringList("leftMOTDs")));
	}
}
