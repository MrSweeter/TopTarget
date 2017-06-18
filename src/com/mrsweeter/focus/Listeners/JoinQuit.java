package com.mrsweeter.focus.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.StateGame;
import com.mrsweeter.focus.Players.FocusPlayer;

public class JoinQuit implements Listener	{
	
	Focus pl;
	
	public JoinQuit() {
		pl = Focus.getInstance();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)	{
		
		Player p = event .getPlayer();
		p.setScoreboard(pl.getTeams().getSidebarScoreboard());
		
		if (pl.getGame().isGameState(StateGame.LOBBY))	{
			p.setGameMode(GameMode.ADVENTURE);
			if (p.isOp())	{
				p.setGameMode(GameMode.CREATIVE);
			} else	{
				p.setGameMode(GameMode.ADVENTURE);
				p.getInventory().clear();
			}
		} else {
			p.setGameMode(GameMode.SPECTATOR);
		}
		
		p.setFireTicks(0);
		p.teleport(Focus.getSpawn());
		p.setBedSpawnLocation(Focus.getSpawn(), true);
		
		p.getInventory().clear();
		p.setHealth(20);
		
		p.sendTitle("§6§l--- Focus §6---", "", 20, 60, 20);
		
		for (PotionEffect potion : p.getActivePotionEffects())	{
			p.removePotionEffect(potion.getType());
		}
		for (Player player : Bukkit.getOnlinePlayers())	{
			if (pl.getGame().isDisplayHP())	{
				player.setScoreboard(pl.getTeams().getSidebarScoreboard());
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)	{
		
		Game game = pl.getGame();
		FocusPlayer p = Focus.playertoFocusPlayer(event.getPlayer());
		
		if (game.getPlayers().contains(p))	{
			
			ItemStack quit = new ItemStack(Material.BARRIER);
			ItemMeta meta = quit.getItemMeta();
			meta.setDisplayName("§c§lQuit game");
			quit.setItemMeta(meta);
			
			game.getColorPicker().chooseColor(event.getPlayer(), quit);
			
			game.removeFocusPlayer(event.getPlayer());
			
		}
		
		if (!game.isGameState(StateGame.LOBBY))	{
			Death.checkEndRound(pl, null);
			Death.checkEndGame(pl, null);
		}
	}
}
