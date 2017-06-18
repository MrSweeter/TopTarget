package com.mrsweeter.focus.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Players.FocusPlayer;
import com.mrsweeter.focus.Players.Teams;

public class ColorPicker implements MyGUI {
	
	private Inventory colorPicker;
	private List<ItemStack> colorAvailable;
	private List<ItemStack> colorUnavailable;
	private Map<UUID, ItemStack> playerColor = new HashMap<>();
	private Teams teams;
	private Focus pl;
	
	public ColorPicker() {
		pl = Focus.getInstance();
		teams = pl.getTeams();
		colorPicker = Bukkit.createInventory(null, 18, Focus.getLanguage().gui_colorPicker);
		loadItems();
		colorUnavailable = new ArrayList<>();
	}
	
	public void chooseColor(Player player, ItemStack item)	{
		
		FocusPlayer p = Focus.playertoFocusPlayer(player);
		UUID uuid = p.getPlayer().getUniqueId();
		
		if (playerColor.containsKey(uuid))	{
			colorAvailable.add(playerColor.get(uuid));
			colorUnavailable.remove(playerColor.get(uuid));
			pl.getGame().removeFocusPlayer(player);
			
			Team t = teams.getTeam(p.getTeamColor());
			if (t instanceof Team)	{
				t.removeEntry(p.getPlayer().getName());
			}
			p.setColor("");
			player.setScoreboard(teams.getSidebarScoreboard());
		}
		
		if (!item.getItemMeta().getDisplayName().contains("Quit game"))	{
			colorAvailable.remove(item);
			colorUnavailable.add(item);
			playerColor.put(uuid, item);
			String color = item.getItemMeta().getDisplayName().toLowerCase().substring(0, 4);
			pl.getGame().addFocusPlayer(new FocusPlayer(player, color.charAt(1)));
			
			p.setColor(item.getItemMeta().getDisplayName().replace(color, ""));
			Team t =teams.getTeam(item.getItemMeta().getDisplayName().toLowerCase().substring(4));
			t.addEntry(p.getPlayer().getName());
			player.setScoreboard(teams.getSidebarScoreboard());
			
		} else {
			playerColor.remove(uuid);
			pl.getGame().removeFocusPlayer(player);
		}
		
		pl.getGame().updateDisplayer();
	}

	@Override
	public void loadItems() {
		colorAvailable = new ArrayList<>();
		ItemStack temp; ItemMeta meta;
		temp = new ItemStack(Material.DIAMOND_BLOCK);
		meta = temp.getItemMeta();
		meta.setDisplayName("§b§lAqua");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.COAL_BLOCK);
		meta = temp.getItemMeta();
		meta.setDisplayName("§0§lBlack");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)3);
		meta = temp.getItemMeta();
		meta.setDisplayName("§9§lBlue");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)9);
		meta = temp.getItemMeta();
		meta.setDisplayName("§3§lDark_Aqua");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)11);
		meta = temp.getItemMeta();
		meta.setDisplayName("§1§lDark_Blue");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.STAINED_CLAY, 1, (byte)9);
		meta = temp.getItemMeta();
		meta.setDisplayName("§8§lDark_Gray");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)13);
		meta = temp.getItemMeta();
		meta.setDisplayName("§2§lDark_Green");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)10);
		meta = temp.getItemMeta();
		meta.setDisplayName("§5§lDark_Purple");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.REDSTONE_BLOCK);
		meta = temp.getItemMeta();
		meta.setDisplayName("§4§lDark_Red");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.GOLD_BLOCK);
		meta = temp.getItemMeta();
		meta.setDisplayName("§6§lGold");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)8);
		meta = temp.getItemMeta();
		meta.setDisplayName("§7§lGray");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)5);
		meta = temp.getItemMeta();
		meta.setDisplayName("§a§lGreen");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)2);
		meta = temp.getItemMeta();
		meta.setDisplayName("§d§lLight_Purple");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.STAINED_CLAY, 1, (byte)14);
		meta = temp.getItemMeta();
		meta.setDisplayName("§c§lRed");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.QUARTZ_BLOCK);
		meta = temp.getItemMeta();
		meta.setDisplayName("§f§lWhite");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
		temp = new ItemStack(Material.WOOL, 1, (byte)4);
		meta = temp.getItemMeta();
		meta.setDisplayName("§e§lYellow");
		temp.setItemMeta(meta);
		colorAvailable.add(temp);
		
	}

	@Override
	public void openGUI(Player player) {
		update();
		player.openInventory(colorPicker);
	}

	@Override
	public void update() {
		colorPicker.clear();
		for (ItemStack item : colorAvailable)	{
			colorPicker.addItem(item);
		}
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta meta = quit.getItemMeta();
		meta.setDisplayName("§c§lQuit game");
		quit.setItemMeta(meta);
		colorPicker.setItem(17, quit);
	}
}
