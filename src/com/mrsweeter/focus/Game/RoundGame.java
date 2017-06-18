package com.mrsweeter.focus.Game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreamAPI.Messages.ActionBar;
import com.mrsweeter.dreamAPI.Messages.FormatedChatComponent;
import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.Event.CreeperSpawn;
import com.mrsweeter.focus.Game.Event.GlowingEnable;
import com.mrsweeter.focus.Players.FocusPlayer;
import com.mrsweeter.focus.WorldManager.Map;

public class RoundGame {
	
	private Map currentMap;
	private Game currentGame;
	
	public RoundGame(Map map, Focus pl)	{
		currentMap = map;
		currentGame = pl.getGame();
		Player p;
		for (int i = 0; i < currentGame.getPlayers().size(); i++)	{
			p = currentGame.getPlayers().get(i).getPlayer();
			p.teleport(Focus.getLobbyStuff().get(i));
			p.setGameMode(GameMode.ADVENTURE);
			p.setHealth(20);
			p.setFireTicks(0);
			p.getInventory().clear();
			currentGame.getPlayers().get(i).openStuff(pl);
			p.sendMessage(Focus.getLanguage().gameplay_round_openGUI);
		}
		new BukkitRunnable() {
			ActionBar ab =  new ActionBar(new FormatedChatComponent(Focus.getLanguage().gameplay_round_startTime.replace("{TIME}", "" + Focus.roundDelay)));
			int i = 0;
			@Override
			public void run() {
				if (i < Focus.roundDelay)	{
					ab = new ActionBar(new FormatedChatComponent(Focus.getLanguage().gameplay_round_startTime.replace("{TIME}", "" + (Focus.roundDelay-i))));
					i++;
					ab.sendActionBar();
				} else {
					start(pl);
					this.cancel();
				}
			}
		}.runTaskTimer(pl, 0, 20);
	}
	
	public boolean start(Focus pl)	{
		
		if (currentGame.getPlayers().size() < 2)	{return false;}
		
		for (Player p : Bukkit.getOnlinePlayers())	{
			p.teleport(currentMap.getLocation(0), TeleportCause.PLUGIN);
			p.setGameMode(GameMode.SPECTATOR);
			if (!currentGame.isVictories(GmOption.KILLS))	{
				p.sendMessage(Focus.getLanguage().gameplay_round_round + currentGame.getNbreRound());
			}
			p.sendMessage(currentMap.toString());
		}
		
		currentGame.setState(StateGame.INGAME);
		
		for (FocusPlayer p : currentGame.getPlayers())	{
			Player player = p.getPlayer();
			Location loc = currentMap.getLocation();
			
			player.teleport(loc, TeleportCause.PLUGIN);
			player.setGameMode(GameMode.ADVENTURE);
			player.setHealth(20);
			player.setFireTicks(0);
			player.setBedSpawnLocation(loc, true);
			
			for (PotionEffect potion : player.getActivePotionEffects())	{
				player.removePotionEffect(potion.getType());
			}
			
//			loadEffect(player);
			player.getInventory().clear();
			p.loadStuffForGame(currentGame);
			player.setItemOnCursor(new ItemStack(Material.AIR));
//			Stuff.loadStuffAllowed(fg, player);
			player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 3, 10, false, false));
//			if (fg.getVictories().equals("Rounds"))	{
//				player.sendMessage("§6Round: §a" + (fg.getNbreRound()+1));
//			}
		}
		BukkitRunnable creeper = new CreeperSpawn();
		creeper.runTaskTimer(pl, 12000+600*currentGame.getPlayers().size(), 600);
		BukkitRunnable glow = new GlowingEnable();
		glow.runTaskTimer(pl, 12000, 20);
		
		return true;
	}	
}
