package de.jeff_media.Drop2Inventory;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;

public class Utils {

	Main plugin;

	Utils(Main main) {
		this.plugin=main;
	}
	 boolean isBlockEnabled(Material mat) {
		 plugin.debug("Checking if "+mat.name()+" is enabled...");
		 plugin.debug("Whitelist: "+plugin.blocksIsWhitelist);
		if (!plugin.blocksIsWhitelist) {
			if (plugin.disabledBlocks.contains(mat)) {
				plugin.debug("Its disabled on the blacklist!");
				return false;
			}
			plugin.debug("Its enabled!");
			return true;
		}
		if (plugin.disabledBlocks.contains(mat)) {
			plugin.debug("Its enabled on the whitelist!");
			return true;
		}
		 plugin.debug("Its not on the whitelist!");
		plugin.debug("BTW the whitelist contains "+plugin.disabledBlocks.size()+" blocks");
		return false;
	}

	boolean isMobEnabled(LivingEntity mob) {
		if (!plugin.mobsIsWhitelist) {
			if (plugin.disabledMobs.contains(mob.getType().name().toLowerCase()))
				return false;
			return true;
		}
		if (plugin.disabledMobs.contains(mob.getType().name().toLowerCase()))
			return true;
		return false;
	}

	void addOrDrop(ItemStack item, Player player) {
		plugin.debug("addOrDrop: " + item.toString() + " -> "+player.getName());

		ItemStack[] items = new ItemStack[1];
		items[0] = item;
		addOrDrop(items,player);
		/*if(plugin.autoCondense) {
			plugin.debug("Auto condensing "+item.getType().name());
			plugin.ingotCondenser.condense(player.getInventory(), item.getType());
		}*/

	}

	static boolean hasPermissionForThisTool(Material mat, Player p) {
		String matt = mat.name().toLowerCase();
		if(matt.contains("_pickaxe")) {
			return p.hasPermission("drop2inventory.tool.pickaxe");
		}
		if(matt.contains("_axe")) {
			return p.hasPermission("drop2inventory.tool.axe");
		}
		if(matt.contains("_hoe")) {
			return p.hasPermission("drop2inventory.tool.hoe");
		}
		if(matt.contains("_sword")) {
			return p.hasPermission("drop2inventory.tool.sword");
		}
		if(matt.contains("_shovel")) {
			return p.hasPermission("drop2inventory.tool.shovel");
		}
		else return p.hasPermission("drop2inventory.tool.hand");
	}

	void addOrDrop(ItemStack[] items, Player player) {
		if(plugin.getConfig().getBoolean("avoid-hotbar")) {
			plugin.debug("  avoid-hotbar enabled");
			plugin.hotbarStuffer.stuffHotbar(player.getInventory());
		} else {
			plugin.debug("  avoid-hotbar disabled");
		}
		for(ItemStack item : items) {
			plugin.debug(" addOrDrop#2");
			if(item==null) continue;
			if(item.getType()==Material.AIR) continue;
			HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(item);
			for (ItemStack leftover : leftovers.values()) {
				if(leftover.getType() == Material.AIR) continue;
				Item drop = player.getWorld().dropItemNaturally(player.getLocation(), leftover);
				plugin.itemSpawnListener.drops.add(drop.getUniqueId());
			}
			if(plugin.autoCondense) {
				plugin.debug("Auto condensing "+item.getType().name());
				plugin.ingotCondenser.condense(player.getInventory(), item.getType());
			}
		}
		if(plugin.getConfig().getBoolean("avoid-hotbar")) {
			plugin.hotbarStuffer.unstuffHotbar(player.getInventory());
		}
	}

	ItemStack getItemInMainHand(Player p) {
		if(plugin.mcVersion<9) {
			return p.getInventory().getItemInHand();
		}
		return p.getInventory().getItemInMainHand();
	}

	ItemStack getItemInMainHand(PlayerInventory inv) {
		if(plugin.mcVersion<9) {
			return inv.getItemInHand();
		}
		return inv.getItemInMainHand();
	}

	// Returns 16 for 1.16, etc.
	static int getMcVersion(String bukkitVersionString) {
		Pattern p = Pattern.compile("^1\\.(\\d*)\\.");
		Matcher m = p.matcher((bukkitVersionString));
		int version = -1;
		while(m.find()) {
			if(NumberUtils.isNumber(m.group(1)))
			version = Integer.parseInt(m.group(1));
		}
		return version;
	}

	static void renameFileInPluginDir(Main plugin,String oldName, String newName) {
		File oldFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + oldName);
		File newFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + newName);
		oldFile.getAbsoluteFile().renameTo(newFile.getAbsoluteFile());
	}
}
