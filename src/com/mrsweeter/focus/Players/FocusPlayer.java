package com.mrsweeter.focus.Players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.GUI.StuffPicker;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Players.Utils.EffectUtils;

public class FocusPlayer	{
	
	private Player player;
	private Map<String, ItemStack> stuff = new HashMap<>();
	private StuffPicker stuffInventory = null;
	private String color;
	private char colorCode = 'r';
	private int point;
	private int victories;
	private int kill;
	private int death;
	
	public FocusPlayer(Player p, char code)	{
		player = p;
		colorCode = code;
		fillDefaultStuff();
		stuffInventory = new StuffPicker(this, Focus.getInstance());
	}
	
	public void openStuff(Focus pl)	{
		new BukkitRunnable() {
			
			@Override
			public void run() {
				stuffInventory.openGUI(player);		
			}
		}.runTaskLater(pl, 5);
	}
	
	public boolean isAlive()	{
		if (player.isDead())	{return false;}
		if (player.getGameMode() != GameMode.ADVENTURE)	{return false;}
		return true;
	}
	
	public void loadStuffForGame(Game game)	{
		
		PlayerInventory inv = player.getInventory();
		inv.clear();
		if (game.helmet)	{inv.setHelmet(stuff.get("helmet"));}
		if (game.chestplate)	{inv.setChestplate(stuff.get("chestplate"));}
		if (game.leggings)	{inv.setLeggings(stuff.get("leggings"));}
		if (game.boots)	{inv.setBoots(stuff.get("boots"));}
		if (game.sword)	{inv.addItem(stuff.get("sword"));}
		if (game.axe)	{inv.addItem(stuff.get("axe"));}
		if (game.shield)	{inv.setItemInOffHand(stuff.get("shield"));}
		if (game.bow)	{inv.addItem(stuff.get("bow"));}
		if (game.pearl)	{inv.addItem(stuff.get("pearl"));}
		if (game.smoke)	{inv.addItem(stuff.get("smoke"));}
		if (game.bow && stuff.get("bow").getType() != Material.AIR)	{
			int quant = stuff.get("arrow").getAmount();
			ItemStack item = stuff.get("arrow");
			while (quant > 0)	{
				if (quant >= 64)	{item.setAmount(64);}
				else {item.setAmount(quant);}
				inv.addItem(item);
				quant -= 64;
			}
		}
		loadCustomEffect();
		
	}
	
	public void reset() {
		
		fillDefaultStuff();
		stuffInventory.update();
		
	}
	
	private void loadCustomEffect()	{
		
		ItemStack item;
		ItemMeta meta;
		List<String> lores;
		for (String str : stuff.keySet())	{
			
			item = stuff.get(str);
			if (item != null && item.getType() != Material.AIR)	{
				
				meta = item.getItemMeta();
				lores = meta.getLore();
				int pos;
				int level;
				if (lores != null)	{
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7MAX-HEALTH:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applyMaxHealth(player, level);
					}
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7SPEED:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applySpeed(player, level);
					}
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7KB-RESIST:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applyKnockbackResist(player, level);
					}
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7JUMP:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applyJumpBoost(player, level);
					}
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7SLOWNESS:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applySlowness(player, level);
					}
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7RESISTANCE:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applyResistance(player, level);
					}
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7WEAKNESS:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applyWeakness(player, level);
					}
				}
			}
		}
	}
	
	private void fillDefaultStuff()	{
		Game game = Focus.getInstance().getGame();
		StuffCollection stuffCollection = Focus.getAllStuff();
		if (game.helmet && stuffCollection.getHelmet().get(0) != null)	{stuff.put("helmet", stuffCollection.getHelmet().get(0).getItem());} else {stuff.put("helmet", new ItemStack(Material.AIR));}
		if (game.chestplate && stuffCollection.getChestPlate().get(0) != null)	{stuff.put("chestplate", stuffCollection.getChestPlate().get(0).getItem());} else {stuff.put("chestplate", new ItemStack(Material.AIR));}
		if (game.leggings && stuffCollection.getLeggings().get(0) != null)	{stuff.put("leggings", stuffCollection.getLeggings().get(0).getItem());} else {stuff.put("leggings", new ItemStack(Material.AIR));}
		if (game.boots && stuffCollection.getBoots().get(0) != null)	{stuff.put("boots", stuffCollection.getBoots().get(0).getItem());} else {stuff.put("boots", new ItemStack(Material.AIR));}
		if (game.sword && stuffCollection.getSword().get(0) != null)	{stuff.put("sword", stuffCollection.getSword().get(0).getItem());} else {stuff.put("sword", new ItemStack(Material.AIR));}
		if (game.axe && stuffCollection.getAxe().get(0) != null)	{stuff.put("axe", stuffCollection.getAxe().get(0).getItem());} else {stuff.put("axe", new ItemStack(Material.AIR));}
		if (game.shield && stuffCollection.getShield().get(0) != null)	{stuff.put("shield", stuffCollection.getShield().get(0).getItem());} else {stuff.put("shield", new ItemStack(Material.AIR));}
		if (game.pearl && stuffCollection.getPearl().get(0) != null)	{stuff.put("pearl", stuffCollection.getPearl().get(0).getItem());} else {stuff.put("pearl", new ItemStack(Material.AIR));}
		if (game.smoke && stuffCollection.getSmoke().get(0) != null)	{stuff.put("smoke", stuffCollection.getSmoke().get(0).getItem());} else {stuff.put("smoke", new ItemStack(Material.AIR));}
		if (game.bow && stuffCollection.getBow().get(0) != null)	{stuff.put("bow", stuffCollection.getBow().get(0).getItem());} else {stuff.put("bow", new ItemStack(Material.AIR));}
		if (game.bow && stuffCollection.getArrow().get(0) != null)	{stuff.put("arrow", stuffCollection.getArrow().get(0).getItem());} else {stuff.put("arrow", new ItemStack(Material.AIR));}
	}
	
	public Player getPlayer()	{
		return player;
	}

	public void addKill(int i) {
		kill += i;
	}
	public void addPoint(int i) {
		point += i;
	}
	public int getPoint()	{
		return point;
	}

	public void addVictories(int i) {
		victories += i;
	}

	public int getVictories() {
		return victories;
	}

	public int getKill() {
		return kill;
	}
	public Map<String, ItemStack> getStuff()	{
		return stuff;
	}
	public void changeStuff(String str, ItemStack item)	{
		if (stuff.keySet().contains(str))	{
			stuff.put(str, item);
		}
	}

	public String getTeamColor() {
		return color;
	}
	
	public char getCodeColor() {
		return colorCode;
	}

	public void setColor(String lowerCase) {
		color = lowerCase;
	}

	public StuffPicker getStuffPicker() {
		return stuffInventory;
	}

	public int getDeath() {
		return death;
	}

	public void addDeath(int v) {
		death += v;
	}
}
