package com.mrsweeter.focus.GUI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.GUI.Buttons.EnableDisableButton;
import com.mrsweeter.focus.GUI.Buttons.MsButton;
import com.mrsweeter.focus.GUI.Buttons.MultipleValueGmOption;
import com.mrsweeter.focus.GUI.Buttons.MultipleValueInteger;
import com.mrsweeter.focus.GUI.Buttons.MultipleValueMap;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.GmOption;
import com.mrsweeter.focus.Game.Parameters;

public class ParametersGUI implements MyGUI {

	private Inventory current;
	private Map<Integer, MsButton> buttons;
	private Focus pl;
	
	public ParametersGUI() {
		pl = Focus.getInstance();
		current = Bukkit.getServer().createInventory(null, 54, Focus.getLanguage().gui_gameConfiguration);
		buttons = new HashMap<>();
//		gm = new GamemodeButton(Material.ENDER_PORTAL_FRAME, "§cDeathmatch", Arrays.asList("", ""));
		loadItems();
	}
	
	@Override
	public void loadItems() {
		
		buttons.put(0, new MultipleValueGmOption(Material.ENDER_PORTAL_FRAME, Focus.getLanguage().parameters_gamemode, Arrays.asList(""), Arrays.asList(GmOption.ROUNDUP, GmOption.DEATHMATCH)));
		buttons.put(1, new MultipleValueGmOption(Material.BOOKSHELF, Focus.getLanguage().parameters_victories, Arrays.asList(""), Arrays.asList(GmOption.ROUNDS, GmOption.KILLS)));
		buttons.put(2, new MultipleValueInteger(Material.BOOK, Focus.getLanguage().parameters_killsLimit, Arrays.asList(""), Arrays.asList(Parameters.KWLIMIT_VERYLOW, Parameters.KWLIMIT_LOW, Parameters.KWLIMIT_NORMAL, Parameters.KWLIMIT_HIGH, Parameters.KWLIMIT_EXTREME)));
		buttons.put(4, new EnableDisableButton(Material.GOLDEN_APPLE, Material.BARRIER, Focus.getLanguage().parameters_life, Arrays.asList("")));
		buttons.put(6, new MultipleValueInteger(Material.IRON_INGOT, Focus.getLanguage().parameters_startingP, Arrays.asList(""), Arrays.asList(Parameters.SP_VERYLOW, Parameters.SP_LOW, Parameters.SP_NORMAL, Parameters.SP_HIGH, Parameters.SP_EXTREME)));
		buttons.put(7, new MultipleValueInteger(Material.GOLD_INGOT, Focus.getLanguage().parameters_roundP, Arrays.asList(""), Arrays.asList(Parameters.RP_VERYLOW, Parameters.RP_LOW, Parameters.RP_NORMAL, Parameters.RP_HIGH, Parameters.RP_EXTREME)));
		buttons.put(8, new MultipleValueInteger(Material.DIAMOND, Focus.getLanguage().parameters_killP, Arrays.asList(""), Arrays.asList(Parameters.KP_VERYLOW, Parameters.KP_LOW, Parameters.KP_NORMAL, Parameters.KP_HIGH, Parameters.KP_EXTREME)));
		
		buttons.put(24, new EnableDisableButton(Material.DIAMOND_HELMET, Material.BARRIER, Focus.getLanguage().parameters_stuff_helmet, Arrays.asList("")));
		buttons.put(33, new EnableDisableButton(Material.DIAMOND_CHESTPLATE, Material.BARRIER, Focus.getLanguage().parameters_stuff_chestplate, Arrays.asList("")));
		buttons.put(42, new EnableDisableButton(Material.DIAMOND_LEGGINGS, Material.BARRIER, Focus.getLanguage().parameters_stuff_leggings, Arrays.asList("")));
		buttons.put(51, new EnableDisableButton(Material.DIAMOND_BOOTS, Material.BARRIER, Focus.getLanguage().parameters_stuff_boots, Arrays.asList("")));
		buttons.put(32, new EnableDisableButton(Material.DIAMOND_SWORD, Material.BARRIER, Focus.getLanguage().parameters_stuff_sword, Arrays.asList("")));
		buttons.put(41, new EnableDisableButton(Material.DIAMOND_AXE, Material.BARRIER, Focus.getLanguage().parameters_stuff_axe, Arrays.asList("")));
		buttons.put(50, new EnableDisableButton(Material.SHIELD, Material.BARRIER, Focus.getLanguage().parameters_stuff_shield, Arrays.asList("")));
		buttons.put(25, new EnableDisableButton(Material.ENDER_PEARL, Material.BARRIER, Focus.getLanguage().parameters_stuff_pearl, Arrays.asList("")));
		buttons.put(34, new EnableDisableButton(Material.BOW, Material.BARRIER, Focus.getLanguage().parameters_stuff_bow, Arrays.asList("")));
		buttons.put(52, new EnableDisableButton(Material.SNOW_BALL, Material.BARRIER, Focus.getLanguage().parameters_stuff_smoke, Arrays.asList("")));
		
		buttons.put(20, new MultipleValueMap(Material.MAP, "§6Map:", Arrays.asList(""), Focus.maps, "Random"));
		int pos = 27;
		for (int i = 0; i < Focus.maps.size(); i++)	{
			
			pos++;
			if (pos == 31)	{pos += 6;}
			if (pos == 40)	{pos += 6;}
			
			buttons.put(pos, new EnableDisableButton(Material.EMPTY_MAP, Material.BARRIER, "§6" + Focus.maps.get(i).getName() + ":", Arrays.asList("")));
		}
		
	}

	@Override
	public void openGUI(Player player) {
		update();
		player.openInventory(current);
	}
	
	private void read()	{
		
		Game game = pl.getGame();
		
		game.gamemode = ((MultipleValueGmOption) buttons.get(0)).getValue();
		game.victories = ((MultipleValueGmOption) buttons.get(1)).getValue();
		game.limitWin = ((MultipleValueInteger) buttons.get(2)).getValue();
		game.startPoint = ((MultipleValueInteger) buttons.get(6)).getValue();
		game.roundPoint = ((MultipleValueInteger) buttons.get(7)).getValue();
		game.killPoint = ((MultipleValueInteger) buttons.get(8)).getValue();
		
		game.setDisplayHP(((EnableDisableButton) buttons.get(4)).isEnable());
		
		game.helmet = ((EnableDisableButton) buttons.get(24)).isEnable();
		game.chestplate = ((EnableDisableButton) buttons.get(33)).isEnable();
		game.leggings = ((EnableDisableButton) buttons.get(42)).isEnable();
		game.boots = ((EnableDisableButton) buttons.get(51)).isEnable();
		game.sword = ((EnableDisableButton) buttons.get(32)).isEnable();
		game.axe = ((EnableDisableButton) buttons.get(41)).isEnable();
		game.shield = ((EnableDisableButton) buttons.get(50)).isEnable();
		game.pearl = ((EnableDisableButton) buttons.get(25)).isEnable();
		game.bow = ((EnableDisableButton) buttons.get(34)).isEnable();
		game.smoke = ((EnableDisableButton) buttons.get(52)).isEnable();
		
		if (((MultipleValueMap) buttons.get(20)).getValue() == null)	{
			
			game.maps.clear();
			int pos = 27;
			
			for (int i = 0; i < Focus.maps.size(); i++)	{
				
				pos++;
				if (pos == 31)	{pos += 6;}
				if (pos == 40)	{pos += 6;}
				
				if (((EnableDisableButton) buttons.get(pos)).isEnable())	{game.maps.add(Focus.maps.get(i));}
				
			}
			
		} else {
			
			game.maps.clear();
			game.maps.add(((MultipleValueMap) buttons.get(20)).getValue());
			
		}
	}

	@Override
	public void update() {
		
		Game game = pl.getGame();
		MultipleValueGmOption gm = (MultipleValueGmOption) buttons.get(0);
		MultipleValueGmOption v = (MultipleValueGmOption) buttons.get(1);
		MultipleValueInteger limit = (MultipleValueInteger) buttons.get(2);
		
		if (gm.getValue() == GmOption.ROUNDUP)	{
			
			buttons.put(1, new MultipleValueGmOption(Material.BOOKSHELF, Focus.getLanguage().parameters_victories, Arrays.asList(""), Arrays.asList(GmOption.VICTORIES)));
			
			MultipleValueInteger newLimit = new MultipleValueInteger(Material.BOOK, Focus.getLanguage().parameters_roundsLimit, Arrays.asList(""), Arrays.asList(Parameters.RWLIMIT_VERYLOW, Parameters.RWLIMIT_LOW, Parameters.RWLIMIT_NORMAL, Parameters.RWLIMIT_HIGH, Parameters.RWLIMIT_EXTREME));
			if (!limit.equals(newLimit))	{
				buttons.put(2, newLimit);
			}
			
		} else if (gm.getValue() == GmOption.DEATHMATCH)	{
			
			if (v.getValue() == GmOption.VICTORIES)	{
				buttons.put(1, new MultipleValueGmOption(Material.BOOKSHELF, Focus.getLanguage().parameters_victories, Arrays.asList(""), Arrays.asList(GmOption.ROUNDS, GmOption.KILLS)));
				buttons.put(2, new MultipleValueInteger(Material.BOOK, Focus.getLanguage().parameters_killsLimit, Arrays.asList(""), Arrays.asList(Parameters.KWLIMIT_VERYLOW, Parameters.KWLIMIT_LOW, Parameters.KWLIMIT_NORMAL, Parameters.KWLIMIT_HIGH, Parameters.KWLIMIT_EXTREME)));
			}
			
			if (v.getValue() == GmOption.KILLS)	{
				
				MultipleValueInteger newLimit = new MultipleValueInteger(Material.BOOK, Focus.getLanguage().parameters_killsLimit, Arrays.asList(""), Arrays.asList(Parameters.KWLIMIT_VERYLOW, Parameters.KWLIMIT_LOW, Parameters.KWLIMIT_NORMAL, Parameters.KWLIMIT_HIGH, Parameters.KWLIMIT_EXTREME));
				if (!limit.equals(newLimit))	{
					buttons.put(2, newLimit);
				}
				
			} else if (v.getValue() == GmOption.ROUNDS)	{
				
				MultipleValueInteger newLimit = new MultipleValueInteger(Material.BOOK, Focus.getLanguage().parameters_maxRoundLimit, Arrays.asList(""), Arrays.asList(Parameters.MR_VERYLOW, Parameters.MR_LOW, Parameters.MR_NORMAL, Parameters.MR_HIGH, Parameters.MR_EXTREME));
				if (!limit.equals(newLimit))	{
					buttons.put(2, newLimit);
				}
				
			}
		}		
		
		read();
		game.updateDisplayer();
		
		for (int i : buttons.keySet())	{
			current.setItem(i, buttons.get(i).getItem());
		}
	}

	public void rightClickSlot(int slot, Focus pl) {
		if (buttons.containsKey(slot))	{
			buttons.put(slot, buttons.get(slot).rightClick());
		}
		update();
	}
	public void leftClickSlot(int slot, Focus pl) {
		if (buttons.containsKey(slot))	{
			buttons.put(slot, buttons.get(slot).leftClick());
		}
		update();
	}
}
