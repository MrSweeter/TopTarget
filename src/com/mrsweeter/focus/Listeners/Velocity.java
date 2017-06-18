package com.mrsweeter.focus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Game.StateGame;

public class Velocity implements Listener	{
	
	private Focus pl;
	
	public Velocity() {
		pl = Focus.getInstance();
	}
	
	@EventHandler
	public void onKnockBack(PlayerVelocityEvent event)	{
		
		if (pl.getGame().isGameState(StateGame.STUFF) || pl.getGame().isGameState(StateGame.LOBBY))	{
			event.setCancelled(true);
		}		
	}
}
