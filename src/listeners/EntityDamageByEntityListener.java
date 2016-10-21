package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ArenaManager.ArenaManager;
import GameState.GameState;
import me.mickyjou.gameapi.GameAPI;

public class EntityDamageByEntityListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() != null || e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if (GameAPI.isIngame(p) && GameAPI.isIngame(damager)) {
				if (GameState.getGameState(ArenaManager.getArena(p)) == GameState.INGAME) {
					e.setCancelled(false);
				} else {
					e.setCancelled(true);
				}
			}

		}
	}

}
