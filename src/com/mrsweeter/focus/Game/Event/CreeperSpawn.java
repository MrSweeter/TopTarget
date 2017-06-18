package com.mrsweeter.focus.Game.Event;

import java.util.List;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.StateGame;
import com.mrsweeter.focus.Players.FocusPlayer;

public class CreeperSpawn extends BukkitRunnable	{
	
	private Focus pl;
	private boolean broadcast;
	private EntityType ent = EntityType.CREEPER;
	
	public CreeperSpawn()	{
		pl = Focus.getInstance();
		broadcast = true;
	}
	
	@Override
	public void run() {
		Game game = pl.getGame();
		
		if (game.isGameState(StateGame.INGAME))	{
			
			List<FocusPlayer> players = game.getPlayers();
			Player player;
			if (broadcast)	{
				for (FocusPlayer p : players)	{
					player = p.getPlayer();
					player.sendTitle(Focus.getLanguage().gameplay_deathmatchTitle, Focus.getLanguage().gameplay_deathmatchSubTitle, 20, 60, 20);
				}
				broadcast = false;
			} else	{
				for (FocusPlayer p : players)	{
					player = p.getPlayer();
					Creeper creeper = (Creeper) player.getWorld().spawnEntity(player.getLocation(), ent);
					creeper.setPowered(true);
					creeper.setSilent(true);
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000, 5, false, false));
					creeper.setCollidable(false);
				}
			}
			
		} else {
			this.cancel();
		}
	}

}
