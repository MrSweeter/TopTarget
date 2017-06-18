package com.mrsweeter.focus;

import java.lang.reflect.Field;

import org.bukkit.configuration.ConfigurationSection;

import com.mrsweeter.dreamAPI.Configuration.PluginConfiguration;

public class Language {
	
	public String prefix = "§6§lFocus";
	public String cmd_noPermission = "";
	public String cmd_onlyPlayer = "";
	public String cmd_reload = "";
	public String cmd_game_notEnoughPlayer = "";
	public String cmd_game_noColorChoiceIG = "";
	public String cmd_game_noOptionIG = "";
	public String cmd_game_noStartGameIG = "";
	public String cmd_game_resetGameTitle = "";
	public String cmd_game_resetGameSubTitleConsole = "";
	public String cmd_game_resetGameSubTitlePlayer = "";
	public String cmd_game_resetGameChat = "";
	public String cmd_game_notInGame = "";
	public String cmd_game_noStuffChoiceOG = "";
	public String cmd_game_ = "";
	public String gameplay_deathmatchTitle = "";
	public String gameplay_deathmatchSubTitle = "";
	public String gameplay_playerGlowing = "";
	public String gameplay_round_startTime = "";
	public String gameplay_round_openGUI = "";
	public String gameplay_sidebar_ip = "";
	public String gameplay_sidebar_gamemode = "";
	public String gameplay_sidebar_victories = "";
	public String gameplay_sidebar_startPoint = "";
	public String gameplay_sidebar_killPoint = "";
	public String gameplay_sidebar_roundPoint = "";
	public String gameplay_sidebar_noBody = "";
	public String gameplay_endRound_win = "";
	public String gameplay_endGame_winTitle = "";
	public String gameplay_endGame_winner = "";
	public String gameplay_endGame_bestKill = "";
	public String gameplay_endGame_bestVictim = "";
	public String gameplay_endGame_roundPlayed = "";
	public String gameplay_endGame_playerStats_kill = "";
	public String gameplay_endGame_playerStats_death = "";
	public String gameplay_endGame_playerStats_victories = "";
	public String gui_gameConfiguration = "";
	public String gui_colorPicker = "";
	public String gui_stuffPicker = "";
	public String gui_gameFull = "";
	public String parameters_gamemode = "";
	public String parameters_victories = "";
	public String parameters_killsLimit = "";
	public String parameters_roundsLimit = "";
	public String parameters_maxRoundLimit = "";
	public String parameters_life = "";
	public String parameters_startingP = "";
	public String parameters_killP = "";
	public String parameters_roundP = "";
	public String parameters_stuff_helmet = "";
	public String parameters_stuff_chestplate = "";
	public String parameters_stuff_leggings = "";
	public String parameters_stuff_boots = "";
	public String parameters_stuff_sword = "";
	public String parameters_stuff_axe = "";
	public String parameters_stuff_shield = "";
	public String parameters_stuff_pearl = "";
	public String parameters_stuff_bow = "";
	public String parameters_stuff_smoke = "";
	public String gameplay_round_round = "";
	
	public Language(PluginConfiguration language, String id)	{
		prefix = language.getString("plugin-prefix");
		ConfigurationSection section = language.getConfigurationSection(id);
		
		if (section != null)	{
		
			cmd_noPermission = section.getString("commands.noPermission");
			cmd_onlyPlayer = section.getString("commands.onlyPlayer");
			cmd_reload = section.getString("commands.reload");
			cmd_game_notEnoughPlayer = section.getString("commands.game.notEnoughPlayer");
			cmd_game_noColorChoiceIG = section.getString("commands.game.noColorChoiceIG");
			cmd_game_noOptionIG = section.getString("commands.game.noOptionIG");
			cmd_game_noStartGameIG = section.getString("commands.game.noStartGameIG");
			cmd_game_resetGameTitle = section.getString("commands.game.resetGameTitle");
			cmd_game_resetGameSubTitleConsole = section.getString("commands.game.resetSubTitleConsole");
			cmd_game_resetGameSubTitlePlayer = section.getString("commands.game.resetSubTitlePlayer");
			cmd_game_resetGameChat = section.getString("commands.game.resetGameChat");
			cmd_game_notInGame = section.getString("commands.game.notInGame");
			cmd_game_noStuffChoiceOG = section.getString("commands.game.noStuffChoiceOG");
			cmd_game_ = section.getString("commands.game.");
			gameplay_deathmatchTitle = section.getString("gameplay.deathmatchTitle");
			gameplay_deathmatchSubTitle = section.getString("gameplay.deathmatchSubTitle");
			gameplay_playerGlowing = section.getString("gameplay.playerGlowing");
			gameplay_round_startTime = section.getString("gameplay.round.startTime");
			gameplay_round_openGUI = section.getString("gameplay.round.openGUI");
			gameplay_sidebar_ip = section.getString("gameplay.sidebar.ip");
			gameplay_sidebar_gamemode = section.getString("gameplay.sidebar.gamemode");
			gameplay_sidebar_victories = section.getString("gameplay.sidebar.victories");
			gameplay_sidebar_startPoint = section.getString("gameplay.sidebar.startPoint");
			gameplay_sidebar_killPoint = section.getString("gameplay.sidebar.killPoint");
			gameplay_sidebar_roundPoint = section.getString("gameplay.sidebar.roundPoint");
			gameplay_sidebar_noBody = section.getString("gameplay.sidebar.noBody");
			gameplay_endRound_win = section.getString("gameplay.endRound.win");
			gameplay_endGame_winTitle = section.getString("gameplay.endGame.winTitle");
			gameplay_endGame_winner = section.getString("gameplay.endGame.winner");
			gameplay_endGame_bestKill = section.getString("gameplay.endGame.bestKill");
			gameplay_endGame_bestVictim = section.getString("gameplay.endGame.bestVictim");
			gameplay_endGame_roundPlayed = section.getString("gameplay.endGame.roundPlayed");
			gameplay_endGame_playerStats_kill = section.getString("gameplay.endGame.playerStats.kill");
			gameplay_endGame_playerStats_death = section.getString("gameplay.endGame.playerStats.death");
			gameplay_endGame_playerStats_victories = section.getString("gameplay.endGame.playerStats.victories");
			gui_gameConfiguration = section.getString("gui.gameConfiguration");
			gui_colorPicker = section.getString("gui.colorPicker");
			gui_stuffPicker = section.getString("gui.stuffPicker");
			gui_gameFull = section.getString("gui.gameFull");
			parameters_gamemode = section.getString("parameters.gamemode");
			parameters_victories = section.getString("parameters.victories");
			parameters_killsLimit = section.getString("parameters.killsLimit");
			parameters_roundsLimit = section.getString("parameters.roundsLimit");
			parameters_maxRoundLimit = section.getString("parameters.maxRoundLimit");
			parameters_life = section.getString("parameters.life");
			parameters_startingP = section.getString("parameters.startingP");
			parameters_killP = section.getString("parameters.killP");
			parameters_roundP = section.getString("parameters.roundP");
			parameters_stuff_helmet = section.getString("parameters.stuff.helmet");
			parameters_stuff_chestplate = section.getString("parameters.stuff.chestplate");
			parameters_stuff_leggings = section.getString("parameters.stuff.leggings");
			parameters_stuff_boots = section.getString("parameters.stuff.boots");
			parameters_stuff_sword = section.getString("parameters.stuff.sword");
			parameters_stuff_axe = section.getString("parameters.stuff.axe");
			parameters_stuff_shield = section.getString("parameters.stuff.shield");
			parameters_stuff_pearl = section.getString("parameters.stuff.pearl");
			parameters_stuff_bow = section.getString("parameters.stuff.bow");
			parameters_stuff_smoke = section.getString("parameters.stuff.smoke");
			gameplay_round_round = section.getString("gameplay.round.round");
			
		}
		
		applyConverter();
	}
	
	private void applyConverter()	{
		Class<?> c;
		String str = "";
		for (Field f : this.getClass().getFields())	{
			c = f.getType();
			
			if (c.equals(String.class))	{
				
				try {
					str = (String) f.get(this);
					if (str != null)	{
						str = str.replace("{PREFIX}", prefix);
						str = str.replace("&", "§");
						
						f.set(this, str);
					} else {
						System.out.println("Error: " + f.toString());
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					System.out.println(f.getName() + "doesn't convert value, please verify the lang.yml"); 
				}
			}
		}
	}
}
