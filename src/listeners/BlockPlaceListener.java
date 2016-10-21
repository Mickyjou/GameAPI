package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import ArenaManager.ArenaManager;

public class BlockPlaceListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if(ArenaManager.isIngame(p)){
			e.setCancelled(true);
		}
	}

}
