package com.mrsweeter.focus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.mrsweeter.dreamAPI.Configuration.PluginConfiguration;
import com.mrsweeter.dreamAPI.Messages.ChatComponent;
import com.mrsweeter.dreamAPI.Messages.FormatedChatComponent;
import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Language;
import com.mrsweeter.focus.Game.Game;
import com.mrsweeter.focus.Game.StateGame;
import com.mrsweeter.focus.Listeners.Death;
import com.mrsweeter.focus.Players.FocusPlayer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class ExecuteCommand {

	public static boolean openColor(Game game, CommandSender sender) {
		if (sender instanceof Player)	{
			if (game.isGameState(StateGame.LOBBY))	{
				Player p = (Player) sender;
				game.getColorPicker().openGUI(p);
			} else {
				sender.sendMessage(Focus.getLanguage().cmd_game_noColorChoiceIG);
			}
		} else {
			sender.sendMessage(Focus.getLanguage().cmd_onlyPlayer);
		}
		return true;
	}

	public static boolean startGame(CommandSender sender, Focus pl) {
		if (pl.getGame().isGameState(StateGame.LOBBY))	{
			if (pl.getGame().getPlayers().size() > 1)	{
				pl.getGame().start();
				return true;
			} else {
				Bukkit.broadcastMessage(Focus.getLanguage().cmd_game_notEnoughPlayer);
			}
		} else {
			sender.sendMessage(Focus.getLanguage().cmd_game_noStartGameIG);
		}
		return true;
	}

	public static boolean resetGame(CommandSender sender, Focus pl) {
		String byWho = Focus.getLanguage().cmd_game_resetGameSubTitleConsole;
		
		pl.resetGame();
		if (sender instanceof Player)	{
			byWho = Focus.getLanguage().cmd_game_resetGameSubTitlePlayer.replace("{PLAYER}", sender.getName());
		}
		
		for (Player player : Bukkit.getServer().getOnlinePlayers())	{
			player.teleport(Focus.getSpawn());
			player.setBedSpawnLocation(Focus.getSpawn(), true);
			if (player.isOp())	{
				player.setGameMode(GameMode.CREATIVE);
			} else {
				player.setGameMode(GameMode.ADVENTURE);
			}
			player.setScoreboard(pl.getTeams().getSidebarScoreboard());
			player.setHealth(20);
			player.getInventory().clear();
			player.sendTitle(Focus.getLanguage().cmd_game_resetGameTitle, byWho, 20, 60, 20);
			for (PotionEffect potion : player.getActivePotionEffects())	{
				player.removePotionEffect(potion.getType());
			}
			Death.clearAttribute(player);
		}
		sender.sendMessage(Focus.getLanguage().cmd_game_resetGameChat);
		return true;
	}

	public static boolean openStuff(Focus pl, Game game, CommandSender sender) {
		if (sender instanceof Player)	{
			if (game.isGameState(StateGame.STUFF))	{
				FocusPlayer p = Focus.playertoFocusPlayer((Player) sender);
				try {
					p.openStuff(pl);
				} catch (NullPointerException ex)	{
					sender.sendMessage(Focus.getLanguage().cmd_game_notInGame);
				}
			} else {
				sender.sendMessage(Focus.getLanguage().cmd_game_noStuffChoiceOG);
			}
		} else {
			sender.sendMessage(Focus.getLanguage().cmd_onlyPlayer);
		}
		return true;
	}

	public static boolean openOption(Game game, CommandSender sender) {
		if (sender instanceof Player)	{
			if (game.isGameState(StateGame.LOBBY))	{
				Player p = (Player) sender;
				Focus.option.openGUI(p);
			} else {
				sender.sendMessage(Focus.getLanguage().cmd_game_noOptionIG);
			}
		} else {
			sender.sendMessage(Focus.getLanguage().cmd_onlyPlayer);
		}
		return true;
	}
	
	public static boolean translate(CommandSender sender, Focus pl, String[] args) {
		
		if (args.length != 1)	{return false;}
		
		PluginConfiguration lang = pl.getConfigurations().getConfigByName("lang");
		if (!lang.contains(args[0]))	{return false;}
		if (args[0].equals("plugin-prefix"))	{return false;}
		
		Focus.translate(new Language(lang, args[0]));
		for (Player p : Bukkit.getOnlinePlayers())	{
			p.sendTitle("", "§9§lG§c§lo§e§lo§9§lG§a§ll§c§le §9§ltraduction process", 20, 60, 20);
		}
		return true;
	}
	
	public static boolean showInfo(CommandSender sender, String[] args) {
		
		ChatComponent text;
		
		sender.sendMessage("§6---------- Focus info -----------");
		if (sender instanceof Player)	{
			
			text = new ChatComponent(new FormatedChatComponent("§e▶ Concept by "));
			text.addClickEvent("Tagazok59", new ClickEvent(Action.OPEN_URL, "https://www.youtube.com/watch?v=HGEmRbopm10"), ChatColor.DARK_AQUA);
			text.sendChatToPlayer((Player) sender);
			
		} else {sender.sendMessage("§e▶ Concept by §aTagazok59");}
		if (sender instanceof Player)	{
			
			text = new ChatComponent(new FormatedChatComponent("§e▶ PvP map in 1.8.9: "));
			text.addClickEvent("Focus", new ClickEvent(Action.OPEN_URL, "https://www.planetminecraft.com/project/focus-3889138/"), ChatColor.DARK_AQUA);
			text.sendChatToPlayer((Player) sender);
			
		} else {sender.sendMessage("§e▶ PvP map in 1.8.9: §aFocus");}
		sender.sendMessage("§e▶ - Free For All PvP game where anything can happen");
		sender.sendMessage("§e▶ - Several Arenas");
		sender.sendMessage("§e▶ - Fully customizables Rules and Options");
		sender.sendMessage("");
		sender.sendMessage("§e▶ This map was made to be played countless time thanks to short and addictives matchs");
		sender.sendMessage("");
		if (sender instanceof Player)	{
			
			text = new ChatComponent(new FormatedChatComponent("§e▶ Builders of Focus 1.8.9: "));
			text.addClickEvent("WolNetwork, ", new ClickEvent(Action.OPEN_URL, "http://wolnetwork.fr/index.php"), ChatColor.GOLD);
			text.addClickEvent("EchoCube, ", new ClickEvent(Action.OPEN_URL, "https://www.echocube.in/"), ChatColor.GOLD);
			text.addClickEvent("Evenzia, ", new ClickEvent(Action.OPEN_URL, "http://www.honey-hive.fr/"), ChatColor.GOLD);
			text.sendChatToPlayer((Player) sender);
			
		} else {sender.sendMessage("§e▶ Builders of Focus 1.8.9: §6Wol§8Network§e, §6EchoCube§e, §6Evenzia");}
		return true;
//		if (args.length == 0)	{
//			
//		} else if (args.length == 1 && isInteger(args[0]))	{
//			
//		}
	}
	
//	private static boolean isInteger(String str)	{
//		try	{
//			Integer.parseInt(str);
//			return true;
//		} catch (Exception ex)	{
//			return false;
//		}
//	}
}
