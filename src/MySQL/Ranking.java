package MySQL;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.mickyjou.gameapi.GameAPI;

public class Ranking {

	static HashMap<Integer, String> rankings = new HashMap<>();

	public static Map<Player, String> newloc = new HashMap<>();

	public static int amount;

	public static void setRankingOrderedByDeaths(int players, String arena) {
		ResultSet rs = PlayerStats.mysql.query("SELECT UUID FROM Stats ORDER BY DEATHS DESC LIMIT " + players + "");

		int i1 = 0;
		try {
			while (rs.next()) {
				i1++;
				rankings.put(i1, rs.getString("UUID"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File file = new File(GameAPI.getPath() + "/" + arena, "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Location> locs = new ArrayList<Location>();
		int locations = cfg.getInt("Locations");
		for (int i = 0; i < locations; i++) {
			String world = cfg.getString("location " + i + ".world");
			Double x = cfg.getDouble("location " + i + ".x");
			Double y = cfg.getDouble("location " + i + ".y");
			Double z = cfg.getDouble("location " + i + ".z");
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			locs.add(loc);
			System.out.println(i);
		}

		for (int i = 0; i < locs.size(); i++) {

			int id = i + 1;
			Skull s = (Skull) locs.get(i).getBlock().getState();
			s.setSkullType(SkullType.PLAYER);
			String name = String.valueOf(Bukkit.getOfflinePlayer(UUID.fromString(rankings.get(id))).getName());
			s.setOwner(name);
			s.update();

			Location newloc = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
					locs.get(i).getZ());

			if (newloc.getBlock().getState() instanceof Sign) {
				BlockState b = newloc.getBlock().getState();
				Sign S = (Sign) b;

				S.setLine(0, "Platz #" + id);
				S.setLine(1, name);
				S.setLine(2, "");
				S.setLine(3, "Deaths: " + Stats.getDeaths(Bukkit.getOfflinePlayer(name).getUniqueId().toString()));

				S.update();
			}

		}

	}

	public static void setRankingOrderedByKills(int players, String arena) {
		ResultSet rs = PlayerStats.mysql.query("SELECT UUID FROM Stats ORDER BY KILLS DESC LIMIT " + players + "");

		int i1 = 0;
		try {
			while (rs.next()) {
				i1++;
				rankings.put(i1, rs.getString("UUID"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File file = new File(GameAPI.getPath() + "/" + arena, "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Location> locs = new ArrayList<Location>();
		HashMap<Integer, BlockFace> bf = new HashMap<>();
		for (int i = 0; i < players; i++) {
			int r = i + 1;
			String world = cfg.getString("location " + r + ".world");
			Double x = cfg.getDouble("location " + r + ".x");
			Double y = cfg.getDouble("location " + r + ".y");
			Double z = cfg.getDouble("location " + r + ".z");
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			locs.add(loc);

		}

		for (int i = 0; i < locs.size(); i++) {
			int id = i + 1;

			Skull s = (Skull) locs.get(i).getBlock().getState();
			s.setSkullType(SkullType.PLAYER);
			String name = String.valueOf(Bukkit.getOfflinePlayer(UUID.fromString(rankings.get(id))).getName());
			s.setOwner(name);
			s.update();

			Location newloc = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
					locs.get(i).getZ());

			if (newloc.getBlock().getState() instanceof Sign) {
				BlockState b = newloc.getBlock().getState();
				Sign S = (Sign) b;

				S.setLine(0, "Platz #" + id);
				S.setLine(1, name);
				S.setLine(2, "");
				S.setLine(3, "Kills: " + Stats.getKills(Bukkit.getOfflinePlayer(name).getUniqueId().toString()));

				S.update();
			}

		}

	}

	public static void setRankingOrderedByWins(int players, String arena) {
		ResultSet rs = PlayerStats.mysql.query("SELECT UUID FROM Stats ORDER BY WINS DESC LIMIT " + players + "");

		int i1 = 0;
		try {
			while (rs.next()) {
				i1++;
				rankings.put(i1, rs.getString("UUID"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File file = new File(GameAPI.getPath() + "/" + arena, "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Location> locs = new ArrayList<Location>();
		int locations = cfg.getInt("Locations");
		for (int i = 0; i < locations; i++) {
			String world = cfg.getString("location " + i + ".world");
			Double x = cfg.getDouble("location " + i + ".x");
			Double y = cfg.getDouble("location " + i + ".y");
			Double z = cfg.getDouble("location " + i + ".z");
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			locs.add(loc);
			System.out.println(i);
		}
		for (int i = 0; i < locs.size(); i++) {

			int id = i + 1;
			Skull s = (Skull) locs.get(i).getBlock().getState();
			s.setSkullType(SkullType.PLAYER);
			String name = String.valueOf(Bukkit.getOfflinePlayer(UUID.fromString(rankings.get(id))).getName());
			s.setOwner(name);
			s.update();

			Location newloc = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
					locs.get(i).getZ());

			if (newloc.getBlock().getState() instanceof Sign) {
				BlockState b = newloc.getBlock().getState();
				Sign S = (Sign) b;

				S.setLine(0, "Platz #" + id);
				S.setLine(1, name);
				S.setLine(2, "");
				S.setLine(3, "Wins: " + Stats.getWins(Bukkit.getOfflinePlayer(name).getUniqueId().toString()));

				S.update();
			}

		}

	}

	public static void setRankingOrderedByLoses(int players, String arena) {
		ResultSet rs = PlayerStats.mysql.query("SELECT UUID FROM Stats ORDER BY LOSES DESC LIMIT " + players + "");

		int i1 = 0;
		try {
			while (rs.next()) {
				i1++;
				rankings.put(i1, rs.getString("UUID"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File file = new File(GameAPI.getPath() + "/" + arena, "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Location> locs = new ArrayList<Location>();
		int locations = cfg.getInt("Locations");
		for (int i = 0; i < locations; i++) {
			String world = cfg.getString("location " + i + ".world");
			Double x = cfg.getDouble("location " + i + ".x");
			Double y = cfg.getDouble("location " + i + ".y");
			Double z = cfg.getDouble("location " + i + ".z");
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			locs.add(loc);
			System.out.println(i);
		}
		for (int i = 0; i < locs.size(); i++) {

			int id = i + 1;
			Skull s = (Skull) locs.get(i).getBlock().getState();
			s.setSkullType(SkullType.PLAYER);
			String name = String.valueOf(Bukkit.getOfflinePlayer(UUID.fromString(rankings.get(id))).getName());
			s.setOwner(name);
			s.update();

			Location newloc = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
					locs.get(i).getZ());

			if (newloc.getBlock().getState() instanceof Sign) {
				BlockState b = newloc.getBlock().getState();
				Sign S = (Sign) b;

				S.setLine(0, "Platz #" + id);
				S.setLine(1, name);
				S.setLine(2, "Loses: " + Stats.getLoses(Bukkit.getOfflinePlayer(name).getUniqueId().toString()));
				S.setLine(3, "");

				S.update();
			}

		}

	}

	public static void setRankingOrderedByGames(int players, String arena) {
		ResultSet rs = PlayerStats.mysql.query("SELECT UUID FROM Stats ORDER BY GAMES DESC LIMIT " + players + "");

		int i1 = 0;
		try {
			while (rs.next()) {
				i1++;
				rankings.put(i1, rs.getString("UUID"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File file = new File(GameAPI.getPath() + "/" + arena, "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<Location> locs = new ArrayList<Location>();
		int locations = cfg.getInt("Locations");
		for (int i = 0; i < locations; i++) {
			String world = cfg.getString("location " + i + ".world");
			Double x = cfg.getDouble("location " + i + ".x");
			Double y = cfg.getDouble("location " + i + ".y");
			Double z = cfg.getDouble("location " + i + ".z");
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			locs.add(loc);
			System.out.println(i);
		}
		for (int i = 0; i < locs.size(); i++) {

			int id = i + 1;
			Skull s = (Skull) locs.get(i).getBlock().getState();
			s.setSkullType(SkullType.PLAYER);
			String name = String.valueOf(Bukkit.getOfflinePlayer(UUID.fromString(rankings.get(id))).getName());
			s.setOwner(name);
			s.update();

			Location newloc = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY() - 1,
					locs.get(i).getZ());

			if (newloc.getBlock().getState() instanceof Sign) {
				BlockState b = newloc.getBlock().getState();
				Sign S = (Sign) b;

				S.setLine(0, "Platz #" + id);
				S.setLine(1, name);
				S.setLine(2, "Games: " + Stats.getGames(Bukkit.getOfflinePlayer(name).getUniqueId().toString()));
				S.setLine(3, "");

				S.update();
			}

		}

	}

	public static void addRankingLocation(Player p, String arena) {
		File file = new File(GameAPI.getPath(), "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		amount = cfg.getInt("Locations") + 1;
		newloc.put(p, arena);
		p.sendMessage(ChatColor.GRAY
				+ "Schlage den Kopf, um einen Ranking-Kopf hinzuzufügen. Stelle sicher, dass sich unter dem Kopf ein Schild befindet.");

	}

}
