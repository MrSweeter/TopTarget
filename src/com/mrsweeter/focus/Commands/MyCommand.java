package com.mrsweeter.focus.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.mrsweeter.dreamAPI.Configuration.PluginConfiguration;
import com.mrsweeter.focus.Focus;

public class MyCommand implements CommandExecutor, TabCompleter	{
	
	private Focus pl;
	
	public MyCommand() {
		pl = Focus.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		if (sender.hasPermission(command.getPermission()))	{
			commandLabel = command.getLabel();
			switch (commandLabel)	{
			case "fcolor":
				return ExecuteCommand.openColor(pl.getGame(), sender);
			case "fstuff":
				return ExecuteCommand.openStuff(pl, pl.getGame(), sender);
			case "freload":
				Focus.pm.disablePlugin(pl);
				Focus.pm.enablePlugin(pl);
				sender.sendMessage(Focus.getLanguage().cmd_reload);
				return true;
			case "fconfig":
				return ExecuteCommand.openOption(pl.getGame(), sender);
			case "finfo":
				return ExecuteCommand.showInfo(sender, args);
			case "freset":
				return ExecuteCommand.resetGame(sender, pl);
			case "focus":
				return ExecuteCommand.startGame(sender, pl);
			case "ftranslate":
				return ExecuteCommand.translate(sender, pl, args);
			}
		} else {
			sender.sendMessage(Focus.getLanguage().cmd_noPermission);
		}
		return true;
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		List<String> result = new ArrayList<>();
		
		if (sender.hasPermission(command.getPermission()))	{
			commandLabel = command.getLabel();
			switch (commandLabel)	{
			case "fcolor":
				break;
			case "fstuff":
				break;
			case "freload":
				break;
			case "fconfig":
				break;
			case "finfo":
				break;
			case "freset":
				break;
			case "focus":
				break;
			case "ftranslate":
				
				PluginConfiguration lang = pl.getConfigurations().getConfigByName("lang");
				for (String key : lang.getKeys(false))	{
					if (!key.equals("plugin-prefix"))	{result.add(key);}
				}
				
				break;
			}
		}
		return result;
		
	}
}
