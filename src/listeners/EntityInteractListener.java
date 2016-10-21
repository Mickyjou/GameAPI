package listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class EntityInteractListener implements Listener {

	public static Map<Player, Player> cameraPlayers = new HashMap<>();
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player target = (Player) e.getRightClicked();
			PacketPlayOutCamera camera = new PacketPlayOutCamera();
			camera.a = target.getEntityId();
			p.setGameMode(GameMode.SPECTATOR);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(camera);
			cameraPlayers.put(p, target);

		}
	}

}
