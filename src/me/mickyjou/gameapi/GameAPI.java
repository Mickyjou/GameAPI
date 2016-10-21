package me.mickyjou.gameapi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ArenaManager.ArenaManager;
import ArenaManager.Team;
import GameState.GameState;

public class GameAPI {

	static String path;
	private static HashMap<Plugin, String> paths = new HashMap<>();
	private static Map<String, Integer> scheduler = new HashMap<>();
	private static List<Player> moveable = new ArrayList<>();

	public static void setPath(String path) {
		paths.put(Main.getPlugin(Main.class), path);
	}

	public static String getPath() {
		return paths.get(Main.getPlugin(Main.class));
	}

	public static int getMinPlayers(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = 0;
		if (ArenaManager.arenaExists(arena)) {
			i = cfg.getInt("minPlayers");
		}
		return i;
	}

	public static int getMaxPlayers(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = 0;
		if (ArenaManager.arenaExists(arena)) {
			i = cfg.getInt("maxPlayers");
		}
		return i;
	}

	public static int getLobbyWaitingTime(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = 0;
		if (ArenaManager.arenaExists(arena)) {
			i = cfg.getInt("LobbyWaitingTime");
		}
		return i;
	}

	public static int getPreparingTime(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = 0;
		if (ArenaManager.arenaExists(arena)) {
			i = cfg.getInt("PreparingTime");
		}
		return i;
	}

	public static int getProtectionTime(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = 0;
		if (ArenaManager.arenaExists(arena)) {
			i = cfg.getInt("ProtectionTime");
		}
		return i;
	}

	public static int getPlayTime(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = 0;
		if (ArenaManager.arenaExists(arena)) {
			i = cfg.getInt("PlayTime");
		}
		return i;
	}

	public static void setArenaLobbySpawn(String arena, Player p) {
		if (ArenaManager.arenaExists(arena)) {
			File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			cfg.set("Spawns.Lobbyspawn.world", p.getWorld().getName());
			cfg.set("Spawns.Lobbyspawn.x", p.getLocation().getX());
			cfg.set("Spawns.Lobbyspawn.y", p.getLocation().getY());
			cfg.set("Spawns.Lobbyspawn.z", p.getLocation().getZ());
			cfg.set("Spawns.Lobbyspawn.yaw", p.getLocation().getYaw());
			cfg.set("Spawns.Lobbyspawn.pitch", p.getLocation().getPitch());

			saveFile(cfg, file);
		}
	}

	public static void addTeamSpawn(String arena, Player p, String team) {
		if (ArenaManager.arenaExists(arena)) {
			Team t = Team.valueOf(team.toUpperCase());

			File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			cfg.set("Spawns.Teamspawn " + team.toUpperCase() + ".world", p.getWorld().getName());
			cfg.set("Spawns.Teamspawn " + team.toUpperCase() + ".x", p.getLocation().getX());
			cfg.set("Spawns.Teamspawn " + team.toUpperCase() + ".y", p.getLocation().getY());
			cfg.set("Spawns.Teamspawn " + team.toUpperCase() + ".z", p.getLocation().getZ());
			cfg.set("Spawns.Teamspawn " + team.toUpperCase() + ".yaw", p.getLocation().getYaw());
			cfg.set("Spawns.Teamspawn " + team.toUpperCase() + ".pitch", p.getLocation().getPitch());

			saveFile(cfg, file);

		} else {
			p.sendMessage(Messages.getArenaDontExistsMessage(arena));
		}
	}

	public static void addRandomSpawn(String arena, Player p) {
		if (ArenaManager.arenaExists(arena)) {
			File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			int i = cfg.getInt("RandomSpawns");
			i++;
			cfg.set("Spawns.Randomspawn" + i + ".world", p.getWorld().getName());
			cfg.set("Spawns.Randomspawn" + i + ".x", p.getLocation().getX());
			cfg.set("Spawns.Randomspawn" + i + ".y", p.getLocation().getY());
			cfg.set("Spawns.Randomspawn" + i + ".z", p.getLocation().getZ());
			cfg.set("Spawns.Randomspawn" + i + ".yaw", p.getLocation().getYaw());
			cfg.set("Spawns.Randomspawn" + i + ".pitch", p.getLocation().getPitch());
			cfg.set("RandomSpawns", i);

			saveFile(cfg, file);
		} else {
			p.sendMessage(Messages.getArenaDontExistsMessage(arena));
		}
	}

	public static boolean isIngame(Player p) {
		return ((ArenaManager.getAllPlayers().contains(p)) ? true : false);
	}

	private static void saveFile(FileConfiguration cfg, File file) {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startLobby(String arena) {
		int TaskId = 0;
		GameState.setGameState(arena, GameState.LOBBY);

		TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {

			int i = getLobbyWaitingTime(arena);

			@Override
			public void run() {

				if (getMinPlayers(arena) <= ArenaManager.getPlayers(arena).size()) {

					if (i > 0) {
						for (Player all : ArenaManager.getPlayers(arena)) {
							all.setLevel(i);
						}
						if (i == 120 || i == 60 || i == 30 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2
								|| i == 1) {
							for (Player all : ArenaManager.getPlayers(arena)) {
								all.playSound(all.getLocation(), Sound.NOTE_BASS, 20, 20);
								all.sendMessage(Messages.getLobbyTimerMessage(i));
							}

						}
					} else {
						Bukkit.getScheduler().cancelTask(scheduler.get(arena));
						for (Player all : ArenaManager.getPlayers(arena)) {
							all.playSound(all.getLocation(), Sound.NOTE_PLING, 20, 20);
							all.setLevel(0);
							setMoveable(false, all);
						}
						startPreparingTime(arena);
						teleportToIngameSpawn(arena);
					}
					i--;
				} else {
					Bukkit.getScheduler().cancelTask(scheduler.get(arena));
					for (Player all : ArenaManager.getPlayers(arena)) {
						all.setLevel(0);
						all.teleport(ArenaManager.getArenaLobbyPoint(arena));
						all.playSound(all.getLocation(), Sound.ITEM_BREAK, 20, 20);

					}
					GameState.setGameState(arena, null);

				}
			}

		}, 0, 20);

		scheduler.put(arena, TaskId);

	}

	private static void startPreparingTime(String arena) {
		int TaskId = 0;
		GameState.setGameState(arena, GameState.PREPARING);
		TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			int i = getPreparingTime(arena);

			@Override
			public void run() {

				if (getMinPlayers(arena) <= ArenaManager.getPlayers(arena).size()) {

					if (i > 0) {
						for (Player all : ArenaManager.getPlayers(arena)) {
							all.setLevel(i);
						}
						if (i == 120 || i == 60 || i == 30 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2
								|| i == 1) {
							for (Player all : ArenaManager.getPlayers(arena)) {
								all.playSound(all.getLocation(), Sound.NOTE_BASS, 20, 20);
								all.sendMessage(Messages.getPreparngTimerMessage(i));
							}

						}
					} else {
						File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
						FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
						if (cfg.getBoolean("EnableProtectionTime") == true) {
							Bukkit.getScheduler().cancelTask(scheduler.get(arena));
							startProtectionTime(arena);

						} else {
							Bukkit.getScheduler().cancelTask(scheduler.get(arena));
							startGame(arena);

						}
						for (Player all : ArenaManager.getPlayers(arena)) {
							setMoveable(true, all);
							all.setLevel(0);
							all.playSound(all.getLocation(), Sound.NOTE_PLING, 20, 20);
						}

					}
					i--;
				} else {
					Bukkit.getScheduler().cancelTask(scheduler.get(arena));
					for (Player all : ArenaManager.getPlayers(arena)) {
						all.setLevel(0);
						all.sendMessage(Messages.getGameStopBecauseNotEnoughPlayers());
						all.playSound(all.getLocation(), Sound.ITEM_BREAK, 20, 20);
						all.teleport(ArenaManager.getArenaLobbyPoint(arena));
					}
					startEndLobby(arena);
					GameState.setGameState(arena, null);
					scheduler.remove(arena);

				}

			}

		}, 0, 20);
		scheduler.put(arena, TaskId);
	}

	private static void teleportToIngameSpawn(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		if (cfg.getBoolean("Teams") == true) {
			for (Player all : ArenaManager.getPlayers(arena)) {
				if (Team.getTeam(all) != null) {
					teleportToTeamSpawn(all);
				} else {
					setRandomTeam(all, arena);
					teleportToTeamSpawn(all);

				}
			}

		} else {
			teleportToRandomSpawn(arena);

		}

	}

	private static void setRandomTeam(Player p, String arena) {
		Team team = Team.getRandomTeam(arena);
		if (team.isTeamEnabled(arena, team)) {
			Team.setTeam(team, p);
		} else {
			setRandomTeam(p, arena);
		}
	}

	public static void teleportToTeamSpawn(Player p) {
		Team team = Team.getTeam(p);

		String arena = ArenaManager.getArena(p);
		File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String world = cfg.getString("Spawns.Teamspawn " + team + ".world");
		Double x = cfg.getDouble("Spawns.Teamspawn " + team + ".x");
		Double y = cfg.getDouble("Spawns.Teamspawn " + team + ".y");
		Double z = cfg.getDouble("Spawns.Teamspawn " + team + ".z");
		float yaw = (float) cfg.getDouble("Spawns.Teamspawn " + team + ".yaw");
		float pitch = (float) cfg.getDouble("Spawns.Teamspawn " + team + ".pitch");

		World w = Bukkit.getWorld(world);
		Location loc = new Location(w, x, y, z);
		loc.setYaw(yaw);
		loc.setPitch(pitch);
		p.teleport(loc);

	}

	public static void teleportToRandomSpawn(String arena) {

		File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		int spawns = cfg.getInt("RandomSpawns");
		for (Player all : ArenaManager.getPlayers(arena)) {

			String world = cfg.getString("Spawns.Randomspawn" + spawns + ".world");
			Double x = cfg.getDouble("Spawns.Randomspawn" + spawns + ".x");
			Double y = cfg.getDouble("Spawns.Randomspawn" + spawns + ".y");
			Double z = cfg.getDouble("Spawns.Randomspawn" + spawns + ".z");
			float yaw = (float) cfg.getDouble("Spawns.Randomspawn" + spawns + ".yaw");
			float pitch = (float) cfg.getDouble("Spawns.Randomspawn" + spawns + ".pitch");

			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			loc.setYaw(yaw);
			loc.setPitch(pitch);
			all.teleport(loc);
			spawns--;
		}

	}

	public static void startProtectionTime(String arena) {
		GameState.setGameState(arena, GameState.PROTECTING);
		int TaskId = 0;

		TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {

			int i = getProtectionTime(arena);

			@Override
			public void run() {

				if (getMinPlayers(arena) <= ArenaManager.getPlayers(arena).size()) {

					if (i > 0) {
						for (Player all : ArenaManager.getPlayers(arena)) {
						}
						if (i == 120 || i == 60 || i == 30 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2
								|| i == 1) {
							for (Player all : ArenaManager.getPlayers(arena)) {
								if (i != 0) {
									all.playSound(all.getLocation(), Sound.NOTE_BASS, 20, 20);
								} else {
									all.playSound(all.getLocation(), Sound.NOTE_PLING, 20, 20);
								}
								all.sendMessage(Messages.getProtectionTimerMessage(i));
							}

						}
					} else {
						Bukkit.getScheduler().cancelTask(scheduler.get(arena));
						for (Player all : ArenaManager.getPlayers(arena)) {
							all.playSound(all.getLocation(), Sound.NOTE_PLING, 20, 20);
							all.sendMessage(Messages.getProtectionTimeEndMessage());
						}
						startGame(arena);
					}
					i--;
				} else {
					Bukkit.getScheduler().cancelTask(scheduler.get(arena));
					for (Player all : ArenaManager.getPlayers(arena)) {
						all.setLevel(0);
						all.playSound(all.getLocation(), Sound.ITEM_BREAK, 20, 20);
						all.teleport(ArenaManager.getArenaLobbyPoint(arena));
					}
					startEndLobby(arena);
					GameState.setGameState(arena, null);
				}
			}

		}, 0, 20);

		scheduler.put(arena, TaskId);
	}

	private static void startGame(String arena) {
		GameState.setGameState(arena, GameState.INGAME);
		int TaskId = 0;
		TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {

			int i = getPlayTime(arena);

			@Override
			public void run() {
				if (1 < ArenaManager.getPlayers(arena).size()) {
					if (i > 0) {
						if (i == 120 || i == 60 || i == 30 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2
								|| i == 1) {
							for (Player all : ArenaManager.getPlayers(arena)) {
								all.playSound(all.getLocation(), Sound.NOTE_BASS, 20, 20);
								all.sendMessage(Messages.getGameTimerMessage(i));
							}

						}
					} else {
						Bukkit.getScheduler().cancelTask(scheduler.get(arena));
						for (Player all : ArenaManager.getPlayers(arena)) {
							all.playSound(all.getLocation(), Sound.NOTE_PLING, 20, 20);

						}
						getResult(arena);
					}
					i--;
				} else {
					Bukkit.getScheduler().cancelTask(scheduler.get(arena));
					getResult(arena);
					startEndLobby(arena);
					GameState.setGameState(arena, GameState.END);

				}
			}

		}, 0, 20);

		scheduler.put(arena, TaskId);
	}

	private static void setMoveable(Boolean b, Player p) {
		if (b == true) {
			if (moveable.contains(p)) {
				moveable.remove(p);
			}
		} else {
			if (b == false) {
				moveable.add(p);
			}
		}
	}

	public static boolean getMoveable(Player p) {
		return ((moveable.contains(p)) ? true : false);
	}

	private static void getResult(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if (cfg.getBoolean("Teams") == true) {

		} else {
			if (ArenaManager.getPlayers(arena).size() > 1) {
				for (Player all : ArenaManager.getPlayers(arena)) {
					all.sendMessage(Messages.getGameDrawMessage());
				}
				for (Player all : ArenaManager.getSpectators(arena)) {
					all.sendMessage(Messages.getGameDrawMessage());
				}
			} else {
				Player p = null;
				for (Player all : ArenaManager.getPlayers(arena)) {
					all.sendMessage(Messages.getPlayerWinGameMessage(all));
					p = all;
				}
				for (Player all : ArenaManager.getSpectators(arena)) {
					all.sendMessage(Messages.getPlayerWinGameMessage(p));
				}
			}
		}
	}

	private static void startEndLobby(String arena) {
		GameState.setGameState(arena, GameState.END);
		int Taskid = 0;

		for (Player all : ArenaManager.getPlayers(arena)) {
			all.teleport(ArenaManager.getArenaLobbyPoint(arena));
		}

		Taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			int i = 15;

			@Override
			public void run() {
				if (i < 0) {
					for (Player all : ArenaManager.getPlayers(arena)) {
						all.setLevel(i);
					}
					i--;

				} else {
					Bukkit.getScheduler().cancelTask(scheduler.get(arena));
					for (Player all : ArenaManager.getPlayers(arena)) {
						System.out.println(all);
						ArenaManager.removeArena(all, false);
					}
					for(Player all: ArenaManager.getSpectators(arena)){
						ArenaManager.removeSpectator(all);
					}
					GameState.setGameState(arena, null);
				}

			}

		}, 0, 20);
		scheduler.put(arena, Taskid);
	}

	public static void setDeathmatchRandomSpawn(String arena, Player p) {
		if (ArenaManager.arenaExists(arena)) {
			File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			int i = cfg.getInt("RandomSpawns");
			i++;
			cfg.set("Spawns.RandomDeathmatchspawn" + i + ".world", p.getWorld().getName());
			cfg.set("Spawns.RandomDeathmatchspawn" + i + ".x", p.getLocation().getX());
			cfg.set("Spawns.RandomDeathmatchspawn" + i + ".y", p.getLocation().getY());
			cfg.set("Spawns.RandomDeathmatchspawn" + i + ".z", p.getLocation().getZ());
			cfg.set("Spawns.RandomDeathmatchspawn" + i + ".yaw", p.getLocation().getYaw());
			cfg.set("Spawns.RandomDeathmatchspawn" + i + ".pitch", p.getLocation().getPitch());
			cfg.set("RandomDeathmatchSpawns", i);

			saveFile(cfg, file);
		} else {
			p.sendMessage(Messages.getArenaDontExistsMessage(arena));
		}
	}

	public static void setArenaMiddle(String arena, Player p) {
		File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Spawns.ArenaMiddle.world", p.getWorld().getName());
		cfg.set("Spawns.ArenaMiddle.x", p.getLocation().getBlockX());
		cfg.set("Spawns.ArenaMiddle.y", p.getLocation().getBlockY());
		cfg.set("Spawns.ArenaMiddle.z", p.getLocation().getBlockZ());

		saveFile(cfg, file);
	}

}
