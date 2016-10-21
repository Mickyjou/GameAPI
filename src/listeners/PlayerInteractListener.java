package listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import ArenaManager.ArenaManager;
import ChestManager.ChestManager;

public class PlayerInteractListener implements Listener {

	@EventHandler

	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (ArenaManager.isIngame(e.getPlayer())) {
				if (e.getClickedBlock().getType() == Material.REDSTONE_BLOCK) {
					ChestManager.openInventoryWithRandomitems(e.getPlayer(), e.getClickedBlock(),
							ArenaManager.getArena(e.getPlayer()));
				}
			}
		}
	}

}
