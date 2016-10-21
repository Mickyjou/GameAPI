package listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import ArenaManager.ArenaManager;
import me.mickyjou.gameapi.GameAPI;
import me.mickyjou.gameapi.Main;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (GameAPI.isIngame(p)) {
			p.setHealth(20);
			p.setVelocity(new Vector(0, 1D, 0));
			ArenaManager.addSpectator(p, ArenaManager.getArena(p));
		}
	}

	public void Respawn(final Player player, int Time) {
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {

			@Override
			public void run() {
				((CraftPlayer) player).getHandle().playerConnection
						.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
				String arena = ArenaManager.getArena(player);
				File file = new File(GameAPI.getPath() + "/" + arena, "config.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				if (cfg.getBoolean("RespawnOnlyOnce") == true) {
					ArenaManager.addSpectator(player, arena);
				}
			}
		}, Time);
	}

}
