package com.mrsweeter.focus.GUI.Buttons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mrsweeter.focus.Game.GmOption;

public class MultipleValueGmOption implements MsButton	{
	
	private ItemStack item;
	private String name;
	private List<GmOption> values;
	private GmOption currentValue;
	
	public MultipleValueGmOption(Material mat, String nameItem, List<String> list, List<GmOption> options)	{
		
		values = new ArrayList<>();
		values.addAll(options);
		name = nameItem + " ";
		currentValue = values.get(values.size()/2);
		
		item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name + currentValue);
		meta.setLore(list);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);
		
	}
	
	@Override
	public MultipleValueGmOption rightClick() {
		
		int index;
		ItemMeta meta = item.getItemMeta();
		
		index = values.indexOf(currentValue);
		if (index >= values.size()-1)	{index = -1;}
		currentValue = values.get(index+1);
		
		meta.setDisplayName(name + currentValue);
		item.setItemMeta(meta);
		return this;
	}

	@Override
	public MultipleValueGmOption leftClick() {
		
		int index;
		ItemMeta meta = item.getItemMeta();
		
		index = values.indexOf(currentValue);
		if (index <= 0)	{index = values.size();}
		currentValue = values.get(index-1);
		
		meta.setDisplayName(name + currentValue);
		item.setItemMeta(meta);
		return this;
	}
	
	public boolean equals(Object temp)	{
		
		if (!(temp instanceof MultipleValueGmOption))	{return false;}
		if (item.getType() != ((MultipleValueGmOption) temp).item.getType())	{return false;}
		if (!name.equals(((MultipleValueGmOption) temp).name))	{return false;}
		
		return true;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public GmOption getValue()	{
		return currentValue;
	}
	
	public List<GmOption> getValues()	{
		return values;
	}
}
