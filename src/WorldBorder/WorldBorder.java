package WorldBorder;

import java.io.File;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import ArenaManager.ArenaManager;
import me.mickyjou.gameapi.GameAPI;
import me.mickyjou.gameapi.Main;

public class WorldBorder {

	private static int radius = 50;

	private static int random(Integer d, Integer x) {
		Random r = new Random();
		return r.nextInt(x - d + 1) + d;
	}

	public static void border(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		new BukkitRunnable() {

			int radius = cfg.getInt("WorldBorderRadius");

			@Override
			public void run() {
				File cfile = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
				FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);
				String world = ccfg.getString("Spawns.ArenaMiddle.world");
				Double x = ccfg.getDouble("Spawns.ArenaMiddle.x");
				Double y = ccfg.getDouble("Spawns.ArenaMiddle.y");
				Double z = ccfg.getDouble("Spawns.ArenaMiddle.z");

				Location middle = new Location(Bukkit.getWorld(world), x, y, z);
				for (Player p : ArenaManager.getPlayers(arena)) {
					if (p.getLocation().distance(middle) >= radius) {
						Vector playervector = p.getLocation().toVector();
						Vector middlevector = middle.toVector();
						Vector v = middlevector.clone().subtract(playervector)
								.multiply(1.0 / middlevector.distance(playervector)).setY(0.5);
						p.setVelocity(v);
						p.getWorld().playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 20, 20);
					}
				}

			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
		new BukkitRunnable() {

			@Override
			public void run() {
				File cfile = new File(GameAPI.getPath() + "/" + arena, "spawns.yml");
				FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);
				String world = ccfg.getString("Spawns.ArenaMiddle.world");
				Double x1 = ccfg.getDouble("Spawns.ArenaMiddle.x");
				Double y1 = ccfg.getDouble("Spawns.ArenaMiddle.y");
				Double z1 = ccfg.getDouble("Spawns.ArenaMiddle.z");

				Location middle = new Location(Bukkit.getWorld(world), x1, y1, z1);
				for (Player all : ArenaManager.getPlayers(arena)) {
					if (all.getLocation().distance(middle) >= 20) {
						Location min = all.getLocation().add(-10.0, -10.0, -10.0);
						Location max = all.getLocation().add(10.0, 10.0, 10.0);
						for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
							for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
								for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
									Location loc = new Location(all.getWorld(), x, y, z);
									if (loc.distance(middle) > radius
											&& loc.distance(middle) < radius + 1) {
										if (random(0, 20) == 0) {
											for (int i = 0; i < 10; i++) {
												all.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 20);
											}
										}

									}
								}
							}
						}

					}
				}

			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 15);
	}

}
