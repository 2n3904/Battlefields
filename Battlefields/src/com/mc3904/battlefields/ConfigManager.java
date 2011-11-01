package com.mc3904.battlefields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.mc3904.battlefields.players.TeamMemberClass;
import com.mc3904.battlefields.util.BFBlock;
import com.mc3904.battlefields.util.BYamlConfiguration;
import com.mc3904.battlefields.util.Format;


public class ConfigManager 
{
	private Battlefields plugin;
	private BYamlConfiguration yaml;
	
	private Map<String, TeamMemberClass> classes = new HashMap<String, TeamMemberClass>();
	
	public ConfigManager(Battlefields plugin, BYamlConfiguration config)
	{
		this.plugin = plugin;
		this.yaml = config;
	}
	
	public String[] getPlayerClassList()
	{
		return classes.keySet().toArray(new String[0]);
	}
	
	public TeamMemberClass getPlayerClass(String str)
	{
		return classes.get(str);
	}
	
	public void loadConfig()
	{
		ConfigurationSection clist = yaml.getConfigurationSection("classes");
		if(clist != null)
		{
			for(String str : clist.getKeys(false))
			{
				ConfigurationSection ckey = clist.getConfigurationSection(str);
				int damage_b, damage_r, helmet;
				List<Integer> weapons;
				Integer[] armor = new Integer[4];
				damage_b = clist.getInt("damage_bonus", 0);
				damage_r = clist.getInt("damage_reduction", 0);
				weapons = (List<Integer>)clist.getList("weapons");
				armor[0] = clist.getInt("armor.helmet", 0);
				armor[1] = clist.getInt("armor.chestplate", 0);
				armor[2] = clist.getInt("armor.leggings", 0);
				armor[3] = clist.getInt("armor.boots", 0);
				classes.put(str, new TeamMemberClass(str, damage_b, damage_r, weapons, armor));
			}
		}
	}
}
