package me.mickyjou.gameapi;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ArenaManager.ArenaManager;
import WorldBorder.WorldBorder;
import listeners.BlockBreakListener;
import listeners.BlockPlaceListener;
import listeners.EntityDamageByEntityListener;
import listeners.EntityInteractListener;
import listeners.PlayerDeathListener;
import listeners.PlayerInteractListener;
import listeners.PlayerItemHeldListener;
import listeners.PlayerMoveListener;
import listeners.PlayerToggleSneakListener;

public class Main extends JavaPlugin implements CommandExecutor {

	public void onEnable() {
		
		GameAPI.setPath(getDataFolder().toString());
		registerCommands();
		registerEvents();
		loadMessageFile();
		for (String all : ArenaManager.getArenas()) {
			File file = new File(GameAPI.getPath() + "/" + all, "config.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			if (cfg.getBoolean("EnableWorldBorder") == true) {
				WorldBorder.border(all);
			}
		}

	}

	public void onDisable() {
		for (String all : ArenaManager.getArenas()) {
			for (Player p : ArenaManager.getPlayers(all)) {
				ArenaManager.removeArena(p, false);
			}
			for (Player player : ArenaManager.getSpectators(all)) {
				ArenaManager.removeSpectator(player);
			}
		}
	}

	private void registerEvents() {
		PluginManager pm = (PluginManager) this.getServer().getPluginManager();
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new PlayerDeathListener(), this);
		pm.registerEvents(new PlayerItemHeldListener(), this);
		pm.registerEvents(new EntityInteractListener(), this);
		pm.registerEvents(new PlayerToggleSneakListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		pm.registerEvents(new BlockBreakListener(), this);
		pm.registerEvents(new EntityDamageByEntityListener(), this);
		pm.registerEvents(new PlayerMoveListener(), this);

	}

	private void registerCommands() {

		GameAPICommand cmd = new GameAPICommand(this);
		getCommand("createarena").setExecutor(cmd);

	}

	private void loadMessageFile() {
		File file = new File(GameAPI.getPath(), "messages.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (!file.exists()) {
			cfg.set("Prefix", "&3[GameAPI]");
			cfg.set("PlayerJoinArenaMessage", "&7Der Spieler &6%player &7hat das Spiel betreten.");
			cfg.set("PlayerLeaveArenaMessage", "&7Der Spieler &6%player &7hat das Spiel verlassen.");
			cfg.set("ArenaDontExistsMessage", "&7Die Arena &6%arena &7existiert nicht.");
			cfg.set("AlreadyIngameMessage", "&7Du bist bereits im Spiel.");
			cfg.set("NotInGameMessage", "&7Du bist nicht im Spiel.");
			cfg.set("LobbypointDontExistsMessage", "&7Es gibt keinen Lobbypunkt fuer die Arena %arena.");
			cfg.set("NotEnoughSpawnsMessage", "&7Es gibt nicht genuegend Spawns auf der Arena %arena.");
			cfg.set("LobbyTimerMessage", "&7Das Spiel startet in &6%time &7Sekunden.");
			cfg.set("PreparngTimerMessage", "&7Die Runde beginnt in &6%time &7Sekunden.");
			cfg.set("ProtectionTimerMessage", "&7Die Schutzzeit endet in &6%time &7Sekunden.");
			cfg.set("GameTimerMessage", "&7Das Spiel endet in &6%time &7Sekunden.");
			cfg.set("GameStopBecauseNotEnoughPlayers",
					"&7Das Spiel wurde abgebrochen, da nicht genuegend Spieler vorhanden sind.");
			cfg.set("ArenaAlreadyExistsMessage", "&7Die Arena &6%arena &7existiert bereits.");
			cfg.set("ProtectionTimeEndMessage", "&7Die Schutzzeit hat geendet.");
			cfg.set("GameDrawMessage", "&7Es konnte sich kein Spieler als Sieger erweisen.");
			cfg.set("PlayerWinGameMessage", "&7Der Spieler &6%player &7hat das Spiel gewonnen.");
			cfg.set("AlreadySpectatorMessage", "&7Du bist bereits ein Spectator.");
			cfg.set("ArenaAlreadyIngameMessage", "&7Die Arena &7%arena &7ist bereits im Spiel!");
			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
