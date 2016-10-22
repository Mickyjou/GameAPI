package MySQL;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ArenaManager.ArenaManager;
import me.mickyjou.gameapi.GameAPI;

public class PlayerStats{

	static File sqlfile = new File(GameAPI.getPath(), "mysql.yml");
	static FileConfiguration sqlcfg = YamlConfiguration.loadConfiguration(sqlfile);

	public static MySQL mysql;
	public static String host;
	public static String port;
	public static String database;
	public static String password;
	public static String user;



	public static void ConnectMySQL() {
		mysql = new MySQL(host, database, user, password);
		mysql.update(
				"CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), KILLS int, DEATHS int, WINS int, LOSES int, GAMES int);");
	}

	public void onDisable() {
	}

	public static void loadSqlConfig() {
		if (!sqlfile.exists()) {
			sqlcfg.set("host", "host");
			sqlcfg.set("port", "port");
			sqlcfg.set("database", "database");
			sqlcfg.set("username", "username");
			sqlcfg.set("password", "password");

			try {
				sqlcfg.save(sqlfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void loadRankingheadLocationConfig(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "RankingHeadLocations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			cfg.set("Locations", 0);
			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
