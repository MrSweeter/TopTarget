package com.mrsweeter.focus.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Players.FocusPlayer;
import com.mrsweeter.focus.Players.Utils.Dual;

public class StuffPicker implements MyGUI	{
	
	private Inventory stuffPicker;
	private FocusPlayer player;
	private ItemStack point;
	private Focus pl;
	
	public StuffPicker(FocusPlayer p, Focus main) {
		pl = main;
		player = p;
		stuffPicker = Bukkit.createInventory(null, 54, Focus.getLanguage().gui_stuffPicker);
		loadItems();
		
		point = new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta = point.getItemMeta();
		meta.setDisplayName("§6§l" + player.getPoint());
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		point.setItemMeta(meta);
	}

	@Override
	public void loadItems() {
		
		Game game = pl.getGame();
		
		if (game.helmet)	{switchSlot(13, 0);}
		if (game.chestplate)	{switchSlot(22, 0);}
		if (game.leggings)	{switchSlot(31, 0);}
		if (game.boots)	{switchSlot(40, 0);}
		if (game.sword)	{switchSlot(21, 0);}
		if (game.axe)	{switchSlot(30, 0);}
		if (game.shield)	{switchSlot(39, 0);}
		if (game.pearl)	{switchSlot(14, 0);}
		if (game.bow)	{switchSlot(23, 0);}
		if (game.smoke)	{switchSlot(41, 0);}
	}

	@Override
	public void openGUI(Player player) {
		update();
		player.openInventory(stuffPicker);
	}

	@Override
	public void update() {
		
		Game game = pl.getGame();
		ItemMeta meta = point.getItemMeta();
		meta.setDisplayName("§6§l" + player.getPoint() + " points");
		point.setItemMeta(meta);
		
		stuffPicker.setItem(19, point);
		
		if (!game.helmet)	{stuffPicker.setItem(13, new ItemStack(Material.AIR));}
		if (!game.chestplate)	{stuffPicker.setItem(22, new ItemStack(Material.AIR));}
		if (!game.leggings)	{stuffPicker.setItem(31, new ItemStack(Material.AIR));}
		if (!game.boots)	{stuffPicker.setItem(40, new ItemStack(Material.AIR));}
		if (!game.sword)	{stuffPicker.setItem(21, new ItemStack(Material.AIR));}
		if (!game.axe)	{stuffPicker.setItem(30, new ItemStack(Material.AIR));}
		if (!game.shield)	{stuffPicker.setItem(39, new ItemStack(Material.AIR));}
		if (!game.pearl)	{stuffPicker.setItem(14, new ItemStack(Material.AIR));}
		if (!game.bow)	{stuffPicker.setItem(23, new ItemStack(Material.AIR));}
		if (!game.smoke)	{stuffPicker.setItem(41, new ItemStack(Material.AIR));}
	}

	private ItemStack loadMetaCost(Dual dual)	{
		
		ItemStack item = new ItemStack(dual.getItem());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f§l" + dual.getCost() + " points");
		item.setItemMeta(meta);
		return item;
	}
	
	public void switchSlot(int slot, int level) {
		ItemStack item;
		switch (slot)	{
		case 13:
			if (level < 5 && Focus.getAllStuff().getHelmet().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getHelmet().get(level+1));
				
				stuffPicker.setItem(13, item);
			} else {
				stuffPicker.setItem(13, Focus.getAllStuff().getMax());
			}
			player.changeStuff("helmet", Focus.getAllStuff().getHelmet().get(level).getItem());
			break;
		case 22:
			if (level < 5 && Focus.getAllStuff().getChestPlate().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getChestPlate().get(level+1));
				
				stuffPicker.setItem(22, item);
			} else {
				stuffPicker.setItem(22, Focus.getAllStuff().getMax());
			}
			player.changeStuff("chestplate", Focus.getAllStuff().getChestPlate().get(level).getItem());
			break;
		case 31:
			if (level < 5 && Focus.getAllStuff().getLeggings().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getLeggings().get(level+1));
				
				stuffPicker.setItem(31, item);
			} else {
				stuffPicker.setItem(31, Focus.getAllStuff().getMax());
			}
			player.changeStuff("leggings", Focus.getAllStuff().getLeggings().get(level).getItem());
			break;
		case 40:
			if (level < 5 && Focus.getAllStuff().getBoots().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getBoots().get(level+1));				
				
				stuffPicker.setItem(40, item);
			} else {
				stuffPicker.setItem(40, Focus.getAllStuff().getMax());
			}
			player.changeStuff("boots", Focus.getAllStuff().getBoots().get(level).getItem());
			break;
		case 21:
			if (level < 5 && Focus.getAllStuff().getSword().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getSword().get(level+1));
				
				stuffPicker.setItem(21, item);
			} else {
				stuffPicker.setItem(21, Focus.getAllStuff().getMax());
			}
			player.changeStuff("sword", Focus.getAllStuff().getSword().get(level).getItem());
			break;
		case 30:
			if (level < 5 && Focus.getAllStuff().getAxe().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getAxe().get(level+1));
				
				stuffPicker.setItem(30, item);
			} else {
				stuffPicker.setItem(30, Focus.getAllStuff().getMax());
			}
			player.changeStuff("axe", Focus.getAllStuff().getAxe().get(level).getItem());
			break;
		case 39:
			if (level < 5 && Focus.getAllStuff().getShield().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getShield().get(level+1));
				
				stuffPicker.setItem(39, item);
			} else {
				stuffPicker.setItem(39, Focus.getAllStuff().getMax());
			}
			player.changeStuff("shield", Focus.getAllStuff().getShield().get(level).getItem());
			break;
		case 14:
			if (level < 5 && Focus.getAllStuff().getPearl().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getPearl().get(level+1));
				
				stuffPicker.setItem(14, item);
			} else {
				stuffPicker.setItem(14, Focus.getAllStuff().getMax());
			}
			player.changeStuff("pearl", Focus.getAllStuff().getPearl().get(level).getItem());
			break;
		case 23:
			if (level < 5 && Focus.getAllStuff().getBow().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getBow().get(level+1));
				
				if (level == 1)	{
					switchSlot(32, 0);
					player.changeStuff("arrow", Focus.getAllStuff().getArrow().get(level).getItem());
				}
				
				stuffPicker.setItem(23, item);
			} else {
				stuffPicker.setItem(23, Focus.getAllStuff().getMax());
			}
			player.changeStuff("bow", Focus.getAllStuff().getBow().get(level).getItem());
			break;
		case 32:
			if (level < 5 && Focus.getAllStuff().getArrow().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getArrow().get(level+1));
				if (item.getAmount() > 64)	{item.setAmount(64);}
				stuffPicker.setItem(32, item);
			} else {
				stuffPicker.setItem(32, Focus.getAllStuff().getMax());
			}
			player.changeStuff("arrow", Focus.getAllStuff().getArrow().get(level).getItem());
			break;
		case 41:
			if (level < 5 && Focus.getAllStuff().getSmoke().get(level+1) != null)	{
				
				item = loadMetaCost(Focus.getAllStuff().getSmoke().get(level+1));
				
				
				stuffPicker.setItem(41, item );
			} else {
				stuffPicker.setItem(41, Focus.getAllStuff().getMax());
			}
			player.changeStuff("smoke", Focus.getAllStuff().getSmoke().get(level).getItem());
			break;
		default:
			update();
			break;
		}
	}

}
