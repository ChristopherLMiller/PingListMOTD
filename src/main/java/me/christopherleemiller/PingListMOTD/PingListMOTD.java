package me.christopherleemiller.PingListMOTD;

import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Chris on 3/15/14.
 */
public class PingListMOTD extends JavaPlugin {
	public String motd;
	public String prefix = "[PingListMOTD] ";
	public Logger log = Logger.getLogger("minecraft");

	@Override
	public void onEnable() {
		loadConfig();

		// enable metrics
		Metrics metrics= new Metrics(this);


		// register the command executor
		getCommand("pinglist").setExecutor(new PingListCommandExecutor(this));

		// register the event listener
		Bukkit.getPluginManager().registerEvents(new PingListListener(this), this);
	}

	@Override
	public void onDisable() {
		Logger.getLogger("minecraft").info(prefix + " is now disabled");
	}

	public void loadConfig() {
		// firstly reload from disk
		reloadConfig();

		// misc settings
		if (!getConfig().contains("debug")) getConfig().set("debug", true);

		// motd
		if (!getConfig().contains("server-list-motd")) getConfig().set("server-list-motd", "&6PingListMOTD &3by &9moose517");

		// save back out
		saveConfig();

		motd = getConfig().getString("server-list-motd");
	}
}
