package com.mrsweeter.focus.Players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mrsweeter.dreamAPI.Configuration.PluginConfiguration;
import com.mrsweeter.dreamAPI.CustomItem.MyPotion;
import com.mrsweeter.dreamAPI.CustomItem.MyTippedArrow;
import com.mrsweeter.dreamAPI.Utils.ConsoleColor;
import com.mrsweeter.focus.Focus;
import com.mrsweeter.focus.Players.Utils.Dual;

public class StuffCollection {
	
	// Add default row lore: Level-1
	
	private List<Dual> helmet = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> chestplate = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> legging = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> boots = Arrays.asList(null, null, null, null, null, null);
	
	private List<Dual> axe = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> sword = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> shield = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> bow = Arrays.asList(null, null, null, null, null, null);
	
	private List<Dual> arrow = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> smoke = Arrays.asList(null, null, null, null, null, null);
	private List<Dual> pearl = Arrays.asList(null, null, null, null, null, null);
	
	private ItemStack nothing = new ItemStack(Material.BARRIER);
	
	public StuffCollection(PluginConfiguration config, Logger log) {
		if (config.contains("helmet"))	{
			loadSpecifyStuff(config.getConfigurationSection("helmet"), helmet);
			log.info("[FocusStuff] helmet loaded");
			loadSpecifyStuff(config.getConfigurationSection("chestplate"), chestplate);
			log.info("[FocusStuff] chestplate loaded");
			loadSpecifyStuff(config.getConfigurationSection("legging"), legging);
			log.info("[FocusStuff] leggings loaded");
			loadSpecifyStuff(config.getConfigurationSection("boots"), boots);
			log.info("[FocusStuff] boots loaded");
			loadSpecifyStuff(config.getConfigurationSection("axe"), axe);
			log.info("[FocusStuff] axe loaded");
			loadSpecifyStuff(config.getConfigurationSection("sword"), sword);
			log.info("[FocusStuff] sword loaded");
			loadSpecifyStuff(config.getConfigurationSection("shield"), shield);
			log.info("[FocusStuff] shield loaded");
			loadSpecifyStuff(config.getConfigurationSection("bow"), bow);
			log.info("[FocusStuff] bow loaded");
			loadSpecifyStuff(config.getConfigurationSection("arrow"), arrow);
			log.info("[FocusStuff] arrow loaded");
			loadSpecifyStuff(config.getConfigurationSection("smoke"), smoke);
			log.info("[FocusStuff] smoke loaded");
			loadSpecifyStuff(config.getConfigurationSection("pearl"), pearl);
			log.info("[FocusStuff] pearl loaded");
			
			ItemMeta meta = nothing.getItemMeta();
			meta.setDisplayName("§c§lMaximun reach");
			nothing.setItemMeta(meta);
		}
	}

	private void loadSpecifyStuff(ConfigurationSection section, List<Dual> stuff) {
		ConfigurationSection level;
		ItemStack item;
		int cost = 0;
		if (section.contains("default"))	{
			level = section.getConfigurationSection("default");
			item = createItem(Material.getMaterial(level.getString("item").toUpperCase()), level);
			stuff.set(0, new Dual(item, 0));
		} else {
			stuff.set(0, new Dual(new ItemStack(Material.AIR), 0));
		}
		if (section.contains("level-1"))	{
			level = section.getConfigurationSection("level-1");
			item = createItem(Material.getMaterial(level.getString("item").toUpperCase()), level);
			cost = level.getInt("cost");
			stuff.set(1, new Dual(item, cost));
			
			if (section.contains("level-2"))	{
				level = section.getConfigurationSection("level-2");
				item = createItem(Material.getMaterial(level.getString("item").toUpperCase()), level);
				cost = level.getInt("cost");
				stuff.set(2, new Dual(item, cost));
				
				if (section.contains("level-3"))	{
					level = section.getConfigurationSection("level-3");
					item = createItem(Material.getMaterial(level.getString("item").toUpperCase()), level);
					cost = level.getInt("cost");
					stuff.set(3, new Dual(item, cost));
					
					if (section.contains("level-4"))	{
						level = section.getConfigurationSection("level-4");
						item = createItem(Material.getMaterial(level.getString("item").toUpperCase()), level);
						cost = level.getInt("cost");
						stuff.set(4, new Dual(item, cost));
						
						if (section.contains("level-5"))	{
							level = section.getConfigurationSection("level-5");
							item = createItem(Material.getMaterial(level.getString("item").toUpperCase()), level);
							cost = level.getInt("cost");
							stuff.set(5, new Dual(item, cost));
						}
					}
				}
			}
		}
	}
	
	private ItemStack createItem(Material material, ConfigurationSection section)	{
		
		ItemStack item = new ItemStack(material);
		
		if (section instanceof ConfigurationSection)	{
			
			if (material == Material.ARROW || material == Material.TIPPED_ARROW || material == Material.SPECTRAL_ARROW)	{
				if (section.contains("data") && section.getString("data").trim().length() != 0)	{
					String str = section.getString("data");
					item = MyTippedArrow.getArrowWithName(str.toUpperCase()).getArrow();
				}
			}
			if (material == Material.POTION || material == Material.SPLASH_POTION || material == Material.LINGERING_POTION)	{
				if (section.contains("data") && section.getString("data").trim().length() != 0)	{
					String str = section.getString("data");
					item = MyPotion.getPotionWithName(str.toUpperCase()).getPotion();
				}
			}
			
			ItemMeta meta = item.getItemMeta();
			
			if (section.contains("quantity"))	{
				int v = section.getInt("quantity");
				if (v > 64 && material != Material.ARROW && material != Material.TIPPED_ARROW && material != Material.SPECTRAL_ARROW)	{
					Focus.getInstance().getLog().info(ConsoleColor.YELLOW + "Only arrow and derivate can have more than 64 in amount, stuff.yml");
					v = 64;
				}
				item.setAmount(v);
			}
			if (section.contains("name") && section.getString("name").trim().length() != 0)	{
				String str = section.getString("name");
				str = str.replace("&", "§");
				meta.setDisplayName(str);
			}
			
			List<String> list = new ArrayList<>();
			String sectionName = section.getName();
			if (!sectionName.contains("level"))	{sectionName = "default";} else {sectionName = sectionName.substring(6);}
			
			list.add("§6--- Level: " + sectionName + " ---");
			
			if (section.contains("lore"))	{
				List<String> strList = section.getStringList("lore");
				for (String str : strList)	{
					str = str.replace("&", "§");
					list.add(str);
				}
				
				list.add("");
				if (section.contains("effect"))	{
					for (String str : section.getStringList("effect"))	{
						list.add("§7" + str);
					}
				}
				if (section.contains("enchant"))	{
					for (String str : section.getStringList("enchant"))	{
						list.add("§7" + str);
					}
				}
			}
			meta.setLore(list);
			if (section.contains("enchant"))	{
				List<String> strList = section.getStringList("enchant");
				Enchantment enchant;
				int power = 1;
				
				for (String str : strList)	{
					
					if (str.contains(":"))	{
						power = Integer.parseInt(str.substring(str.indexOf(":")+1));
						str = str.substring(0, str.indexOf(":"));
					}
					enchant = Enchantment.getByName(str.toUpperCase());
					if (enchant != null)	{
						meta.addEnchant(enchant, power, true);
					}
				}
			}
						
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
			meta.setUnbreakable(true);
			item.setItemMeta(meta);
		}
		
		return item;
	}

	public List<Dual> getHelmet() {
		return helmet;
	}

	public List<Dual> getChestPlate() {
		return chestplate;
	}
	
	public List<Dual> getLeggings() {
		return legging;
	}
	
	public List<Dual> getBoots() {
		return boots;
	}
	
	public List<Dual> getSword() {
		return sword;
	}
	
	public List<Dual> getAxe() {
		return axe;
	}
	
	public List<Dual> getShield() {
		return shield;
	}
	
	public List<Dual> getBow() {
		return bow;
	}
	
	public List<Dual> getArrow() {
		return arrow;
	}
	
	public List<Dual> getSmoke() {
		return smoke;
	}
	
	public List<Dual> getPearl() {
		return pearl;
	}
	
	public ItemStack getMax()	{
		return nothing;
	}
}
