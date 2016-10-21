package listeners;

import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class PlayerToggleSneakListener implements Listener {

	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();

		if (EntityInteractListener.cameraPlayers.containsKey(p)) {
			PacketPlayOutCamera camera = new PacketPlayOutCamera();
			camera.a = p.getEntityId();
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(camera);
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(EntityInteractListener.cameraPlayers.get(p));
			p.setVelocity(new Vector(0, 1D, 0));
			EntityInteractListener.cameraPlayers.remove(p);
			

		}

	}
}
