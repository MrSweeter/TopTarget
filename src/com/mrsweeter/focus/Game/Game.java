package com.mrsweeter.focus.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.GUI.ColorPicker;
import com.mrsweeter.focus.GUI.ParametersGUI;
import com.mrsweeter.focus.Players.FocusPlayer;
import com.mrsweeter.focus.WorldManager.Map;

public class Game {
	
	private ColorPicker colorPicker;
	private ParametersGUI param;
	private int nbreRound;
	private boolean displayHP;
	private boolean loaded;
	
	public GmOption gamemode;
	public int killPoint;
	public int limitWin;
	public int maxRound;
	public int roundPoint;
	public int startPoint;
	public GmOption victories;
	
	public boolean helmet = true;
	public boolean chestplate = true;
	public boolean leggings = true;
	public boolean boots = true;
	public boolean sword = true;
	public boolean axe = true;
	public boolean shield = true;
	public boolean pearl = true;
	public boolean bow = true;
	public boolean smoke = true;
	
	private RoundGame round;
	private StateGame state;
	private List<FocusPlayer> players;
	private List<FocusPlayer> alives;
	public List<Map> maps = new ArrayList<>();
	private List<Map> availableMaps = new ArrayList<>();
	private Focus pl;
	private boolean load;
	
	public Game()	{
		pl = Focus.getInstance();
		colorPicker = new ColorPicker();
		setNbreRound(0);
		setDisplayHP(true);
		setLoaded(false);
		
		gamemode = GmOption.DEATHMATCH;
		killPoint = Parameters.KP_NORMAL;
		limitWin = Parameters.KWLIMIT_NORMAL;
		maxRound = Parameters.MR_NORMAL;
		roundPoint = Parameters.RP_NORMAL;
		startPoint = Parameters.SP_NORMAL;
		victories = GmOption.KILLS;
		
		maps = Focus.maps;
		setState(StateGame.LOBBY);
		setPlayers(new ArrayList<>());
		alives = new ArrayList<>();
	}
	
	public void start()	{
		if (maps.size() == 0)	{
			Bukkit.broadcastMessage("§cNo specific map(s) set, all will be loaded");
			maps = Focus.maps;
		}
		if (availableMaps.size() == 0)	{availableMaps.addAll(maps);}
		availableMaps.remove(null);
		Map m = Map.randomMap(availableMaps);
		if (!load)	{
			for (FocusPlayer p : players)	{
				
				p.reset();
				p.addPoint(startPoint);
				
			}
			load = true;
			nbreRound = 1;
		} else {nbreRound += 1;}
		setNewRound(new RoundGame(m, pl));
		availableMaps.remove(m);
		state = StateGame.STUFF;
		updateDisplayer();
	}
	
	public void updateDisplayer()	{
		
		pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_sidebar_gamemode + gamemode, 2);
		pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_sidebar_victories + victories + " (" + limitWin + ")", 3);
		pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_sidebar_startPoint + startPoint, 4);
		pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_sidebar_killPoint + killPoint, 5);
		pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_sidebar_roundPoint + roundPoint, 6);
		if (victories == GmOption.VICTORIES)	{
			pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_round_round + nbreRound, 13);
		} else {
			pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_round_round + "...", 13);
		}
		
		List<FocusPlayer> pList = new ArrayList<>();
		pList.addAll(players);
		FocusPlayer p;
		for (int i = 9; i < 12; i++)	{
			p = selectPlayerMax(pList);
			if (p != null)	{
				pl.getTeams().siderbar.changeLineText("§" + p.getCodeColor() + p.getPlayer().getName() + " - " + p.getVictories(), i);
				pList.remove(p);
			} else {
				pl.getTeams().siderbar.changeLineText(Focus.getLanguage().gameplay_sidebar_noBody, i);
			}
		}
	}
	
	public void end(String winner) {
		
		FocusPlayer bestKiller = selectMaxKill();
		FocusPlayer bestVictim = selectMaxDeath();
		
		String stats = "";
		for (FocusPlayer fP : players)	{
			
			stats += "§e◆ " + fP.getPlayer().getDisplayName();
			stats += " | " + Focus.getLanguage().gameplay_endGame_playerStats_kill + fP.getKill();
			stats += " | " + Focus.getLanguage().gameplay_endGame_playerStats_death + fP.getDeath();
			stats += " | " + Focus.getLanguage().gameplay_endGame_playerStats_victories + fP.getVictories();
			stats += " |\n";
			
		}
		
		for (Player p : Bukkit.getOnlinePlayers())	{
			
			p.sendMessage("§6---------- Focus game -----------");
			p.sendMessage(Focus.getLanguage().gameplay_endGame_winner + winner);
			p.sendMessage(Focus.getLanguage().gameplay_endGame_bestKill + bestKiller.getPlayer().getDisplayName() + " (" + bestKiller.getKill() + ")");
			p.sendMessage(Focus.getLanguage().gameplay_endGame_bestVictim + bestVictim.getPlayer().getDisplayName() + " (" + bestVictim.getDeath() + ")");
			p.sendMessage("");
			p.sendMessage(Focus.getLanguage().gameplay_endGame_roundPlayed + nbreRound);
			p.sendMessage("§6---------- Focus players -----------");
			p.sendMessage(stats);
		}
		
	}
	
	private FocusPlayer selectMaxKill()	{
		int max = -1;
		FocusPlayer result = null;
		for (FocusPlayer p : players)	{
			if (p.getKill() > max)	{
				max = p.getKill();
				result = p;
			}
		}
		return result;
	}
	
	private FocusPlayer selectMaxDeath()	{
		int max = -1;
		FocusPlayer result = null;
		for (FocusPlayer p : players)	{
			if (p.getDeath() > max)	{
				max = p.getDeath();
				result = p;
			}
		}
		return result;
	}
	
	public FocusPlayer selectPlayerMax(List<FocusPlayer> list)	{
		int max = -1;
		FocusPlayer result = null;
		for (FocusPlayer p : list)	{
			if (p.getVictories() > max)	{
				max = p.getVictories();
				result = p;
			}
		}
		return result;
	}
	
	public int getKillPoint()	{
		return killPoint;
	}
	public int getRoundPoint()	{
		return roundPoint;
	}
	
	public List<Map> getMaps()	{
		return maps;
	}
	public ColorPicker getColorPicker() {
		return colorPicker;
	}
	public ParametersGUI getParam() {
		return param;
	}
	public int getNbreRound() {
		return nbreRound;
	}
	public void setNbreRound(int nbreRound) {
		this.nbreRound = nbreRound;
	}
	public boolean isDisplayHP() {
		return displayHP;
	}
	public void setDisplayHP(boolean displayHP) {
		this.displayHP = displayHP;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public boolean isGameState(StateGame g) {
		return state == g;
	}
	public void setState(StateGame state) {
		this.state = state;
	}
	public List<FocusPlayer> getPlayers() {
		return players;
	}
	public List<FocusPlayer> getAlivePlayers() {
		return alives;
	}
	public void killPlayers(FocusPlayer p) {
		alives.remove(p);
	}
	public void setPlayers(List<FocusPlayer> players) {
		this.players = players;
	}
	public void addFocusPlayer(FocusPlayer focusPlayer) {
		players.add(focusPlayer);
		alives.add(focusPlayer);
	}
	public void fillAlivePlayer()	{
		alives.clear();
		alives.addAll(players);
	}
	public RoundGame getRound() {
		return round;
	}
	public void setNewRound(RoundGame round) {
		this.round = round;
	}

	public void removeFocusPlayer(Player player) {
		Iterator<FocusPlayer> it = players.iterator();
		FocusPlayer p;
		while (it.hasNext())	{
			p = it.next();
			if (p.getPlayer() == player)	{
				it.remove();
			}
		}
	}

	public boolean isVictories(GmOption v) {
		return victories == v;
	}
}
