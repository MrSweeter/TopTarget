package com.mrsweeter.focus.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

public class Parameters {
	
	public static int KP_VERYLOW, KP_LOW, KP_NORMAL, KP_HIGH, KP_EXTREME;
	public static int KWLIMIT_VERYLOW, KWLIMIT_LOW, KWLIMIT_NORMAL, KWLIMIT_HIGH, KWLIMIT_EXTREME;
	public static int MR_VERYLOW, MR_LOW, MR_NORMAL, MR_HIGH, MR_EXTREME;
	public static int RP_VERYLOW, RP_LOW, RP_NORMAL, RP_HIGH, RP_EXTREME;
	public static int RWLIMIT_VERYLOW, RWLIMIT_LOW, RWLIMIT_NORMAL, RWLIMIT_HIGH, RWLIMIT_EXTREME;
	public static int SP_VERYLOW, SP_LOW, SP_NORMAL, SP_HIGH, SP_EXTREME;
	
	public static List<Integer> KW = new ArrayList<>();
	public static List<Integer> RW = new ArrayList<>();
	public static List<Integer> MR = new ArrayList<>();
	
	public static void loadValue(ConfigurationSection config)	{
		
		List<Integer> section = config.getIntegerList("kill-point");
		KP_VERYLOW = section.get(0);
		KP_LOW = section.get(1);
		KP_NORMAL = section.get(2);
		KP_HIGH = section.get(3);
		KP_EXTREME = section.get(4);
		
		section = config.getIntegerList("kill-limit");
		KWLIMIT_VERYLOW = section.get(0);
		KWLIMIT_LOW = section.get(1);
		KWLIMIT_NORMAL = section.get(2);
		KWLIMIT_HIGH = section.get(3);
		KWLIMIT_EXTREME = section.get(4);
		
		KW.addAll(section);
		
		section = config.getIntegerList("max-round");
		MR_VERYLOW = section.get(0);
		MR_LOW = section.get(1);
		MR_NORMAL = section.get(2);
		MR_HIGH = section.get(3);
		MR_EXTREME = section.get(4);
		
		MR.addAll(section);
		
		section = config.getIntegerList("round-limit");
		RWLIMIT_VERYLOW = section.get(0);
		RWLIMIT_LOW = section.get(1);
		RWLIMIT_NORMAL = section.get(2);
		RWLIMIT_HIGH = section.get(3);
		RWLIMIT_EXTREME = section.get(4);
		
		RW.addAll(section);
		
		section = config.getIntegerList("round-point");
		RP_VERYLOW = section.get(0);
		RP_LOW = section.get(1);
		RP_NORMAL = section.get(2);
		RP_HIGH = section.get(3);
		RP_EXTREME = section.get(4);
		
		section = config.getIntegerList("start-point");
		SP_VERYLOW = section.get(0);
		SP_LOW = section.get(1);
		SP_NORMAL = section.get(2);
		SP_HIGH = section.get(3);
		SP_EXTREME = section.get(4);
		
	}
}
