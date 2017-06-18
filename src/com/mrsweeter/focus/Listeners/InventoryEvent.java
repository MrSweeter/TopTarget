package com.mrsweeter.focus.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.GUI.ColorPicker;
import com.mrsweeter.focus.GUI.StuffPicker;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Players.FocusPlayer;

public class InventoryEvent implements Listener	{
	
	private Focus pl;
	
	public InventoryEvent() {
		pl = Focus.getInstance();
	}
	
	@EventHandler
	public void onClickGUI(InventoryClickEvent event)	{
		
		Game game = pl.getGame();
		
		if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR && event.getClickedInventory() != null)	{
			
			String inventoryName = event.getClickedInventory().getName();
			Player p = (Player) event.getWhoClicked();
			FocusPlayer fP = Focus.playertoFocusPlayer(p);
			if (inventoryName.equals(Focus.getLanguage().gui_colorPicker))	{
				
				if (game.getPlayers().size() < Focus.maxPlayer || game.getPlayers().contains(fP))	{
					ColorPicker picker = game.getColorPicker();
					picker.chooseColor(p, event.getCurrentItem());
					picker.openGUI((Player) event.getWhoClicked());
					event.setCancelled(true);
				} else {
					p.closeInventory();
					p.sendMessage(Focus.getLanguage().gui_gameFull);
				}
				
			} else if (inventoryName.equals(Focus.getLanguage().gui_stuffPicker))	{
					
				FocusPlayer pF = Focus.playertoFocusPlayer(p);
				StuffPicker picker = pF.getStuffPicker();
	
				if (event.getSlot() != 19 && event.getCurrentItem().getType() != Material.BARRIER)	{
					int level = Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(0).substring(13, 14));
					String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
					int point = Integer.parseInt(itemName.substring(4, itemName.indexOf(" ")));
					if (pF.getPoint() >= point)	{
						picker.switchSlot(event.getSlot(), level);
						pF.addPoint(-point);
					}
				}
				picker.openGUI(p);
				event.setCancelled(true);
			} else if (inventoryName.equals(Focus.getLanguage().gui_gameConfiguration))	{
				
				if (event.getClick() == ClickType.RIGHT)	{
					Focus.option.rightClickSlot(event.getSlot(), pl);
				} else if (event.getClick() == ClickType.LEFT)	{
					Focus.option.leftClickSlot(event.getSlot(), pl);
				}
				Focus.option.openGUI(p);
				event.setCancelled(true);
			}
		}
	}
}
