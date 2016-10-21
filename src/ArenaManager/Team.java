package ArenaManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import me.mickyjou.gameapi.GameAPI;

public enum Team {
	RED, BLUE, GREEN, YELLOW, ORANGE, CYAN, PINK, BLACK;

	private static BiMap<Player, Team> teams = HashBiMap.create();

	public static void setTeam(Team team, Player p) {
		String s = ArenaManager.getArena(p);

		if (ArenaManager.isIngame(p)) {
			if (getTeam(p) == null) {
				if (isTeamEnabled(s, team)) {
					teams.put(p,team);
					setTeamColourArmor(p, team);
				}

			} else {
				teams.remove(getTeam(p), p);
				teams.put(p, team);
				setTeamColourArmor(p, team);
			}

		}

	}

	public static Team getTeam(Player p) {
		return teams.get(p);
	}

	private static List<Player> getTeamSize(String arena, String team) {
		List<Player> t = new ArrayList<>();
		for(Player all: ArenaManager.getPlayers(arena)){
			if(getTeam(all).equals(Team.valueOf(team))){
				t.add(all);
				
			}
		}
		return t;
	}

	public static boolean isTeamEnabled(String arena, Team team) {
		if (ArenaManager.arenaExists(arena)) {
			File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			String s = cfg.getString("Team" + getTeamColorAsSring(team));
			return ((s == "true") ? true : false);
		} else {
			return false;
		}
	}

	private static void setTeamColourArmor(Player p, Team team) {
		ItemStack i = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(getTeamColor(team));
		i.setItemMeta(meta);

		ItemStack i1 = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta meta1 = (LeatherArmorMeta) i1.getItemMeta();
		meta1.setColor(getTeamColor(team));
		i1.setItemMeta(meta1);

		ItemStack i2 = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) i2.getItemMeta();
		meta2.setColor(getTeamColor(team));
		i2.setItemMeta(meta2);
		p.getInventory().setBoots(i);
		p.getInventory().setChestplate(i1);
		p.getInventory().setLeggings(i2);

	}

	private static org.bukkit.Color getTeamColor(Team team) {
		org.bukkit.Color c = null;
		if (team == Team.BLACK) {
			c = org.bukkit.Color.BLACK;
		} else if (team == Team.BLUE) {
			c = org.bukkit.Color.BLUE;
		} else if (team == Team.CYAN) {
			c = org.bukkit.Color.AQUA;
		} else if (team == Team.GREEN) {
			c = org.bukkit.Color.GREEN;
		} else if (team == Team.ORANGE) {
			c = org.bukkit.Color.ORANGE;
		} else if (team == Team.PINK) {
			c = org.bukkit.Color.PURPLE;
		} else if (team == Team.RED) {
			c = org.bukkit.Color.RED;
		} else if (team == Team.YELLOW) {
			c = org.bukkit.Color.YELLOW;
		}
		return c;

	}

	private static String getTeamColorAsSring(Team team) {
		if (team == Team.BLACK) {
			return "Black";
		} else if (team == Team.BLUE) {
			return "Blue";
		} else if (team == Team.CYAN) {
			return "Cyan";
		} else if (team == Team.GREEN) {
			return "Green";
		} else if (team == Team.ORANGE) {
			return "Orange";
		} else if (team == Team.PINK) {
			return "Pink";
		} else if (team == Team.RED) {
			return "Red";
		} else if (team == Team.YELLOW) {
			return "Yellow";
		} else {
			return null;
		}

	}

	public static Team getRandomTeam(String arena) {
		List<Team> teams = new ArrayList<>();
		if(isTeamEnabled(arena, Team.BLACK)){
		teams.add(Team.BLACK);
		}
		if(isTeamEnabled(arena, Team.BLUE)){
			teams.add(Team.BLACK);
			}
		if(isTeamEnabled(arena, Team.CYAN)){
			teams.add(Team.CYAN);
			}
		if(isTeamEnabled(arena, Team.GREEN)){
			teams.add(Team.GREEN);
			}
		if(isTeamEnabled(arena, Team.ORANGE)){
			teams.add(Team.ORANGE);
			}
		if(isTeamEnabled(arena, Team.PINK)){
			teams.add(Team.PINK);
			}
		if(isTeamEnabled(arena, Team.RED)){
			teams.add(Team.RED);
			}
		if(isTeamEnabled(arena, Team.YELLOW)){
			teams.add(Team.YELLOW);
			}
		
		Team result = null;
		Random r = new Random();
		result = teams.get(r.nextInt(teams.size()));
		return result;
	}

}
