package me.christopherleemiller.PingListMOTD;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PingListMOTD extends JavaPlugin {
	public String motd;
	public String prefix = "[PingListMOTD] ";

	@Override
	public void onEnable() {
		// Load the config
		loadConfig();

		// enable metrics
		Metrics metrics= new Metrics(this, 991);

		// register the command executor
		getCommand("pinglist").setExecutor(new PingListCommandExecutor(this));

		// register the event listener
		Bukkit.getPluginManager().registerEvents(new PingListListener(this), this);

		// Check for updates to the plugin
		new UpdateChecker(this, 42658).getVersion((version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
				this.getLogger().info("No updates found");
			} else {
				this.getLogger().info("Update found");
			}
		}));
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
