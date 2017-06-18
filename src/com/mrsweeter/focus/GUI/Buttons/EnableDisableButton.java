package com.mrsweeter.focus.GUI.Buttons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnableDisableButton implements MsButton	{
	
	private String name;
	private boolean state;
	private ItemStack enable;
	private ItemStack disable;
	private ItemStack item;

	public EnableDisableButton(Material enableMat, Material disableMat, String nameItem, List<String> list) {
		
		state = true;
		name = nameItem + " ";
		
		enable = new ItemStack(enableMat);
		ItemMeta meta = enable.getItemMeta();
		meta.setDisplayName(name + "§aenable");
		meta.setLore(list);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		enable.setItemMeta(meta);
		
		disable = new ItemStack(disableMat);
		meta = disable.getItemMeta();
		meta.setDisplayName(name + "§cdisable");
		meta.setLore(list);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		disable.setItemMeta(meta);
		
		item = new ItemStack(enable);
		
	}

	@Override
	public EnableDisableButton rightClick() {
		change();
		return this;
	}

	@Override
	public EnableDisableButton leftClick() {
		change();
		return this;
	}
	
	private void change()	{
		
		if (state)	{
			state = false;
			item = new ItemStack(disable);
		} else {
			state = true;
			item = new ItemStack(enable);
		}
	}
	
	@Override
	public ItemStack getItem() {
		return item;
	}
	
	public boolean isEnable()	{
		return state;
	}
}
