package com.mc3904.battlefields.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.mc3904.battlefields.Battlefields;
import com.mc3904.battlefields.fieldbuilder.Builder;
import com.mc3904.battlefields.players.TeamColor;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.signs.BattlefieldSign;
import com.mc3904.battlefields.signs.SignArmor;
import com.mc3904.battlefields.signs.SignClasses;
import com.mc3904.battlefields.signs.SignControl;
import com.mc3904.battlefields.signs.SignDefaults;
import com.mc3904.battlefields.signs.SignGametypes;
import com.mc3904.battlefields.signs.SignOptions;
import com.mc3904.battlefields.signs.SignWeapons;
import com.mc3904.battlefields.util.BFBlock;
import com.mc3904.battlefields.util.BFLocation;
import com.mc3904.battlefields.util.BFRegion;
import com.mc3904.battlefields.util.BYamlConfiguration;

public class BattlefieldManager 
{
	Battlefields plugin;
	
	// Field save file
	BYamlConfiguration yaml;
	
	// List of available battlefields
	public Map<World, List<Battlefield>> fields = new HashMap<World, List<Battlefield>>();

	private Map<Player, TeamMember> players = new HashMap<Player, TeamMember>();
	private Map<Player, Builder> builders = new HashMap<Player, Builder>();
	
	public BattlefieldManager(Battlefields plugin, BYamlConfiguration yaml)
	{
		this.yaml = yaml;
		this.plugin = plugin;
	}
	
	public Battlefields getPlugin()
	{
		return plugin;
	}
	
	public Builder getBuilder(Player p)
	{
		return builders.get(p);
	}
	
	public void addBuilder(Player p, Builder b)
	{
		builders.put(p, b);
	}
	
	public Builder removeBuilder(Player p)
	{
		return builders.remove(p);
	}

	public List<TeamMember> getTeamMemberList()
	{
		return new ArrayList<TeamMember>(players.values());
	}
	
	public List<Player> getPlayerList()
	{
		return new ArrayList<Player>(players.keySet());
	}
	
	public TeamMember getPlayer(Player p)
	{
		return players.get(p);
	}
	
	public void addPlayer(TeamMember m)
	{
		players.put(m.getPlayer(), m);
	}
	
	public void removePlayer(TeamMember m)
	{
		players.remove(m.getPlayer());
	}
	
	public void addField(Battlefield field)
	{
		World w = field.getRegion().getWorld();
		if(!fields.containsKey(w))
			fields.put(w, new ArrayList<Battlefield>());
		fields.get(field.getRegion().getWorld()).add(field);
	}
	
	public boolean removeField(Battlefield field)
	{
		List<Battlefield> list = fields.get(field.getRegion().getWorld());
		if(list == null)
			return false;
		return list.remove(field);
	}
	
	public List<Battlefield> getFields(World w)
	{
		if(!fields.containsKey(w))
			fields.put(w, new ArrayList<Battlefield>());
		return fields.get(w);
	}
	
	public List<Battlefield> getFields()
	{
		List<Battlefield> all = new ArrayList<Battlefield>();
		for(List<Battlefield> list : fields.values())
			all.addAll(list);
		return all;
	}
	
	// Load a field by given name from save
	public boolean loadField(String name)
	{
		String root = "fields." + name + ".";
		BFRegion r = (BFRegion)yaml.getObj(root+"region", BFRegion.class);
		if(r == null)
		{
			plugin.log.warning("FAILURE : Battlefield '" + name + "' defined by an invalid region!");
			return false;
		}
		for(Battlefield field : getFields(r.getWorld()))
		{
			if(field.getRegion().intersects(r))
			{
				plugin.log.warning("FAILURE : Battlefield '" + name + "' intersects a previously defined field!");
				return false;
			}
		}
		Battlefield field = new Battlefield(name, r, plugin);
		
		field.setExit((BFLocation)yaml.getObj(root+"exitspawn", BFLocation.class));
		
		List<BFBlock> blist = (List<BFBlock>)yaml.getObjList(root+"signs", BFBlock.class);
		for(BFBlock bb : blist)
		{
			if(bb.getBlock().getState() instanceof Sign)
			{
				Sign sign = (Sign)bb.getBlock().getState();
				BattlefieldSign bs = null;
				String line = sign.getLine(1);
				if(line.equals("[Armor]"))
					bs = new SignArmor(sign, field);
				else if(line.equals("[Class]"))
					bs = new SignClasses(sign, field);
				else if(line.equals("[Controls]"))
					bs = new SignControl(sign, field);
				else if(line.equals("[Defaults]"))
					bs = new SignDefaults(sign, field);
				else if(line.equals("[Gametype]"))
					bs = new SignGametypes(sign, field);
				else if(line.equals("[Options]"))
					bs = new SignOptions(sign, field);
				else if(line.equals("[Weapons]"))
					bs = new SignWeapons(sign, field);
				if(bs != null)
					field.addSign(bs, bb);
			}
		}
		
		ConfigurationSection blocks = yaml.getConfigurationSection(root+"blocks");
		if(blocks != null)
		{
			for(String str : blocks.getKeys(false))
			{
				List<BFBlock> list = (List<BFBlock>)yaml.getObjList(root+"blocks."+str, BFBlock.class);
				if(list != null)
					field.addBlockList(str, list);
			}
		}
		
		ConfigurationSection spawns = yaml.getConfigurationSection(root+"spawns");
		if(blocks != null)
		{
			for(String str : spawns.getKeys(false))
			{
				List<BFLocation> list = (List<BFLocation>)yaml.getObjList(root+"spawns."+str, BFLocation.class);
				if(list != null)
					field.addSpawns(str, list);
			}
		}
		
		ConfigurationSection regions = yaml.getConfigurationSection(root+"regions");
		if(regions != null)
		{
			for(String str : regions.getKeys(false))
			{
				BFRegion reg = (BFRegion)yaml.getObj(root+"regions."+str, BFRegion.class);
				if(reg != null)
					field.addSubregion(str, reg);
			}
		}
		
		addField(field);
		return true;
	}
	
	// Load all fields from save
	public void loadFields()
	{
		ConfigurationSection fieldsec = yaml.getConfigurationSection("fields");
		if(fieldsec == null)
		{
			plugin.log.info("FAILURE : fields.yml is empty.");
			return;
		}
		Set<String> fieldlist = fieldsec.getKeys(false);
		plugin.log.info("[Battlefields] Loading Battlefields...");
		for(String name : fieldlist)
		{
			if(loadField(name))
				plugin.log.info("SUCCESS : Battlefield '" + name + "' loaded.");
		}
	}
	
	// Save a field to file
	public void saveField(Battlefield field)
	{
		String root = "fields."+field.getName() + ".";
		yaml.setObj(root+"exitspawn", field.getExitSpawn());
		yaml.setObj(root+"region", field.getRegion());
		
		for(String str : field.getBlockNames())
			yaml.setObjList(root+"blocks."+str, field.getBlocks(str));

		for(String str : field.getSubregionNames())
			yaml.setObj(root+"regions."+str, field.getSubregion(str));
	
		for(String str : field.getSpawnNames())
			yaml.setObjList(root+"spawns."+str, field.getSpawns(str));
				
		yaml.setObjList(root+"signs", field.getSignBlocks());
		
		yaml.save();
	}	
	
	public void saveFields()
	{
		yaml.getRoot().createSection("fields");
		for(Battlefield bf : getFields())
			saveField(bf);
	}

	// Get battlefield
	public Battlefield getField(Player p)
	{	
		for(Battlefield field : getFields(p.getWorld()))
		{
			if(field.getRegion().contains(p))
				return field;
		}
		return null;
	}
	
	public Battlefield getField(Location l)
	{	
		for(Battlefield field : getFields(l.getWorld()))
		{
			if(field.getRegion().contains(l))
				return field;
		}
		return null;
	}
	
	public Battlefield getField(Block b)
	{
		for(Battlefield field : getFields(b.getWorld()))
		{
			if(field.getRegion().contains(b))
				return field;
		}
		return null;
	}
	
	public Battlefield getField(String name)
	{
		for(Battlefield field : getFields())
		{
			if(field.getName().equalsIgnoreCase(name))
				return field;
		}
		return null;
	}
}
