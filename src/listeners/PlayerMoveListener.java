package listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.mickyjou.gameapi.GameAPI;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		if (GameAPI.getMoveable(e.getPlayer()) == true) {
			double x = Math.floor(e.getFrom().getX());
			double z = Math.floor(e.getFrom().getZ());
			if (Math.floor(e.getTo().getX()) != x || Math.floor(e.getTo().getZ()) != z) {
				x += .5;
				z += .5;
				e.getPlayer().teleport(new Location(e.getFrom().getWorld(), x, e.getFrom().getY(), z,
						e.getFrom().getYaw(), e.getFrom().getPitch()));
			}
		}
	}

}
