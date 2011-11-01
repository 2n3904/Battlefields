package me.jkow.battlefields.scores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.gametypes.GametypePlugin;
import me.jkow.battlefields.util.BYamlConfiguration;
import me.jkow.battlefields.util.Stringable;

public class PlayerScores
{
	private String player;
	
	// Player/score trees for given score type
	private List<Score> scores = new ArrayList<Score>();
	private Map<String, Integer[]> games = new HashMap<String, Integer[]>();
	
	public PlayerScores(String player)
	{
		this.player = player;
	}
	
	public void addScore(Score score)
	{
		if(score != null)
			scores.add(score);
	}
	
	public final Map<String, Integer[]> getGameStats()
	{
		return games;
	}
	
	public int getTotalPoints()
	{
		int points = 0;
		for(Score score : scores)
			points += score.points();
		return points;
	}
	
	public int getTotalGamesWon()
	{
		int total = 0;
		for(Integer[] stats : games.values())
			total += stats[1];
		return total;
	}
	
	public int getTotalGamesLost()
	{
		int total = 0;
		for(Integer[] stats : games.values())
			total += stats[0];
		return total;
	}
	
	public int getTotalGamesPlayer()
	{
		return getTotalGamesWon() + getTotalGamesLost();
	}
	
	public void registerGame(GametypePlugin gp, boolean won)
	{
		String game = gp.getDescription().getFullName();
		if(!games.containsKey(game))
			games.put(game, new Integer[2]);
		Integer[] stats = games.get(game);
		if(won)
			stats[1]++;
		else
			stats[0]++;
	}
}
