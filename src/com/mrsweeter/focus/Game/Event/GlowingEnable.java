package com.mrsweeter.focus.Game.Event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.StateGame;
import com.mrsweeter.focus.Players.FocusPlayer;

public class GlowingEnable extends BukkitRunnable	{
	
	private Focus pl;
	private boolean broadcast;
	
	public GlowingEnable()	{
		pl = Focus.getInstance();
		broadcast = true;
	}
	
	@Override
	public void run() {
		Game game = pl.getGame();
		
		if (game.isGameState(StateGame.INGAME))	{
			
			List<FocusPlayer> players = game.getPlayers();
			Player player;
			String top = "";
			
			List<FocusPlayer> pList = new ArrayList<>();
			pList.addAll(players);
			FocusPlayer p;
			for (int i = 0; i < 3; i++)	{
				p = game.selectPlayerMax(pList);
				if (p != null)	{
					top += p.getPlayer().getDisplayName() + " | ";
					p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 9999999, 0, true, false), false);
					pList.remove(p);
				}
			}
			for (FocusPlayer fP : pList)	{
				fP.getPlayer().removePotionEffect(PotionEffectType.GLOWING);
			}
			top = top.substring(0, top.lastIndexOf(" | "));
			
			if (broadcast)	{
				for (FocusPlayer fP : players)	{
					player = fP.getPlayer();
					player.sendTitle(Focus.getLanguage().gameplay_playerGlowing, "§c" + top, 20, 60, 20);
				}
				broadcast = false;
			}
			
		} else {
			this.cancel();
		}
	}

}
