package com.mrsweeter.focus.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.GmOption;
import com.mrsweeter.focus.Game.StateGame;
import com.mrsweeter.focus.Players.FocusPlayer;

public class Death implements Listener	{
	
	private Focus pl;
	
	public Death() {
		pl = Focus.getInstance();
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)	{
		
		Game game = pl.getGame();
		
		Player victim = event.getEntity();
		Entity murder = victim.getKiller();
		Location loc = Focus.getSpawn();
		
		if (game.isGameState(StateGame.INGAME))	{
			
			event.setDeathMessage(null);
			
			FocusPlayer pV = Focus.playertoFocusPlayer(victim);
			game.killPlayers(pV);
			victim.setGameMode(GameMode.SPECTATOR);
			pV.addDeath(1);
			loc = victim.getLocation();
			loc.setY(loc.getY()+3);
			if (loc.getY() < -50)	{loc.setY(loc.getY()*-1 - 50);}
			else if (loc.getY() < 0)	{loc.setY(loc.getY()*-1);}
			victim.setHealth(20);
			victim.teleport(loc, TeleportCause.PLUGIN);
			victim.setBedSpawnLocation(loc, true);
			
			if (murder != null && !murder.isDead())	{
				
				if (murder instanceof Player)	{
					FocusPlayer pM = Focus.playertoFocusPlayer((Player) murder);
					
					pM.addKill(1);
					pM.addPoint(game.getKillPoint());
					
					if (game.isVictories(GmOption.KILLS) || game.isVictories(GmOption.ROUNDS))	{
						pM.addVictories(1);
					}
					checkEndRound(pl, pM);
//					checkEndGame(pl, pM);
				} else {
					checkEndRound(pl, null);
//					checkEndGame(pl, null);
				}
				
			} else {
				checkEndRound(pl, null);
//				checkEndGame(pl, null);
			}
			game.updateDisplayer();
			
		} else {		
			victim.setHealth(20);
			victim.teleport(loc, TeleportCause.PLUGIN);
			victim.setBedSpawnLocation(loc, true);
		}
		
	}

	public static void checkEndRound(Focus pl, FocusPlayer player) {
		Game game = pl.getGame();
		if (game.getAlivePlayers().size() <= 1)	{
			
			
			if (player != null)	{
				
				Bukkit.getServer().broadcastMessage(Focus.getLanguage().gameplay_endRound_win.replace("{PLAYER}", player.getPlayer().getDisplayName()));
				
				if (game.isVictories(GmOption.VICTORIES))	{
					player.addVictories(1);
				}
				player.addPoint(game.getRoundPoint());
				
			} else	{
				
				FocusPlayer murder = null;
				for (FocusPlayer p : game.getAlivePlayers())	{
					if (p.getPlayer().getGameMode() != GameMode.SPECTATOR)	{
						murder = p;
					}
				}
				
				if (game.isGameState(StateGame.INGAME) && murder != null)	{
					Bukkit.getServer().broadcastMessage(Focus.getLanguage().gameplay_endRound_win.replace("{PLAYER}", murder.getPlayer().getDisplayName()));
					if (game.isVictories(GmOption.VICTORIES))	{
						murder.addVictories(1);
					}
					murder.addPoint(game.getRoundPoint());
				}
			}
			
			game.updateDisplayer();
			if (!checkEndGame(pl, player))	{
				
				Bukkit.getScheduler().cancelAllTasks();
				for (Player pO : Bukkit.getOnlinePlayers())	{
					clearAttribute(pO);
				}
				game.fillAlivePlayer();
				for (FocusPlayer p : game.getPlayers())	{
					int addP = 6 + game.getNbreRound() + game.getNbreRound()%2;
					p.addPoint(addP);
				}
				game.start();
			}
		}
		
	}

	public static boolean checkEndGame(Focus pl, FocusPlayer player) {
		Game game = pl.getGame();
		if (game.getPlayers().size() > 1)	{
			
			if (game.isVictories(GmOption.ROUNDS) || player == null)	{player = findWinner(game);}
			
			if ((game.isVictories(GmOption.VICTORIES) && game.getNbreRound() == game.limitWin) || ((game.isVictories(GmOption.ROUNDS) || game.isVictories(GmOption.KILLS)) && player.getVictories() == game.limitWin))	{
				
				Bukkit.getScheduler().cancelAllTasks();
				
				for (Player p : Bukkit.getServer().getOnlinePlayers())	{
					p.setFireTicks(0);
					p.teleport(Focus.getSpawn());
					p.setBedSpawnLocation(Focus.getSpawn(), true);
					p.setGameMode(GameMode.ADVENTURE);
					p.getInventory().clear();
//					p.setLevel(p.getLevel()+1);
					p.setHealth(20);
					for (PotionEffect potion : p.getActivePotionEffects())	{
						p.removePotionEffect(potion.getType());
					}
					p.sendTitle(Focus.getLanguage().gameplay_endGame_winTitle.replace("{PLAYER}", player.getPlayer().getDisplayName()), "", 20, 60, 20);
					
//					BukkitRunnable block = new BlockSpawn(pl, fg.getPlayersInGame().get(p.getName()).getTeamColor());
//					block.runTaskTimer(pl, 0, 10);
					clearAttribute(p);
				}
				game.end(player.getPlayer().getDisplayName());
				pl.resetGame();
				return true;
			}
		} else	{
			
			player = findWinner(game);
			
			for (Player p : Bukkit.getServer().getOnlinePlayers())	{
				p.setFireTicks(0);
				p.teleport(Focus.getSpawn());
				p.setBedSpawnLocation(Focus.getSpawn(), true);
				if (p.isOp())	{
					p.setGameMode(GameMode.CREATIVE);
				} else {
					p.setGameMode(GameMode.ADVENTURE);
				}
				p.getInventory().clear();
//				p.setLevel(p.getLevel()+1);
				p.setHealth(20);
				for (PotionEffect potion : p.getActivePotionEffects())	{
					p.removePotionEffect(potion.getType());
				}
				p.sendTitle(Focus.getLanguage().gameplay_endGame_winTitle.replace("{PLAYER}", player.getPlayer().getDisplayName()), "", 20, 60, 20);
//				BukkitRunnable block = new BlockSpawn(pl, fg.getPlayersInGame().get(p.getName()).getTeamColor());
//				block.runTaskTimer(pl, 0, 5);
				clearAttribute(p);
			}
			game.end(player.getPlayer().getDisplayName());
			pl.resetGame();
			return true;
		}
		return false;
	}
	
	private static FocusPlayer findWinner(Game game) {
		return game.selectPlayerMax(game.getPlayers());
	}
	
	public static void clearAttribute(Player p)	{
		for (Attribute a : Attribute.values())	{
			AttributeInstance ai = p.getAttribute(a);
			if (ai != null)	{
				for (AttributeModifier am : ai.getModifiers())	{ai.removeModifier(am);}
			}
		}
	}
}

