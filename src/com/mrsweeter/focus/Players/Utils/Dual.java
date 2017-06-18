package com.mrsweeter.focus.Players.Utils;

import org.bukkit.inventory.ItemStack;

public class Dual {
	
	private ItemStack item;
	private int cost;
	
	public Dual(ItemStack i, int c)	{
		setItem(i);
		setCost(c);
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
