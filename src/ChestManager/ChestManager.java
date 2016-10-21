package ChestManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ArenaManager.ArenaManager;
import GameState.GameState;
import me.mickyjou.gameapi.GameAPI;

public class ChestManager {
	private static HashMap<Location, Inventory> inventorys = new HashMap<>();

	public static void openInventoryWithRandomitems(Player p, Block b, String arena) {
		if (GameState.getGameState(arena) != null && GameState.getGameState(arena) == GameState.INGAME || GameState.getGameState(arena) == GameState.PROTECTING)  {
			Location loc = b.getLocation();
			if (inventorys.containsKey(loc)) {
				p.openInventory(inventorys.get(loc));
				return;
			} else {
				File cfile = new File(GameAPI.getPath() + "/" + arena, "config.yml");
				FileConfiguration ccfg = YamlConfiguration.loadConfiguration(cfile);
				Random r = new Random();
				int i = r.nextInt(ccfg.getInt("maxItemsInRandomChest"));
				if (i < ccfg.getInt("minItemsInRandomChest")) {
					i = i + ccfg.getInt("minItemsInRandomChest");
				}

				Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
				List<ItemStack> items = new ArrayList<>();
				File file = new File(GameAPI.getPath() + "/" + arena, "ItemValues.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

				for (String all : cfg.getStringList("ItemValues")) {
					int ID = 0;
					int subID = 0;
					int amount = 0;
					int chance = 0;

					if (all.contains(":")) {
						String[] a = all.split(":");
						ID = Integer.valueOf(a[0]);
						String s = a[1];
						s = s.substring(0, 1);
						subID = Integer.valueOf(s);

					}

					String[] array = all.split(", ");
					amount = Integer.valueOf(array[1]);
					chance = Integer.valueOf(array[2]);
					for (int in = 0; in < chance; in++) {
						items.add(new ItemStack(Material.getMaterial(ID), amount, (short) subID));
					}
				}
				while (i != 0) {
					i--;
					Random r2 = new Random();
					Random r3 = new Random();

					int n2 = r2.nextInt(27);
					int n3 = r3.nextInt(items.size());
					inv.setItem(n2, items.get(n3));

				}
				inventorys.put(loc, inv);

				p.openInventory(inv);
				return;
			}
		}
	}

	public static void loadItemValues(String arena) {
		File file = new File(GameAPI.getPath() + "/" + arena, "ItemValues.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			cfg.set("How to use", "ID:subID, amount, chance");
			List<String> values = cfg.getStringList("ItemValues");
			values.add("267:0, 1, 5");
			values.add("268:0, 1, 15");
			values.add("272:0, 1, 15");
			values.add("283:0, 1, 10");
			values.add("301:0, 1, 20");
			values.add("305:0, 1, 10");
			values.add("309:0, 1, 5");
			values.add("317:0, 1, 10");
			values.add("304:0, 1, 10");
			values.add("308:0, 1, 5");
			values.add("316:0, 1, 10");
			values.add("300:0, 1, 15");
			values.add("303:0, 1, 14");
			values.add("307:0, 1, 10");
			values.add("315:0, 1, 15");
			values.add("299:0, 1, 15");
			values.add("298:0, 1, 20");
			values.add("302:0, 1, 10");
			values.add("306:0, 1, 10");
			values.add("314:0, 1, 10");
			values.add("297:0, 5, 15");
			values.add("350:1, 7, 10");
			values.add("320:0, 6, 15");
			values.add("391:0, 1, 50");
			cfg.set("ItemValues", values);

			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
