package com.mrsweeter.focus.Players.Utils;

import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectUtils {
	
	// Permanent Effect
	
	public static void applyMaxHealth(Player p, double level)	{
		
		Attribute a = Attribute.GENERIC_MAX_HEALTH;
		AttributeInstance ai = p.getAttribute(a);
		
		for (AttributeModifier am : ai.getModifiers())	{ai.removeModifier(am);}
		
		ai.addModifier(new AttributeModifier("health", level/100, Operation.ADD_SCALAR));
		
	}
	
	public static void applySpeed(Player p, double level)	{
		
		Attribute a = Attribute.GENERIC_MOVEMENT_SPEED;
		AttributeInstance ai = p.getAttribute(a);
		
		for (AttributeModifier am : ai.getModifiers())	{ai.removeModifier(am);}
		
		ai.addModifier(new AttributeModifier("speed", level/100, Operation.ADD_SCALAR));
		
	}
	
	public static void applyKnockbackResist(Player p, double level)	{
		
		Attribute a = Attribute.GENERIC_KNOCKBACK_RESISTANCE;
		AttributeInstance ai = p.getAttribute(a);
		
		for (AttributeModifier am : ai.getModifiers())	{ai.removeModifier(am);}
		
		ai.addModifier(new AttributeModifier("speed", level/100, Operation.ADD_SCALAR));
		
	}

	public static void applyJumpBoost(Player player, int level) {
		
		PotionEffect effect = new PotionEffect(PotionEffectType.JUMP, 999999, level-1, true, true);
		player.addPotionEffect(effect);
	}
	
	public static void applySlowness(Player player, int level) {
		
		PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 999999, level-1, true, true);
		player.addPotionEffect(effect);
	}
	
	public static void applyResistance(Player player, int level) {
		PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, level-1, true, true);
		player.addPotionEffect(effect);
	}
	
	public static void applyWeakness(Player player, int level) {
		PotionEffect effect = new PotionEffect(PotionEffectType.WEAKNESS, 999999, level-1, true, true);
		player.addPotionEffect(effect);
	}
	
	// Temporary Effect
	
	public static void applyPoison(Player victim, int level) {
		PotionEffect effect = new PotionEffect(PotionEffectType.POISON, level*70, 0, true, true);
		victim.addPotionEffect(effect);
	}
	
	public static void applyNausea(Player victim, int level)	{
		PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, level*70, 0, true, true);
		victim.addPotionEffect(effect);
	}
	
	public static void applyWither(Player victim, int level)	{
		PotionEffect effect = new PotionEffect(PotionEffectType.WITHER, level*70, 0, true, true);
		victim.addPotionEffect(effect);
	}
	
	public static void applyLevitation(Player victim, int level)	{
		PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, level*70, 0, true, true);
		victim.addPotionEffect(effect);
	}
	
	public static int getMatchingIndex(List<String> list, String regex)	{
		
		ListIterator<String> it = list.listIterator();
		String next;
		
		while (it.hasNext())	{
			next = it.next();
			if (Pattern.matches(regex, next))	{return it.nextIndex()-1;}
		}
		return -1;
	}
	
//	public static void applyAttackSpeed(Player p, int level)	{
//		
//		Attribute a = Attribute.GENERIC_ATTACK_SPEED;
//		AttributeInstance ai = p.getAttribute(a);
//		
//		for (AttributeModifier am : ai.getModifiers())	{ai.removeModifier(am);}
//		
//		ai.addModifier(new AttributeModifier("attackSpeed", level, Operation.ADD_SCALAR));
//		
//	}
}
