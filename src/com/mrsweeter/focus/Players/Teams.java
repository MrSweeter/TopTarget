package com.mrsweeter.focus.Players;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.mrsweeter.dreamAPI.Scoreboard.SidebarDisplayer;

public class Teams {
	
	public SidebarDisplayer siderbar;
	public Objective life;
	public Map<String , Team> teams;
	private Team darkRed, red, gold, yellow, darkGreen, green, aqua, darkAqua, darkBlue, blue, lightPurple, darkPurple, white, gray, darkGray, black;
	
	public Team getTeam(String color)	{
		if (teams.containsKey(color))	{return teams.get(color);} else {return null;}
	}
	
	public void unregister() {
		life.unregister();
		siderbar.getObjective().unregister();
	}
	
	public Teams()	{
		
		siderbar = new SidebarDisplayer("§6§l--- Focus ---");
		siderbar.setLineText("§eGamemode: Deathmatch", 2);
		siderbar.setLineText("§eVictory: Victories", 3);
		siderbar.setLineText("§eStart point: 50", 4);
		siderbar.setLineText("§eKill point: 100", 5);
		siderbar.setLineText("§eRound point: 100", 6);
		siderbar.setLineText("§e----------------", 8);
		siderbar.setLineText("§7nobody", 9);
		siderbar.setLineText("§7nobody ", 10);
		siderbar.setLineText("§7nobody  ", 11);
		siderbar.setLineText("§e---------------- ", 12);
		siderbar.setLineText("§eRound: ...", 13);
		siderbar.setLineText("§eip.du.serveur.com", 15);
		
		Scoreboard scoreboard = siderbar.getScoreboard();

		if (scoreboard.getObjective("life") != null)	{scoreboard.getObjective("life").unregister();}
		life = scoreboard.registerNewObjective("life", "health");
		
		life.setDisplaySlot(DisplaySlot.BELOW_NAME);
		life.setDisplayName("§4♥");
		
		if (scoreboard.getTeams() != null)	{
			for (Team t : scoreboard.getTeams())	{
				t.unregister();
			}
		}
		teams = new HashMap<>();
		darkRed = scoreboard.registerNewTeam("darkred");
		darkRed.setPrefix("§4");
		teams.put("dark_red", darkRed);
		red = scoreboard.registerNewTeam("red");
		red.setPrefix("§c");
		teams.put("red", red);
		gold = scoreboard.registerNewTeam("gold");
		gold.setPrefix("§6");
		teams.put("gold", gold);
		yellow = scoreboard.registerNewTeam("yellow");
		yellow.setPrefix("§e");
		teams.put("yellow", yellow);
		darkGreen = scoreboard.registerNewTeam("darkGreen");
		darkGreen.setPrefix("§2");
		teams.put("dark_green", darkGreen);
		green = scoreboard.registerNewTeam("green");
		green.setPrefix("§a");
		teams.put("green", green);
		aqua = scoreboard.registerNewTeam("aqua");
		aqua.setPrefix("§b");
		teams.put("aqua", aqua);
		darkAqua = scoreboard.registerNewTeam("darkAqua");
		darkAqua.setPrefix("§3");
		teams.put("dark_aqua", darkAqua);
		darkBlue = scoreboard.registerNewTeam("darkBlue");
		darkBlue.setPrefix("§1");
		teams.put("dark_blue", darkBlue);
		blue = scoreboard.registerNewTeam("blue");
		blue.setPrefix("§9");
		teams.put("blue", blue);
		lightPurple = scoreboard.registerNewTeam("lightPurple");
		lightPurple.setPrefix("§d");
		teams.put("light_purple", lightPurple);
		darkPurple = scoreboard.registerNewTeam("darkPurple");
		darkPurple.setPrefix("§5");
		teams.put("dark_purple", darkPurple);
		white = scoreboard.registerNewTeam("white");
		white.setPrefix("§f");
		teams.put("white", white);
		gray = scoreboard.registerNewTeam("gray");
		gray.setPrefix("§7");
		teams.put("gray", gray);
		darkGray = scoreboard.registerNewTeam("darkGray");
		darkGray.setPrefix("§8");
		teams.put("dark_gray", darkGray);
		black = scoreboard.registerNewTeam("black");
		black.setPrefix("§0");
		teams.put("black", black);
	}
	
	public Scoreboard getSidebarScoreboard()	{
		return siderbar.getScoreboard();
	}
}
