package com.mrsweeter.focus.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.mrsweeter.dreamAPI.Utils.ConsoleColor;
import com.mrsweeter.focus.Focus;

public class Map {
	
	private String name;
	private List<String> lore;
	private List<Location> spawns;
	private List<Location> availableSpawn;
	
	public Map(ConfigurationSection section)	{
		
		spawns = new ArrayList<>();
		availableSpawn = new ArrayList<>();
		name = section.getString("name").replace("&", "§");
		lore = section.getStringList("description");
		for (String str : section.getStringList("coordinates"))	{
			spawns.add(stringToLocation(str));
			availableSpawn.add(stringToLocation(str));
		}
		
	}
	
	public static List<Map> loadMap()	{
		Focus pl = Focus.getInstance();
		List<Map> temp = new ArrayList<>();
		ConfigurationSection section;
		for (String str : pl.getConfigurations().getConfigByName("maps").getKeys(false))	{
			if (temp.size() > 9)	{
				pl.getLog().info(ConsoleColor.YELLOW + "Only 9 maps can be loaded" + ConsoleColor.RESET);
				return temp;
			}
			section = pl.getConfigurations().getConfigByName("maps").getConfigurationSection(str);
			temp.add(new Map(section));
			pl.getLog().info("[FocusMap] " + str + " loaded");
		}
		return temp;		
	}
	
	public static Location stringToLocation(String str)	{
		
		String[] locStr = str.split("%");
		World world = null; double x = 0, y = 0, z = 0; float yaw = 0, pitch = 0;
		if (locStr.length > 3)	{
			
			world = Bukkit.getWorld(locStr[0]);
			x = Double.parseDouble(locStr[1]);
			y = Double.parseDouble(locStr[2]);
			z = Double.parseDouble(locStr[3]);
			if (locStr.length > 5)	{
				yaw = Float.parseFloat(locStr[4]);
				pitch = Float.parseFloat(locStr[5]);
			}
			
			
		}
		Location loc = new Location(world, x, y, z, yaw, pitch);
		return loc;
	}

	public static Map randomMap(List<Map> maps) {
		if (maps != null && maps.size() > 0)	{
			Random r = new Random();
			return maps.get(r.nextInt(maps.size()));
		}
		return null;
	}
	
	public String toString()	{
		String str = name;
		for (String s : lore)	{
			str += "\n" + s.replace("&", "§");
		}
		return str;
	}
	
	public Location getLocation()	{
		if (availableSpawn.size() == 0)	{
			availableSpawn.addAll(spawns);
		}
		Random r = new Random();
		int x = r.nextInt(availableSpawn.size());
		return availableSpawn.remove(x);
	}
	
	public Location getLocation(int i) {
		return spawns.get(i);
	}

	public String getName() {
		return name;
	}
}
