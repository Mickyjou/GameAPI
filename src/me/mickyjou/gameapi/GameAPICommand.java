package me.mickyjou.gameapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ArenaManager.ArenaManager;

public class GameAPICommand implements CommandExecutor {

	private Main plugin;

	public GameAPICommand(Main main) {
		this.plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length == 2) {

			if (args[0].equalsIgnoreCase("createarena")) {
				ArenaManager.createArena(args[1], p);
				p.sendMessage("succses");
			} else if (args[0].equalsIgnoreCase("join")) {
				ArenaManager.addArena(args[1], p);
			} else if (args[0].equalsIgnoreCase("leave")) {
				ArenaManager.removeArena(p, true);
			} else if (args[0].equalsIgnoreCase("setlobby")) {
				GameAPI.setArenaLobbySpawn(args[1], p);
			}else if(args[0].equalsIgnoreCase("spawn")){
				GameAPI.addRandomSpawn(args[1], p);
			}
		}else if(args.length == 3){
			if(args[0].equalsIgnoreCase("setteamspawn")){
				GameAPI.addTeamSpawn(args[1],p, args[2] );
			} 
		}

		return false;
	}

}
