package com.mc3904.battlefields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import com.mc3904.battlefields.players.TeamMemberClass;
import com.mc3904.battlefields.util.BYamlConfiguration;


public class ConfigManager 
{
	private BYamlConfiguration yaml;
	
	private Map<String, TeamMemberClass> classes = new HashMap<String, TeamMemberClass>();
	
	public ConfigManager(BYamlConfiguration config)
	{
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
	
	@SuppressWarnings("unchecked")
	public void loadConfig()
	{
		ConfigurationSection clist = yaml.getConfigurationSection("classes");
		if(clist != null)
		{
			for(String str : clist.getKeys(false))
			{
				ConfigurationSection ckey = clist.getConfigurationSection(str);
				int damage_b, damage_r;
				List<Integer> weapons;
				Integer[] armor = new Integer[4];
				damage_b = ckey.getInt("damage_bonus", 0);
				damage_r = ckey.getInt("damage_reduction", 0);
				weapons = (List<Integer>)clist.getList("weapons");
				armor[0] = ckey.getInt("armor.helmet", 0);
				armor[1] = ckey.getInt("armor.chestplate", 0);
				armor[2] = ckey.getInt("armor.leggings", 0);
				armor[3] = ckey.getInt("armor.boots", 0);
				classes.put(str, new TeamMemberClass(str, damage_b, damage_r, weapons, armor));
			}
		}
	}
}
