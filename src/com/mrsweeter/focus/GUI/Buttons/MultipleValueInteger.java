package com.mrsweeter.focus.GUI.Buttons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MultipleValueInteger implements MsButton	{
	
	private ItemStack item;
	private List<Integer> values;
	private int currentValue;
	private String name;
	
	public MultipleValueInteger(Material mat, String nameItem, List<String> list, List<Integer> options) {
		
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
	public MultipleValueInteger rightClick() {
		
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
	public MultipleValueInteger leftClick() {
		
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
		
		if (!(temp instanceof MultipleValueInteger))	{return false;}
		if (item.getType() != ((MultipleValueInteger) temp).getItem().getType())	{return false;}
		if (!name.equals(((MultipleValueInteger) temp).name))	{return false;}
		
		return true;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}
	
	public int getValue()	{
		return currentValue;
	}
	
	public List<Integer> getValues()	{
		return values;
	}
}
