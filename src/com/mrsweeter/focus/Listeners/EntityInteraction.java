package com.mrsweeter.focus.Listeners;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.StateGame;
import com.mrsweeter.focus.Game.Event.SmokeEffect;
import com.mrsweeter.focus.Players.Utils.EffectUtils;

public class EntityInteraction implements Listener	{
	
	private Focus pl;

	public EntityInteraction() {
		pl = Focus.getInstance();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)	{
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		
		if (pl.getGame().isGameState(StateGame.INGAME))	{
			if (loc.getBlock().getType() == Material.GOLD_PLATE)	{
				
				Vector direction = player.getLocation().getDirection();
				direction.setY(1.2);
				player.setVelocity(direction);
			}
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event)	{
		
		Entity ent = event.getEntity();
		if (ent instanceof Player)	{
			Player p = (Player) event.getEntity();
			if (p.getFoodLevel() < 20)	{
				p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10, 255, false, false), false);
			}
		}
	}
	
	@EventHandler
	public void onSmokeLaunch(ProjectileHitEvent event)	{
		
		Block block = event.getHitBlock();
		Entity ent = event.getHitEntity();
		Entity snowball = event.getEntity();
		
		if (snowball instanceof Snowball && (block != null || ent != null))	{
			Location loc = event.getEntity().getLocation();
			Player p = (Player) event.getEntity().getShooter();
			if (pl.getGame().isGameState(StateGame.INGAME))	{
				BukkitRunnable smoke = new SmokeEffect(pl, loc, p);
				smoke.runTaskTimer(pl, 0, 1);
			}
		}
	}
	
	@EventHandler
	public void onPortalTemple(PlayerPortalEvent event)	{
//		World w = Bukkit.getWorld("msd_focus_world");
//		if (event.getCause() == TeleportCause.END_PORTAL)	{
//			event.getPlayer().teleport(new Location(w, 1400.5, 85, 600.5));
//		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onDropIG(PlayerDropItemEvent event)	{
		if (!pl.getGame().isGameState(StateGame.LOBBY) && Focus.drop)	{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickupIG(PlayerPickupItemEvent event)	{
		if (!pl.getGame().isGameState(StateGame.LOBBY) && Focus.pickup)	{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)	{
		if (!(pl.getGame().isGameState(StateGame.INGAME)) && event.getEntity().getLocation().getBlockY() > 10)	{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamageAxe(EntityDamageByEntityEvent event)	{
		Entity victimDamage = event.getEntity();
		Entity damager = event.getDamager();
		
		if (victimDamage instanceof Player && damager instanceof Player && pl.getGame().isGameState(StateGame.INGAME) && event.getCause() != DamageCause.THORNS)	{
			
			Player victim = (Player) event.getEntity();
			Player murder = (Player) event.getDamager();
			
			ItemStack weapon = murder.getInventory().getItemInMainHand();
			ItemMeta meta;
			List<String> lores;
			
			meta = weapon.getItemMeta();
			if (meta != null)	{
				lores = meta.getLore();
				int pos;
				int level;
				if (lores != null)	{
					if ((pos = EffectUtils.getMatchingIndex(lores, "§7POISON:\\d+")) != -1)	{
						level = Integer.parseInt(lores.get(pos).substring(lores.get(pos).indexOf(":")+1));
						EffectUtils.applyPoison(victim, level);
					}
				}
			}
		}
	}
}
