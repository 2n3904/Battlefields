package com.mc3904.battlefields.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.mc3904.battlefields.Battlefields;
import com.mc3904.battlefields.gametypes.Gametype;
import com.mc3904.battlefields.players.Team;
import com.mc3904.battlefields.players.TeamColor;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.signs.BattlefieldSign;
import com.mc3904.battlefields.util.BFBlock;
import com.mc3904.battlefields.util.BFLocation;
import com.mc3904.battlefields.util.BFRegion;
import com.mc3904.battlefields.util.Format;
import com.mc3904.battlefields.voting.Vote;



public class Battlefield
{
	public final Battlefields plugin;
	private final BattlefieldManager fm;
	
	// Name of battlefield
	private String name;
	private String entry_message;
	private String exit_message;
	
	// Battlefield host
	private TeamMember host;
	
	// Temporarily blocked players
	private List<Player> banned = new ArrayList<Player>();
	
	
	// Battlefield Teams
	private HashMap<TeamColor, Team> teams = new HashMap<TeamColor, Team>();
	
	// Battlefield Features
	private HashMap<BFBlock, BattlefieldSign> signs = new HashMap<BFBlock, BattlefieldSign>();

	private HashMap<String, List<BFLocation>> spawns = new HashMap<String, List<BFLocation>>();
	private HashMap<String, BFRegion> subregions = new  HashMap<String, BFRegion>();
	private HashMap<String, List<BFBlock>> blocks = new HashMap<String, List<BFBlock>>();
	
	
	private BFLocation exitpoint;
	
	// Battlefield Objects
	private Gametype gametype = null;
	private Vote vote = null;
	private BFRegion region;
	private BattlefieldSettings settings;

	// Field technical data
	private int timeTaskId;
	private int countdownTaskId;
	private long timeStamp;
	private int countdown;
	private Boolean isactive = false;
		
	// Constructor
	public Battlefield(String name, BFRegion region, Battlefields plugin)
	{
		this.plugin = plugin;
		this.fm = plugin.getBattlefieldManager();
		this.name = name;
		this.region = region;
		this.exitpoint = new BFLocation(region.getWorld().getSpawnLocation());
		for(TeamColor color : TeamColor.values())
		{
			teams.put(color, new Team(color, this));
		}
		this.settings = new BattlefieldSettings();
	}
	
	// Add a player to the field
	public void addPlayer(Player p)
	{
		Team t = getTeamByOrder();
		TeamMember m = new TeamMember(p, t);
		fm.addPlayer(m);
		t.addMember(m);
		if(host == null)
			setHost(m);
	}
	
	// Register a sign to field
	public void addSign(BattlefieldSign type, BFBlock bb)
	{
		signs.put(bb, type);
	}
	
	// Get the type of a sign
	public BattlefieldSign getSign(Block b)
	{
		for(BFBlock loc : signs.keySet())
		{
			if(loc.equals(b))
				return signs.get(loc);
		}
		return null;
	}
	
	// Get the type of a sign
	public BattlefieldSign getSign(BFBlock b)
	{
		return signs.get(b);
	}
	
	// Get the type of a sign
	public List<BFBlock> getSignBlocks()
	{
		return new ArrayList<BFBlock>(signs.keySet());
	}
	
	// Unregister a sign from the field
	public boolean removeSign(Block b)
	{
		for(BFBlock loc : signs.keySet())
		{
			if(loc.equals(b))
			{
				signs.remove(loc);
				return true;
			}
		}
		return false;
	}
	
	// Switches a player's team
	public boolean changePlayerTeam(TeamMember m, Team t)
	{
		if(gametype != null)
			return false;
		t.addMember(m);
		m.reset();
		m.getTeam().removeMember(m);
		Format.sendMessage(m, "Your team has been switched to " + t.getColor().getTeamTag() + ".");
		return true;
	}
	
	// Clears the field of all item entities
	public void clearItems()
	{
		List<Entity> things = region.getWorld().getEntities();
		for(int i=0; i<things.size(); i++)
		{
			Entity thing = things.get(i);
			if(thing instanceof Item)
			{
				if(region.contains(thing.getLocation()))
					thing.remove();
			}
		}
	}
	
	public void clearAll()
	{
		if(isActive())
			endGametype();
		for(TeamMember m : getPlayers())
			kickPlayer(m);
	}
	
	// Forces a game end
	public boolean endGametype()
	{
		if(!isactive)
			return false;
		gametype.getListener().onGameEnd();
		savePlayers();
		isactive = false;
		return true;
	}
	
	// Gets the message on entering a battlefield
	public String getEntryMessage() 
	{
		return entry_message;
	}
	
	// Gets the message on exiting a battlefield
	public String getExitMessage() 
	{
		return exit_message;
	}
	
	// Gets the outside-field spawnpoint
	public Location getExitSpawn() 
	{
		return exitpoint;
	}
	
	// Returns the current gametype loaded
	public Gametype getGametype()
	{
		return gametype;
	}
	
	// Get game host
	public TeamMember getHost()
	{
		return host;
	}
	
	// Returns the name of the battlefield
	public String getName()
	{
		return name;
	}
	
	// Return a list of all the players on every team
	public List<TeamMember> getPlayers()
	{
		List<TeamMember> players = new ArrayList<TeamMember>();
		for(Team team : teams.values())
		{
			players.addAll(team.getMembers());
		}
		return players;
	}

	
	// Return battlefield region
	public BFRegion getRegion()
	{
		return region;
	}
	
	// Gets the list of all spawnpoints
	public List<Location> getSpawns()
	{
		List<Location> list = new ArrayList<Location>();
		for(List<BFLocation> current : spawns.values())
			list.addAll(current);
		return list;
	}
	
	// Gets the list of all spawnpoints of a given color
	public List<BFLocation> getSpawns(TeamColor color)
	{
		return spawns.get(color);
	}
	
	// Return the team corresponding to the given color
	public Team getTeam(TeamColor color)
	{
		return teams.get(color);
	}
	
	// Return a list of the teams
	public List<Team> getTeams()
	{
		List<Team> sort = new ArrayList<Team>(teams.values());
		return sort;
	}
	
	// Get team with the least number of players & is in gametype
	public Team getTeamByOrder()
	{
		if(gametype == null)
			return getTeam(TeamColor.NEUTRAL);
		Team result = null;
		Team temp = null;
		for(TeamColor color : gametype.getGametypePlugin().getDescription().getAllowedTeams())
		{
			temp = getTeam(color);
			if(result == null)
				result = temp;
			if(temp.getMembers().size() < result.getMembers().size())
				result = temp;
		}
		return result;
	}
	
	// Gets the current vote 
	public Vote getVote() 
	{
		return vote;
	}
	
	// Indicates whether gametype is being played or not
	public boolean isActive()
	{
		return isactive;
	}
	
	// Kicks a player from the battlefield
	public void kickPlayer(TeamMember m)
	{
		Player p = m.getPlayer();
		banned.add(p);
		Format.sendMessage(m, "You have been kicked from the game.");
		removePlayer(m);
		p.teleport(exitpoint);
	}
	
	// Removes a player from the battlefield
	public void removePlayer(TeamMember m)
	{
		Team t = m.getTeam();
		t.removeMember(m);	
		if(host == m)
		{
			if(getPlayers().isEmpty())
				host = null;
			else
				host = getPlayers().get(0);
		}
		fm.removePlayer(m);
	}
	
	// Removes all subregions defined at players location
	public int removeRegion(Location l)
	{
		int removed = 0;
		for(String str : subregions.keySet())
		{
			if(subregions.get(str).contains(l))
			{
				removed++;
				subregions.remove(str);
			}
		}
		return removed;
	}
	
	// Remove all nearby spawns 
	public int removeSpawns(Location l)
	{
		int removed = 0;
		for(String str : spawns.keySet())
		{
			for(Location spawn : spawns.get(str))
			{
				if(spawn.distanceSquared(l) < 16)
				{
					spawns.get(str).remove(spawn);
					removed++;					
				}
			}
		}
		return removed;
	}
	
	// Save field to file
	public void save()
	{
		fm.saveField(this);
	}
	
	// TODO Save the statistics for all players on the battlefield
	public void savePlayers()
	{
	}
	
	// Sends given message to all players on the battlefield
	public void sendMessageToAll(String message)
	{
		for(Team team : teams.values())
		{
			team.sendMessageToAll(message);
		}
	}
	
	// Sets a team's block
	public boolean addBlock(String s, BFBlock b)
	{
		List<BFBlock> list = blocks.get(s);
		if(list == null)
		{
			list = new ArrayList<BFBlock>();
			list.add(b);
			blocks.put(s, list);
			return true;
		}
		for(BFBlock block : list)
		{
			if(block.equals(b))
				return false;
		}
		list.add(b);
		return true;
	}
	
	public List<BFBlock> removeBlocks(String str)
	{
		return blocks.remove(str);
	}
	
	public boolean removeBlock(Block b)
	{
		List<BFBlock> list;
		for(String str : blocks.keySet())
		{
			list = blocks.get(str);
			for(BFBlock block : list)
			{
				if(block.equals(b))
					return list.remove(block);
			}
		}
		return false;
	}

	public boolean addSubregion(String s, BFRegion r)
	{
		boolean outcome = true;
		if(subregions.containsKey(s))
			outcome = false;
		subregions.put(s, r);
		return outcome;
	}
	
	public BFRegion getSubregion(String str)
	{
		return subregions.get(str);
	}
	
	public BFRegion removeSubregion(String str)
	{
		return subregions.remove(str);
	}
	
	public boolean removeSubregion(Location loc)
	{
		for(String str : subregions.keySet())
		{
			if(subregions.get(str).contains(loc));
			{
				subregions.remove(str);
				return true;
			}
		}
		return false;
	}
	
	public List<BFBlock> getBlocks(String str)
	{
		return blocks.get(str);
	}
	
	public boolean addBlockList(String str, List<BFBlock> list)
	{
		if(blocks.containsKey(str))
			return false;
		blocks.put(str, list);
		return true;
	}
	
	public List<BFRegion> getSubregions(String str)
	{
		return new ArrayList<BFRegion>(subregions.values());
	}
	
	public List<String> getBlockNames()
	{
		return new ArrayList<String>(blocks.keySet());
	}
	
	public List<String> getSubregionNames()
	{
		return new ArrayList<String>(subregions.keySet());
	}


	
	// Clears all of the spawnpoints
	public void clearSpawns(TeamColor color)
	{
		this.spawns.get(color).clear();
	}
	
	public List<BFLocation> getSpawns(String str)
	{
		return spawns.get(str);
	}
	
	public List<String> getSpawnNames()
	{
		return new ArrayList<String>(spawns.keySet());
	}
	
	// Returns the location of a randomly selected spawn from all teams
	public BFLocation getRandomSpawn()
	{
		Random rand = new Random();
		List<BFLocation> locs = new ArrayList<BFLocation>();
		for(List<BFLocation> current : spawns.values())
			locs.addAll(current);
		if(locs.isEmpty())
			return null;
		return locs.get(rand.nextInt(locs.size()));
	}
	
	// Returns the location of a randomly selected spawn for the given team color
	public BFLocation getRandomSpawn(String str)
	{
		Random rand = new Random();
		List<BFLocation> locs = spawns.get(str);
		if(locs.isEmpty())
			return null;
		return locs.get(rand.nextInt(locs.size()));
	}
	
	// Add a list of spawnpoints to the field
	public void addSpawns(String str, List<BFLocation> list)
	{
		if(!spawns.containsKey(str))
			spawns.put(str, new ArrayList<BFLocation>());
		this.spawns.get(str).addAll(list);
	}
	
	// Add a spawnpoint to the field
	public boolean addSpawn(String str, BFLocation l)
	{
		if(!spawns.containsKey(str))
			spawns.put(str, new ArrayList<BFLocation>());
		this.spawns.get(str).add(l);
		return true;
		//TODO condition for tool
	}
	
	// Sets a new entry message
	public void setEntryMessage(String entry_message) 
	{
		this.entry_message = entry_message;
	}

	// Sets the spectator spawnpoint
	public void setExit(BFLocation l)
	{
		this.exitpoint = l;
	}

	// Sets the exit message
	public void setExitMessage(String exit_message) 
	{
		this.exit_message = exit_message;
	}

	// Sets a new gametype and adjusts the 
	public boolean setGametype(Gametype g)
	{
		if(isactive)
			return false;
		gametype = g;
		organizeTeams();
		for(BattlefieldSign s : signs.values())
			s.reset();
		return true;
	}
	
	// Organizes teams into teams matching current gametype
	public void organizeTeams()
	{
		if(gametype == null)
			return;
		List<TeamColor> allowed = gametype.getGametypePlugin().getDescription().getAllowedTeams();
		for(TeamMember m : getPlayers())
		{
			if(!allowed.contains(m.getTeam()))
				changePlayerTeam(m, getTeamByOrder());
		}
	}

	// Sets a new host for the game
	public boolean setHost(TeamMember m)
	{
		if(!getPlayers().contains(m))
			return false;
		host = m;
		Format.sendMessage(m, "You are now Host of this battlefield.");
		return true;
	}

	// Starts up a new vote
	public boolean setVote(Vote newvote) 
	{
		
		if(vote != null)
		{
			if(vote.isActive())
				return false;
		}
		vote = newvote;
		if(newvote.isValid())
		{
			vote.start();
			return true;
		}
		return false;
	}

	// Attempts to start the loaded gametype
	public boolean startGametype()
	{
		if(isactive)
			return false;
		if(gametype == null)
			return false;
		if(gametype.checkField())
		{
			final BukkitScheduler sc = plugin.getServer().getScheduler();
			countdown = 5;
			countdownTaskId = sc.scheduleSyncRepeatingTask(plugin, new Runnable() {
			    public void run() {
			    	if(countdown < 1)
			    	{
			    		sc.cancelTask(countdownTaskId);
			    		timeTaskId = sc.scheduleSyncDelayedTask(plugin, new Runnable() {
			    		    public void run() {
			    		        endGametype();
			    		    }
			    		}, 600);
			    		gametype.getListener().onGameStart();
			    		isactive = true;
			    	}
			    	else
			    	{
				        sendMessageToAll(Format.pluginTag + "Game starting in " + Integer.toString(countdown) + "...");
				        countdown--;
			    	}
			    }
			}, 1, 20);
			return true;
		}
		return false;
	}
	
	public BattlefieldSettings getSettings()
	{
		return settings;
	}
}