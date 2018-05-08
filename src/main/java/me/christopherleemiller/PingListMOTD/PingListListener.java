package me.christopherleemiller.PingListMOTD;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String motd = plugin.motd;

		// convert ^ to a new line
		motd = motd.replace('^', '\n');


		// convert codes of format [xxxx] to the proper version for display
		Pattern pattern = Pattern.compile("\\[([0-9a-fA-F]+)\\]");
		Matcher matcher = pattern.matcher(motd);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			char c = (char) Integer.parseInt(matcher.group(1), 16);
			matcher.appendReplacement(sb, Character.toString(c));
		}

		matcher.appendTail(sb);

		event.setMotd(ChatColor.translateAlternateColorCodes('&', sb.toString()));
	}
}