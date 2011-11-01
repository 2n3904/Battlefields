package me.jkow.battlefields.scores;

import org.bukkit.Bukkit;

import me.jkow.battlefields.util.Stringable;

public class Score
{
	private String pname = null;
	private int score = 0;
	private ScoreType type;
	
	public Score(ScoreType type, String pname, int score)
	{
		this.pname = pname;
		this.score = score;
		this.type = type;
	}
	
	public ScoreType getType()
	{
		return type;
	}
	
	public String getPlayerName()
	{
		return pname;
	}
	
	public int value()
	{
		return score;
	}
	
	public int add(int amount)
	{
		return score += amount;
	}
	
	public int subtract(int amount)
	{
		return score -= amount;
	}
	
	public int multiply(int amount)
	{
		return score *= amount;
	}
	
	public void set(int amount)
	{
		score = amount;
	}
	
	public int points()
	{
		return score*type.getPoints();
	}
	
	public float reward()
	{
		return score*type.getReward();
	}
	
}
