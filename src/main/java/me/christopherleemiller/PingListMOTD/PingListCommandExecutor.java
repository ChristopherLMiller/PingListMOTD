package me.christopherleemiller.PingListMOTD;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Chris on 3/16/14.
 */
public class PingListCommandExecutor implements CommandExecutor {
	PingListMOTD plugin;

	PingListCommandExecutor(PingListMOTD plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pinglist")) {
			if (args.length == 0) {
				showHelp(sender);
			} else {
				if (args[0].equalsIgnoreCase("help")) {
					showHelp(sender);
				} else if (args[0].equalsIgnoreCase("version")) {
					sender.sendMessage(ChatColor.GOLD + "PingListMOTD " + ChatColor.WHITE + "v" + plugin.getDescription().getVersion() + ChatColor.GOLD + " by " + ChatColor.YELLOW + "moose517");
				} else if (args[0].equalsIgnoreCase("motd")) {
					setMotd(sender, args);
				} else if (args[0].equalsIgnoreCase("view")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server-list-motd")));
				} else if (args[0].equalsIgnoreCase("reload")) {
					plugin.loadConfig();
					sender.sendMessage("Config reloaded");
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid command.  Please see " + ChatColor.WHITE + "/pinglist help" + ChatColor.RED + " for available commands");
				}
			}
		}
		return false;
	}

	public void showHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "/pinglist help" + ChatColor.WHITE + ": Display the help screen");
		sender.sendMessage(ChatColor.RED + "/pinglist version" + ChatColor.WHITE + ": Display plugin version");
		sender.sendMessage(ChatColor.RED + "/pinglist view " + ChatColor.WHITE + ": View the Ping List MOTD");

		if (sender.hasPermission("pinglist.admin")) {
			sender.sendMessage(ChatColor.RED + "/pinglist motd <motd to be set here>" + ChatColor.WHITE + ": Change the Ping List MOTD");
			sender.sendMessage(ChatColor.RED + "/pinglist reload" + ChatColor.WHITE + ": Reload the config file");
		}
	}

	public void setMotd(CommandSender sender, String[] args) {
		if (sender.hasPermission("pinglist.admin")) {
			if (args.length > 1) {
				String motd = makeString(args);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',motd));
				plugin.getConfig().set("server-list-motd", motd);
				plugin.saveConfig();
				sender.sendMessage("Ping List MOTD successfully set");
			} else {
				sender.sendMessage("Must provide a motd to set");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Missing required permission node: " + ChatColor.WHITE + "pinglist.admin");
		}
	}

	public String makeString(String[] args) {
		StringBuilder string = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			string.append(args[i] + " ");
		}
		return string.toString();
	}
}
