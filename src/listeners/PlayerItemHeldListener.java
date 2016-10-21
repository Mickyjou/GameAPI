package listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import ArenaManager.ArenaManager;

public class PlayerItemHeldListener implements Listener {
	
	@EventHandler
	public void onItemHeld(PlayerItemHeldEvent e){
		Player p = e.getPlayer();
		if(p.getGameMode().equals(GameMode.SURVIVAL)){
			if(ArenaManager.isSpectator(p)){
				int slot = e.getNewSlot() + 1;
				float speed = (float) slot/10;
				p.setLevel(slot);
				p.setFlySpeed(speed);
			}
		}
	}

}
