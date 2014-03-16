package com.moosemanstudios.PingListMOTD;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Chris on 3/16/14.
 */
public class PingListListener implements Listener{
	PingListMOTD plugin;

	PingListListener(PingListMOTD plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		// see if we can find a '\n' which indicates a new line
		String motd = plugin.getConfig().getString("server-list-motd");
		motd = motd.replace('^', '\n');
		event.setMotd(ChatColor.translateAlternateColorCodes('&',motd));
	}
}