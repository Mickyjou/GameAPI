package ArenaManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import ChestManager.ChestManager;
import GameState.GameState;
import me.mickyjou.gameapi.GameAPI;
import me.mickyjou.gameapi.Messages;

public class ArenaManager {

	private static Map<UUID, Location> locs = new HashMap<UUID, Location>();
	private static Map<UUID, ItemStack[]> inv = new HashMap<UUID, ItemStack[]>();
	private static Map<UUID, ItemStack[]> armor = new HashMap<UUID, ItemStack[]>();
	private static Map<UUID, Integer> level = new HashMap<UUID, Integer>();
	private static Map<UUID, Double> health = new HashMap<UUID, Double>();
	private static Map<UUID, Integer> foodlevel = new HashMap<UUID, Integer>();
	private static Map<Player, String> spectators = new HashMap<Player, String>();

	private static Map<Player, String> players = new HashMap<>();
	private static ArrayList<Player> ingame = new ArrayList<>();
	private static ArrayList<Player> spectator = new ArrayList<>();

	public static void createArena(String arena, Player p) {
		if (!getArenas().contains(arena)) {
			File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			cfg.set("minPlayers", 8);
			cfg.set("maxPlayers", 24);
			cfg.set("LobbyWaitingTime", 60);
			cfg.set("PreparingTime", 30);
			cfg.set("EnableProtectionTime", true);
			cfg.set("ProtectionTime", 30);
			cfg.set("RespawnOnlyOnce", false);
			cfg.set("PlayTime", 600);
			cfg.set("EnableDeathmatch", true);
			cfg.set("DeathmatchTime", 60);
			cfg.set("Teams", true);
			cfg.set("TeamBlue", true);
			cfg.set("TeamRed", true);
			cfg.set("TeamYellow", true);
			cfg.set("TeamGreen", true);
			cfg.set("TeamOrange", true);
			cfg.set("TeamCyan", true);
			cfg.set("TeamPink", true);
			cfg.set("TeamBlack", true);
			cfg.set("minItemsInRandomChest", 5);
			cfg.set("maxItemsInRandomChest", 15);
			cfg.set("AllowPvP", true);
			cfg.set("AllowBreakingBlocks", true);
			cfg.set("AllowPlacingBlocks", true);
			cfg.set("SetSpectatorAfterDeath", true);
			cfg.set("EnableWorldBorder", true);
			cfg.set("WorldBorderRadius", 50);

			saveFile(cfg, file);
			File cfile = new File(GameAPI.getPath(), "config.yml");
			FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);

			if (ccfg.getStringList("Arenas") != null) {
				List<String> arenas = ccfg.getStringList("Arenas");
				arenas.add(arena);
				ccfg.set("Arenas", arenas);

				saveFile(ccfg, cfile);
			} else {
				List<String> arenas = new ArrayList<>();
				arenas.add(arena);
				ccfg.set("Arenas", arenas);

				saveFile(ccfg, cfile);
			}
			File spawnsfile = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
			FileConfiguration spawnscfg = YamlConfiguration.loadConfiguration(spawnsfile);
			spawnscfg.set("RandomSpawns", 0);
			spawnscfg.set("RandomDeathmatchSpawns", 0);
			saveFile(spawnscfg, spawnsfile);
		} else {
			p.sendMessage(Messages.getArenaAlreadyExistsMessage(arena));
		}
	}

	public static List<String> getArenas() {
		File cfile = new File(GameAPI.getPath(), "config.yml");
		FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);
		List<String> arenas = ccfg.getStringList("Arenas");
		return arenas;

	}

	public static boolean isIngame(Player p) {
		return ((ingame.contains(p)) ? true : false);
	}

	public static void deleteArena(String arena, Player p) {
		if (getArenas().contains(arena)) {
			File file = new File(GameAPI.getPath() + "/" + arena, arena + ".yml");
			file.delete();

			File cfile = new File(GameAPI.getPath() + "/" + arena, "config.yml");
			FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);
			List<String> arenas = ccfg.getStringList("Arenas");
			arenas.remove(arena);
			saveFile(ccfg, cfile);

		} else {
			p.sendMessage(Messages.getArenaDontExistsMessage(arena));
		}

	}

	public static void addArena(String arena, Player p) {
		File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int spawns = cfg.getInt("RandomSpawns");
		boolean b = cfg.getBoolean("Teams");
		if (p != null) {
			if (!ingame.contains(p)) {
				if (arenaExists(arena)) {
					if (GameState.getGameState(arena) == null
							|| GameState.getGameState(arena).equals(GameState.LOBBY)) {
						if (ifLobbyPointExists(arena)) {
							if (getPlayers(arena).size() < spawns) {
								players.put(p, arena);
								ingame.add(p);
								locs.put(p.getUniqueId(), p.getLocation());
								inv.put(p.getUniqueId(), p.getInventory().getContents());
								armor.put(p.getUniqueId(), p.getInventory().getArmorContents());
								level.put(p.getUniqueId(), p.getLevel());
								health.put(p.getUniqueId(), p.getHealth());
								foodlevel.put(p.getUniqueId(), p.getFoodLevel());

								p.getInventory().clear();
								p.getInventory().setArmorContents(null);
								p.setLevel(0);
								p.setHealth(20);
								p.setFoodLevel(20);

								p.teleport(getArenaLobbyPoint(arena));

								for (Player all : getPlayers(arena)) {
									all.sendMessage(Messages.getPlayerjoinArenaMessage(p));
								}
								if (getPlayers(arena).size() == GameAPI.getMinPlayers(arena)) {
									GameAPI.startLobby(arena);
									ChestManager.loadItemValues(arena);
								}

							} else {
								p.sendMessage(Messages.getNotEnoughSpawnsMessage(arena));

							}

						} else {
							p.sendMessage(Messages.getLobbypointDontExistsMessage(arena));
						}
					} else {
						p.sendMessage(Messages.getAlreadyIngameMessage());
					}
				} else {
					p.sendMessage(Messages.getArenaDontExistsMessage(arena));
				}
			} else {
				p.sendMessage(Messages.getAlreadyIngameMessage());
			}
		}
	}

	public static void removeArena(Player p, boolean b) {
		if (ingame.contains(p)) {
			ingame.remove(p);
			p.getInventory().setContents(inv.get(p.getUniqueId()));
			p.getInventory().setArmorContents(armor.get(p.getUniqueId()));
			p.setLevel(level.get(p.getUniqueId()));
			p.setHealth(health.get(p.getUniqueId()));
			p.setFoodLevel(foodlevel.get(p.getUniqueId()));
			p.teleport(locs.get(p.getUniqueId()));

			inv.remove(p.getUniqueId());
			armor.remove(p.getUniqueId());
			level.remove(p.getUniqueId());
			health.remove(p.getUniqueId());
			foodlevel.remove(p.getUniqueId());
			locs.remove(p.getUniqueId());
			if (b == true) {
				for (Player all : getPlayers(getArena(p))) {
					all.sendMessage(Messages.getPlayerLeaveMessage(p));
				}
			}

			players.remove(p);
		} else {
			p.sendMessage(Messages.getNotInGameMessage());
		}
	}

	public static boolean arenaExists(String arena) {
		return ((getArenas().contains(arena)) ? true : false);
	}

	private static void saveFile(FileConfiguration cfg, File file) {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Location getArenaLobbyPoint(String arena) {

		File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		String world = cfg.getString("Spawns.Lobbyspawn.world");
		double x = cfg.getDouble("Spawns.Lobbyspawn.x");
		double y = cfg.getDouble("Spawns.Lobbyspawn.y");
		double z = cfg.getDouble("Spawns.Lobbyspawn.z");
		float yaw = (float) cfg.getDouble("Spawns.Lobbyspawn.yaw");
		float pitch = (float) cfg.getDouble("Spawns.Lobbyspawn.pitch");
		World w = Bukkit.getWorld(world);

		Location loc = new Location(w, x, y, z);
		loc.setYaw(yaw);
		loc.setPitch(pitch);

		return loc;

	}

	private static boolean ifLobbyPointExists(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		return ((cfg.getString("Spawns.Lobbyspawn.world") != null) ? true : false);

	}

	public static String getArena(Player p) {
		return players.get(p);

	}

	public static List<Player> getPlayers(String arena) {
		List<Player> all = new ArrayList<>();
		if (players != null) {
			for (Map.Entry entry : players.entrySet()) {
				if (arena.equals(entry.getValue())) {
					Player p = (Player) entry.getKey();
					all.add(p);
				}
			}
		}
		return all;
	}

	public static List<Player> getAllPlayers() {
		return ingame;

	}

	public static boolean isSpectator(Player p) {
		return ((spectator.contains(p)) ? true : false);
	}

	public static void addSpectator(Player p, String arena) {
		if (!isSpectator(p)) {
			spectator.add(p);
			spectators.put(p, arena);
			File cfile = new File(GameAPI.getPath() + "/" + arena, "config.yml");
			FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);

			if (!isIngame(p)) {
				locs.put(p.getUniqueId(), p.getLocation());
				inv.put(p.getUniqueId(), p.getInventory().getContents());
				armor.put(p.getUniqueId(), p.getInventory().getArmorContents());
				level.put(p.getUniqueId(), p.getLevel());
				health.put(p.getUniqueId(), p.getHealth());
				foodlevel.put(p.getUniqueId(), p.getFoodLevel());

				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.setLevel(0);
				p.setHealth(20);
				p.setFoodLevel(20);

			} else {
				players.remove(p);
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		} else {
			p.sendMessage(Messages.getAlreadySpectatorMessage());

		}
	}

	public static void removeSpectator(Player p) {
		if (isSpectator(p)) {
			spectator.remove(p);
			spectators.remove(p, spectators.get(p));
			p.getInventory().setContents(inv.get(p.getUniqueId()));
			p.getInventory().setArmorContents(armor.get(p.getUniqueId()));
			p.setLevel(level.get(p.getUniqueId()));
			p.setHealth(health.get(p.getUniqueId()));
			p.setFoodLevel(foodlevel.get(p.getUniqueId()));
			p.teleport(locs.get(p.getUniqueId()));

			inv.remove(p.getUniqueId());
			armor.remove(p.getUniqueId());
			level.remove(p.getUniqueId());
			health.remove(p.getUniqueId());
			foodlevel.remove(p.getUniqueId());
			locs.remove(p.getUniqueId());
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}

	public static List<Player> getSpectators(String arena) {
		List<Player> all = new ArrayList<>();
		if (players != null) {
			for (Map.Entry entry : spectators.entrySet()) {
				if (spectators.equals(entry.getValue())) {
					Player p = (Player) entry.getKey();
					all.add(p);
				}
			}
		}
		return all;
	}

}
