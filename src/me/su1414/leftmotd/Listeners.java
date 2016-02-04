package me.su1414.leftmotd;

import java.util.Arrays;

import org.bukkit.Bukkit;

import com.comphenix.packetwrapper.WrapperStatusServerOutServerInfo;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import me.su1414.leftmotd.utils.MOTDUtils;

public class Listeners {

	private PacketAdapter packetAdapter;
	private LeftMOTD inst;
	
	public Listeners(LeftMOTD instance) {
		this.inst = instance;
		packetAdapter = new PacketAdapter(inst, ListenerPriority.HIGHEST, PacketType.Status.Server.OUT_SERVER_INFO) {
			public void onPacketSending(PacketEvent e){
				if (e.getPacketType() != PacketType.Status.Server.OUT_SERVER_INFO)
					return;
				WrapperStatusServerOutServerInfo packet = new WrapperStatusServerOutServerInfo(e.getPacket());
				WrappedServerPing ping = packet.getJsonResponse();
				
				ping.setVersionProtocol(3);
				
				if (inst.getConfig().getBoolean("customSlots")) {
					ping.setVersionName(MOTDUtils.getMOTDToSend(true, ping.getVersionName(), 0, 0));
				} else {
					ping.setVersionName(MOTDUtils.getMOTDToSend(false, "", Arrays.asList(Bukkit.getOnlinePlayers()).size(), Bukkit.getMaxPlayers()));
				}
			}
		};
	}
	
	public PacketAdapter getPacketAdapter() {
		return packetAdapter;
	}
}
