package de.jeff_media.Drop2Inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import com.google.common.base.Enums;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Door;
import org.bukkit.inventory.ItemStack;

public class BlockDropWrapper {

	// STUFF THAT I HAVE LEFT OUT:
	// Double Slabs -> normal slabs (maybe they will disappear? I have to check)
	// Infested blocks -> normal blocks
	
	Random rand;
	
	HashMap<Material,Material> silkTouchMap;
	HashMap<Material, ItemStack[]> dropMap;
	Main main;
	
	BlockDropWrapper(Main main) {
		this.silkTouchMap=new HashMap<Material,Material>();
		this.dropMap=new HashMap<Material,ItemStack[]>();	
		this.rand=new Random();
		this.main=main;
	}
	
	boolean isPickaxe(ItemStack itemInMainHand) {
		return itemInMainHand.getType().name().toLowerCase().endsWith("_pickaxe");
	}
	
	boolean isShovel(ItemStack itemInMainHand) {
		return itemInMainHand.getType().name().toLowerCase().endsWith("_shovel");
	}
	
	boolean isFullyGrown(Block block) {
		Ageable ageable = (Ageable) block.getBlockData();
		if(ageable.getAge() == ageable.getMaximumAge()) {
			//System.out.println("This crop is fully grown");
			return true;
		}
		//System.out.println("This crop is NOT fully grown");
		return false;
	}
	
	public ItemStack[] getSilkTouchDrop(Block blockDestroyed,ItemStack itemInMainHand) {
		
		ArrayList<ItemStack> tmp = new ArrayList<ItemStack>();
		
		Material mat = blockDestroyed.getType();
		
		// https://minecraft.gamepedia.com/Enchanting#Silk_Touch

		// Why the fuck is this no switch/case statement?

		if(mat.name().toLowerCase().endsWith("_door") ||
				(mat.name().toLowerCase().endsWith("_door_block"))) {
			doDoorDrop(mat,blockDestroyed,tmp, true);
		}

		if(mat.name().equalsIgnoreCase("BEETROOTS")) {
			if(isFullyGrown(blockDestroyed)) {
				// fully grown
				tmp.add(new ItemStack(Material.BEETROOT,1));
				int seedcount = rand.nextInt(4);
				if(seedcount>0) {
					tmp.add(new ItemStack(Material.BEETROOT_SEEDS,seedcount));
				}
			} else {
				// not fully grown
				tmp.add(new ItemStack(Material.BEETROOT_SEEDS,1));
			}
		} else if(mat.name().equalsIgnoreCase("CAKE")) {
			return new ItemStack[0];
		} else if(mat.name().equalsIgnoreCase("CARROTS")) {
			if(isFullyGrown(blockDestroyed)) {
				tmp.add(new ItemStack(Material.CARROT,rand.nextInt(4) + 1));
			} else {
				tmp.add(new ItemStack(Material.CARROT,1));
			}
		} else if(mat.name().equalsIgnoreCase("COAL_ORE")) {
			if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(Material.COAL_ORE,1));
			} else {
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("COCOA")) {
			tmp.add(new ItemStack(Material.COCOA_BEANS, rand.nextInt(3)+1));
		} else if(mat.name().toLowerCase().endsWith("_coral_block")) {
			//if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(mat,1));
			//} else {
				//for(ItemStack item : getBlockDrop(blockDestroyed,itemInMainHand, fortuneLevel)) {
					//tmp.add(item);
				//}
		} else if(mat.name().equalsIgnoreCase("DIAMOND_ORE")) {
			if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(Material.DIAMOND_ORE,1));
			} else {
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("EMERALD_ORE")) {
			if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(Material.EMERALD_ORE,1));
			} else {
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("FARMLAND")) {
			tmp.add(new ItemStack(Material.DIRT,1));
		} else if(mat.name().equalsIgnoreCase("FIRE")) {
			return new ItemStack[0];
		} else if(mat.name().equalsIgnoreCase("FROSTED_ICE")) {
			return new ItemStack[0];
		} else if(mat.name().equalsIgnoreCase("GRAVEL")) {
			tmp.add(new ItemStack(Material.GRAVEL,1));
		} else if(mat.name().equalsIgnoreCase("LAPIS_ORE")) {
			if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(Material.LAPIS_ORE,1));
			} else {
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("MELON_STEM")) {
			if(!isFullyGrown(blockDestroyed)) {
				return new ItemStack[0];
			}
			int seedcount = rand.nextInt(5);
			if(seedcount>0) {
				tmp.add(new ItemStack(Material.MELON_SEEDS,seedcount));
			}
		} else if(mat.name().equalsIgnoreCase("SPAWNER")) {
			return new ItemStack[0];
		} else if(mat.name().equalsIgnoreCase("NETHER_QUARTZ_ORE")) {
			if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(Material.QUARTZ,1));
			} else {
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("NETHER_WART")) {
			if(isFullyGrown(blockDestroyed)) {
				tmp.add(new ItemStack(Material.NETHER_WART,rand.nextInt(4)+1));
			} else {
				tmp.add(new ItemStack(Material.NETHER_WART,1));
			}
		} else if(mat.name().equalsIgnoreCase("POTATOES")) {
			if(isFullyGrown(blockDestroyed)) {
				tmp.add(new ItemStack(Material.POTATO,rand.nextInt(4)+1));
			} else {
				tmp.add(new ItemStack(Material.POTATO,1));
			}
		} else if(mat.name().equalsIgnoreCase("PUMPKIN_STEM")) {
			if(!isFullyGrown(blockDestroyed)) {
				return new ItemStack[0];
			}
			int seedcount = rand.nextInt(5);
			if(seedcount>0) {
				tmp.add(new ItemStack(Material.PUMPKIN_SEEDS,seedcount));
			}
		} else if(mat.name().equalsIgnoreCase("GLOWING_REDSTONE_ORE")) {
			//System.out.println("DEBUG: GLOWING_REDSTONE_ORE");
			if(isPickaxe(itemInMainHand) ) {
				//System.out.println("DEBUG: isPickaxe");
				//tmp.add(new ItemStack(Material.REDSTONE,rand.nextInt(1)+4));
				tmp.add(new ItemStack(Material.REDSTONE_ORE));
			} else {
				//System.out.println("DEBUG: NO PICKAXE");
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("SNOW")) {
			if(isShovel(itemInMainHand)) {
				tmp.add(new ItemStack(Material.SNOW,1));
			} else {
				tmp.add(new ItemStack(Material.SNOWBALL,1));
			}
		} else if(mat.name().equalsIgnoreCase("SNOW_BLOCK")) {
			if(isShovel(itemInMainHand)) {
				tmp.add(new ItemStack(Material.SNOW_BLOCK,1));
			} else {
				// Enums.getIfPresent fixes errors on 1.12 and earlier
				Material snowball = Enums.getIfPresent(Material.class,"SNOWBALL").orNull();
				if(snowball == null) {
					//System.out.println("snowball = null");
					snowball = Enums.getIfPresent(Material.class,"SNOW_BALL").orNull();
				}
				if(snowball != null) {
					tmp.add(new ItemStack(snowball, 4));
				} else {
					//System.out.println("snowball still = null");
				}
			}
		} else if(mat.name().equalsIgnoreCase("STONE")) {
			if(isPickaxe(itemInMainHand)) {
				tmp.add(new ItemStack(Material.STONE,1));
			} else {
				return new ItemStack[0];
			}
		} else if(mat.name().equalsIgnoreCase("WHEAT")) {
			if(isFullyGrown(blockDestroyed)) {
				tmp.add(new ItemStack(Material.WHEAT,1));
				int seedcount = rand.nextInt(4);
				if(seedcount!=0) {
					tmp.add(new ItemStack(Material.WHEAT_SEEDS,seedcount));
				}
			} else {
				tmp.add(new ItemStack(Material.WHEAT_SEEDS,1));
			}
		}

		if(tmp.size()>0) {
			return tmp.toArray(new ItemStack[tmp.size()]);
		}
		
		
		// the rest
		
		ItemStack[] result = new ItemStack[1];
		if(silkTouchMap.containsKey(mat)) {
			result[0] = new ItemStack(silkTouchMap.get(mat),1);
		} else {
			result[0] = new ItemStack(mat,1);
			//System.out.println("Material "+mat.name()+" not found in Silk Touch Map, return itself");
		}
		return result;
	}
	
	public ItemStack[] getBlockDrop(Block blockDestroyed,ItemStack itemInMainHand, int fortuneLevel) {
		
		Material mat = blockDestroyed.getType();
		
		ArrayList<ItemStack> tmp = new ArrayList<ItemStack>();
		
		if(mat.name().toLowerCase().endsWith("_door") || mat.name().toLowerCase().endsWith("_door_block")) {
			doDoorDrop(mat,blockDestroyed,tmp,false);
			
		} else if(mat.name().equalsIgnoreCase("BEETROOTS")) {
			Ageable ageable = (Ageable) blockDestroyed.getBlockData();
			if(ageable.getAge() == ageable.getMaximumAge()) {
				// fully grown
				tmp.add(new ItemStack(Material.BEETROOT,1));
				int seedcount = rand.nextInt(4);
				if(seedcount>0) {
					tmp.add(new ItemStack(Material.BEETROOT_SEEDS,seedcount));
				}
			} else {
				// not fully grown
				tmp.add(new ItemStack(Material.BEETROOT_SEEDS,1));
			}
		} else if(mat.name().equalsIgnoreCase("CARROTS")) {
			if(isFullyGrown(blockDestroyed)) {
			tmp.add(new ItemStack(Material.CARROT,rand.nextInt(4) + 1));
			} else {
				tmp.add(new ItemStack(Material.CARROT,1));
			}
		} else if(mat.name().equalsIgnoreCase("COCOA")) {
			tmp.add(new ItemStack(Material.COCOA_BEANS, rand.nextInt(3)+1));
		} else if(mat.name().equalsIgnoreCase("BLUE_ICE")) {
			return new ItemStack[0];
		} else if(mat.name().equalsIgnoreCase("GRAVEL")) {
			if(rand.nextInt(10)==0) {
				tmp.add(new ItemStack(Material.FLINT,1));
			} else {
				tmp.add(new ItemStack(Material.GRAVEL,1));
			}
		} else if(mat.name().equalsIgnoreCase("WHEAT")) {
			if(isFullyGrown(blockDestroyed)) {
				tmp.add(new ItemStack(Material.WHEAT,1));
				int seedcount = rand.nextInt(4);
				if(seedcount!=0) {
					tmp.add(new ItemStack(Material.WHEAT_SEEDS,seedcount));
				}
			} else {
				tmp.add(new ItemStack(Material.WHEAT_SEEDS,1));
			}
		} else if(mat.name().equalsIgnoreCase("SNOW")) {
			if(isShovel(itemInMainHand)) {
				tmp.add(new ItemStack(Material.SNOWBALL));
			}
		}
		
		if(tmp.size()>0) {
			main.debug("tmp > 0, returning following drops:");
			for(ItemStack itemStack : tmp) {
				main.debug("- " + itemStack.toString());
			}
			return tmp.toArray(new ItemStack[tmp.size()]);
		}

		if(dropMap.containsKey(blockDestroyed.getType())) {
			return dropMap.get(blockDestroyed.getType());
		} else {
		Collection<ItemStack> drops = blockDestroyed.getDrops(itemInMainHand);		
			return drops.toArray(new ItemStack[drops.size()]);
		}

	}
	
	private void doDoorDrop(Material mat, Block blockDestroyed,ArrayList<ItemStack> tmp, boolean silkTouch) {
		if(main.mcVersion>=13) {
			if (mat.name().toLowerCase().endsWith("_door")) {
				//System.out.println("Processing door");
				Door doorMeta = (Door) blockDestroyed.getBlockData();
				if (doorMeta.getHalf() == Half.TOP) {
					tmp.add(new ItemStack(mat, 1));

					// TODO: Before deleting the bottom half, check if it really is a door
					blockDestroyed.getRelative(BlockFace.DOWN).setType(Material.AIR);
				}
			}
		} else {
			// 1.12.2 and below
			main.debug("doDoorDrop < 1.13");
			if(mat.name().contains("DOOR") && !mat.name().contains("TRAP")) {
				if (blockDestroyed.getRelative(BlockFace.DOWN).getType() == mat || silkTouch) {
					mat = getAppropiateDoorItemForFuckingAncientVersions(mat);
					main.debug("Adding " + mat + " to drops list");
					tmp.add(new ItemStack(mat, 1));
				}
			}
		}
	}

	public Material getAppropiateDoorItemForFuckingAncientVersions(Material doorBlock) {
		main.debug("Door BLOCK: " + doorBlock);
		switch(doorBlock.name()) {
			case "WOODEN_DOOR":
				doorBlock = Material.valueOf("WOOD_DOOR");
				break;
			case "IRON_DOOR_BLOCK":
				doorBlock = Material.valueOf("IRON_DOOR");
				break;
			default:
				doorBlock = Material.valueOf(doorBlock.name()+"_ITEM");
				break;
		}
		main.debug("Detected ITEM: " + doorBlock);
		return doorBlock;
	}

}
