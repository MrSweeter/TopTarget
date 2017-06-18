package com.mrsweeter.focus.Game.Event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Players.FocusPlayer;
import com.mrsweeter.focus.Players.Utils.EffectUtils;

public class SmokeEffect extends BukkitRunnable	{
	
	private Location loc;
	private int time;
	private FocusPlayer player;
	private int level = 0;
	private int timeEffect = 0;
	private int radius = 0;
	private boolean apply = false;
	
	public SmokeEffect(Focus main, Location locItem, Player p) {
		loc = locItem;
		time = 0;
		player = Focus.playertoFocusPlayer(p);
		ItemStack smoke = player.getStuff().get("smoke");
		if (smoke.getType() == Material.SNOW_BALL)	{
			ItemMeta meta = smoke.getItemMeta();
			List<String> lores = meta.getLore();
			int pos;
			if ((pos = EffectUtils.getMatchingIndex(lores, "§7SMOKE:\\d+")) > -1)	{
				
				level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
				if (level % 2 == 0)	{
					timeEffect = 20 * (level-1);
				} else {
					timeEffect = 20 * level;
				}
				
				if (timeEffect < 60)	{timeEffect = 60;} else {timeEffect += 30;}
				
				radius = level + 2;
				if (radius > 4)	{radius = 4;}
				apply = true;
				
			}
		}
	}

	@Override
	public void run() {
		if (time > timeEffect)	{this.cancel();}
		
		if (apply)	{
			
			loc.getWorld().spawnParticle(Particle.CLOUD, loc, 50, radius, radius, radius, 0.01);
			
			for (Entity e : getNearbyEntities(loc, radius))	{
				if (e instanceof Player)	{
					((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, timeEffect, 1, true, false), false);
				}
			}
				
		} else {
			this.cancel();
		}
		time++;
	}
	
	private List<Entity> getNearbyEntities(Location loc, double radius)	{
		List<Entity> entities = new ArrayList<Entity>();
		for (Entity e : loc.getWorld().getEntities())	{
			if (loc.distance(e.getLocation()) <= radius)	{
				entities.add(e);
			}
		}
		return entities;
	}
}
