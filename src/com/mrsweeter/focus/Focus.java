package com.mrsweeter.focus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreamAPI.Configuration.ConfigurationCollection;
import com.mrsweeter.dreamAPI.Utils.ConsoleColor;
import com.mrsweeter.focus.Commands.MyCommand;
import com.mrsweeter.focus.GUI.ParametersGUI;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.Parameters;
import com.mrsweeter.focus.Listeners.ArrowEvent;
import com.mrsweeter.focus.Listeners.Death;
import com.mrsweeter.focus.Listeners.EntityInteraction;
import com.mrsweeter.focus.Listeners.InventoryEvent;
import com.mrsweeter.focus.Listeners.JoinQuit;
import com.mrsweeter.focus.Listeners.Velocity;
import com.mrsweeter.focus.Players.FocusPlayer;
import com.mrsweeter.focus.Players.StuffCollection;
import com.mrsweeter.focus.Players.Teams;
import com.mrsweeter.focus.WorldManager.Map;

public class Focus extends JavaPlugin	{
	
	private Logger log = Logger.getLogger("Minecraft - Focus");
	public static PluginManager pm = Bukkit.getPluginManager();
	
	private static ConfigurationCollection configs;
	private static Language language;
	private static StuffCollection stuff;
	private static Game focusGame;
	public static ParametersGUI option;
	private static Teams teams;
	private static Location spawn;
	private static List<Location> lobbyStuff = new ArrayList<>();
	public static List<Map> maps = new ArrayList<>();
	private static Focus instance;
	public static int maxPlayer = 16;
	public static int roundDelay = 15;
	public static boolean pickup = false;
	public static boolean drop = false;
	public static boolean persistentArrow = false;
	
	public void onEnable()	{
		
		instance = this;
		
		// Configuration Management
		// Creation
		configs = new ConfigurationCollection(this);
		configs.addExistingConfiguration("lang");
		configs.addFileConfiguration("stuff");
		configs.addExistingConfiguration("configuration");
		configs.addFileConfiguration("maps");
		
		// Setting
		String l = configs.getConfigByName("configuration").getString("lang");
		if (l.equals("plugin-prefix"))	{l = "english";}
		language = new Language(configs.getConfigByName("lang"), configs.getConfigByName("configuration").getString("lang"));
		stuff = new StuffCollection(configs.getConfigByName("stuff"), log);
		spawn = Map.stringToLocation(configs.getConfigByName("configuration").getString("lobby.spawn"));
		for (String str : configs.getConfigByName("configuration").getStringList("lobby.lobby-stuff"))	{
			lobbyStuff.add(Map.stringToLocation(str));
		}
		maps = Map.loadMap();
		List<String> wNames = configs.getConfigByName("configuration").getStringList("worlds-name");
		maxPlayer = configs.getConfigByName("configuration").getInt("game.max-players");
		roundDelay = configs.getConfigByName("configuration").getInt("game.round-delay");
		if (maxPlayer < 4)	{maxPlayer =4;}
		drop = configs.getConfigByName("configuration").getBoolean("game.allow-drop");
		pickup = configs.getConfigByName("configuration").getBoolean("game.allow-pickup");
		persistentArrow = configs.getConfigByName("configuration").getBoolean("game.persistent-arrow");
		
		// Plugin Management
		teams = new Teams();
		for (String wName : wNames)	{
			this.getServer().getWorld(wName).setGameRuleValue("keepInventory", "true");
			this.getServer().getWorld(wName).setGameRuleValue("mobGriefing", "false");
			this.getServer().getWorld(wName).setGameRuleValue("naturalRegeneration", "false");
			this.getServer().getWorld(wName).setDifficulty(Difficulty.NORMAL);
			this.getServer().getWorld(wName).setPVP(true);
		}
		Parameters.loadValue(configs.getConfigByName("configuration").getConfigurationSection("parameters"));
		focusGame = new Game();
		focusGame.updateDisplayer();
		option = new ParametersGUI();
		
		// Commands Management
		getCommand("fcolor").setExecutor(new MyCommand());
		getCommand("freload").setExecutor(new MyCommand());
		getCommand("fconfig").setExecutor(new MyCommand());
		getCommand("finfo").setExecutor(new MyCommand());
		getCommand("freset").setExecutor(new MyCommand());
		getCommand("focus").setExecutor(new MyCommand());
		getCommand("fstuff").setExecutor(new MyCommand());
		getCommand("ftranslate").setExecutor(new MyCommand());
		
		// Event Management
		pm.registerEvents(new JoinQuit(), this);
		pm.registerEvents(new InventoryEvent(), this);
		pm.registerEvents(new ArrowEvent(), this);
		pm.registerEvents(new Death(), this);
		pm.registerEvents(new EntityInteraction(), this);
		pm.registerEvents(new Velocity(), this);
		
		for (Player p : Bukkit.getOnlinePlayers())	{
			p.setScoreboard(teams.getSidebarScoreboard());
			p.teleport(Focus.getSpawn());
			p.setBedSpawnLocation(Focus.getSpawn(), true);
			if (p.isOp())	{
				p.setGameMode(GameMode.CREATIVE);
			} else {
				p.setGameMode(GameMode.ADVENTURE);
			}
			p.setHealth(20);
			p.getInventory().clear();
			for (PotionEffect potion : p.getActivePotionEffects())	{
				p.removePotionEffect(potion.getType());
			}
			Death.clearAttribute(p);
		}
		
		if (configs.getConfigByName("maps").getKeys(false).size() == 0)	{
			
			log.info(ConsoleColor.BACKGROUND_RED + "Focus will be disable in 5 secondes, no maps configurate" + ConsoleColor.RESET);
			new BukkitRunnable() {
				@Override
				public void run() {
					pm.disablePlugin(JavaPlugin.getPlugin(Focus.class));
				}
			}.runTaskLater(this, 100);
		}
		
		log.info(ConsoleColor.GREEN + "=============== " + ConsoleColor.YELLOW + "Focus enable" + ConsoleColor.GREEN + " ===============" + ConsoleColor.RESET);
	}
	
	public void onDisable()	{
		
		teams.unregister();
		log.info(ConsoleColor.GREEN + "=============== " + ConsoleColor.YELLOW + "Focus disable" + ConsoleColor.GREEN + " ===============" + ConsoleColor.RESET);
		
	}
	
	public static Focus getInstance()	{
		return instance;
	}
	
	public ConfigurationCollection getConfigurations()	{
		return configs;
	}
	public static StuffCollection getAllStuff()	{
		return stuff;
	}
	public static void translate(Language newLang)	{
		language = newLang;
		focusGame.updateDisplayer();
		option = new ParametersGUI();
	}
	public static Language getLanguage()	{
		return language;
	}
	public static Location getSpawn()	{
		return spawn;
	}
	public static List<Location> getLobbyStuff()	{
		return lobbyStuff;
	}
	public Logger getLog()	{
		return log;
	}
	public Game getGame()	{
		return focusGame;
	}
	public static FocusPlayer playertoFocusPlayer(Player p)	{
		for (FocusPlayer fp : focusGame.getPlayers())	{
			if (p.getUniqueId().toString().equals(fp.getPlayer().getUniqueId().toString()))	{
				return fp;
			}
		}
		return new FocusPlayer(p, 'r');
	}

	public Teams getTeams() {
		return teams;
	}

	public void resetGame() {
		Bukkit.getScheduler().cancelAllTasks();
		teams = new Teams();
		focusGame = new Game();
		focusGame.updateDisplayer();
		option = new ParametersGUI();
	}
}
