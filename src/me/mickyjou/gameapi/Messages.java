package me.mickyjou.gameapi;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Messages {
	private static File file = new File(GameAPI.getPath(), "messages.yml");
	private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	public static String getPrefix() {
		String message = cfg.getString("Prefix");
		if (message != null) {
			return ChatColor.translateAlternateColorCodes('&', message + " ");
		} else {
			return "";
		}

	}

	public static String getPlayerjoinArenaMessage(Player p) {
		String message = cfg.getString("PlayerJoinArenaMessage");
		if (message.contains("%player")) {
			message = message.replace("%player", p.getName());
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getPlayerLeaveMessage(Player p) {
		String message = cfg.getString("PlayerLeaveArenaMessage");
		if (message.contains("%player")) {
			message = message.replace("%player", p.getName());
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getArenaDontExistsMessage(String arena) {
		String message = cfg.getString("ArenaDontExistsMessage");
		if (message.contains("%arena")) {
			message = message.replace("%arena", arena);
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getAlreadyIngameMessage() {
		String message = cfg.getString("AlreadyIngameMessage");
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getNotInGameMessage() {
		String message = cfg.getString("NotInGameMessage");
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getLobbypointDontExistsMessage(String arena) {
		String message = cfg.getString("LobbypointDontExistsMessage");
		if (message.contains("%arena")) {
			message = message.replace("%arena", arena);
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getNotEnoughSpawnsMessage(String arena) {
		String message = cfg.getString("NotEnoughSpawnsMessage");
		if (message.contains("%arena")) {
			message = message.replace("%arena", arena);
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getLobbyTimerMessage(int time) {
		String message = cfg.getString("LobbyTimerMessage");
		if (message.contains("%time")) {
			message = message.replace("%time", time + "");
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getPreparngTimerMessage(int time) {
		String message = cfg.getString("PreparngTimerMessage");
		if (message.contains("%time")) {
			message = message.replace("%time", time + "");
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getProtectionTimerMessage(int time) {
		String message = cfg.getString("ProtectionTimerMessage");
		if (message.contains("%time")) {
			message = message.replace("%time", time + "");
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getGameTimerMessage(int time) {
		String message = cfg.getString("GameTimerMessage");
		if (message.contains("%time")) {
			message = message.replace("%time", time + "");
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getGameStopBecauseNotEnoughPlayers() {
		String message = cfg.getString("GameStopBecauseNotEnoughPlayers");
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getArenaAlreadyExistsMessage(String arena) {
		String message = cfg.getString("ArenaAlreadyExistsMessage");
		if (message.contains("%arena")) {
			message = message.replace("%arena", arena);
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getProtectionTimeEndMessage() {
		String message = cfg.getString("ProtectionTimeEndMessage");
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getGameDrawMessage() {
		String message = cfg.getString("GameDrawMessage");
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getPlayerWinGameMessage(Player p) {
		String message = cfg.getString("PlayerWinGameMessage");
		if (message.contains("%player")) {
			message = message.replace("%player", p.getName());
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getAlreadySpectatorMessage() {
		String message = cfg.getString("AlreadySpectatorMessage");
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

	public static String getArenaAlreadyIngameMessage(String arena) {
		String message = cfg.getString("ArenaAlreadyIngameMessage");
		if (message.contains("%arena")) {
			message = message.replace("%arena", arena);
		}
		return ChatColor.translateAlternateColorCodes('&', getPrefix() + message);
	}

}
