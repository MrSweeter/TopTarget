package com.mrsweeter.focus.GUI.Buttons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mrsweeter.focus.WorldManager.Map;

public class MultipleValueMap implements MsButton	{
	
	private ItemStack item;
	private String name;
	private String defValueString;
	private List<Map> values;
	private Map currentValue;
	
	public MultipleValueMap(Material mat, String nameItem, List<String> list, List<Map> options, String def)	{
		
		defValueString = def;
		values = new ArrayList<>();
		values.addAll(options);
		name = nameItem + " ";
		currentValue = null;
		
		item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name + defValueString);
		meta.setLore(list);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);
		
	}
	
	@Override
	public MultipleValueMap rightClick() {
		
		int index;
		ItemMeta meta = item.getItemMeta();
		
		if (currentValue == null)	{
			
			currentValue = values.get(0);
			
		} else {
			
			index = values.indexOf(currentValue);
			if (index >= values.size()-1)	{currentValue = null;}
			else {currentValue = values.get(index+1);}
			
		}
		
		if (currentValue == null)	{meta.setDisplayName(name + defValueString);}
		else {meta.setDisplayName(name + currentValue.getName());}
		
		item.setItemMeta(meta);
		return this;
	}

	@Override
	public MultipleValueMap leftClick() {
		
		int index;
		ItemMeta meta = item.getItemMeta();
		
		if (currentValue == null)	{
			
			currentValue = values.get(values.size()-1);
			
		} else {
			
			index = values.indexOf(currentValue);
			if (index <= 0)	{currentValue = null;}
			else {currentValue = values.get(index-1);}
			
		}
		
		if (currentValue == null)	{meta.setDisplayName(name + defValueString);}
		else {meta.setDisplayName(name + currentValue.getName());}
		
		item.setItemMeta(meta);
		return this;
	}
	
	public boolean equals(Object temp)	{
		
		if (!(temp instanceof MultipleValueMap))	{return false;}
		if (item.getType() != ((MultipleValueMap) temp).item.getType())	{return false;}
		if (!name.equals(((MultipleValueMap) temp).name))	{return false;}
		
		return true;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public Map getValue()	{
		return currentValue;
	}
	
	public List<Map> getValues()	{
		return values;
	}
	
}
