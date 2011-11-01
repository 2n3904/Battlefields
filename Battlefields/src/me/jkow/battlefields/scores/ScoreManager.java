package me.jkow.battlefields.scores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.gametypes.GametypePlugin;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.BYamlConfiguration;

import org.bukkit.entity.Player;

public class ScoreManager 
{
	private Battlefields plugin;
	private BYamlConfiguration scorefile;
	private BYamlConfiguration configfile;
	
	// Player/score trees for given score type
	private Map<String, PlayerScores> scores = new HashMap<String, PlayerScores>();
	
	public ScoreManager(Battlefields plugin, BYamlConfiguration yaml)
	{
		this.plugin = plugin;
		this.scorefile = yaml;
		this.configfile = plugin.getConfigFile();
	}
	
	public boolean contains(String player)
	{
		return scores.containsKey(player);
	}
	
	public void loadPlayers()
	{
		
	}
	
	public PlayerScores getPlayerScores(String name)
	{
		return scores.get(name);
	}
	
	public void addPlayer(String player)
	{
		if(!contains(player))
			scores.put(player, new PlayerScores(player));
	}
}
