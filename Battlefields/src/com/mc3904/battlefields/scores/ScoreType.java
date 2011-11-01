package com.mc3904.battlefields.scores;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

//-------------------------------------------------------------------------------------------------------------------//

public class ScoreType
{
	private int points = 1;
	private float reward = 1;
	private String name;
	private ScoreManager sm;
	
	@SuppressWarnings("serial")
	private SortedSet<Score> scores = new TreeSet<Score>() 
	{
		@Override
		public Comparator<Score> comparator()
		{
			return new Comparator<Score>()
			{
				@Override
				public int compare(Score o1, Score o2) 
				{
					int s1 = o1.value();
					int s2 = o2.value();
					if(s1 > s2)
						return 1;
					if(s1 < s2)
						return -1;
					return 0;
				}
			};
		}
	};

	//-------------------------------------------------------------------------------------------------------------------//
	
	public ScoreType(ScoreManager sm, String name)
	{
		this.sm = sm;
		this.name = name;
	}
	
	public ScoreType(ScoreManager sm, String name, int points, float reward)
	{
		this.sm = sm;
		this.name = name;
		this.points = points;
		this.reward = reward;
	}

	//-------------------------------------------------------------------------------------------------------------------//
	
	public void setPoints(int amount)
	{
		points = amount;
	}
	
	public void setReward(float amount)
	{
		reward = amount;
	}
	
	public int getPoints() 
	{
		return points;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public float getReward() 
	{
		return reward;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//

	public String getName() 
	{
		return name;
	}
	
	public Score getPlayerScore(String name)
	{
		for(Score score : scores)
		{
			if(score.getPlayerName().equalsIgnoreCase(name))
				return score;
		}
		return null;
	}
	
	public void addPlayerScore(Score score)
	{
		if(score == null)
			return;
		scores.add(score);
		sm.getPlayerScores(score.getPlayerName()).addScore(score);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//

}
