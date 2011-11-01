package com.mc3904.battlefields.gametypes;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.signs.BattlefieldSign;


public abstract class Gametype 
{	
	public GametypeListener listener = null;
	public Battlefield field;
	public GametypePlugin gp;
	public BattlefieldSign sign = null;
	
	public Gametype(GametypePlugin gp, Battlefield field)
	{
		this.field = field;
		this.gp = gp;
	}
	
	// Get the listener for this gametype
	public GametypeListener getListener()
	{
		return listener;
	}
	
	// Get the field this gametype applies to
	public Battlefield getField()
	{
		return field;
	}

	// Get the gametype plugin this gametype applies to
	public GametypePlugin getGametypePlugin()
	{
		return gp;
	}

	public BattlefieldSign getOptionSign()
	{
		return sign;
	}
	
	// Return the leading team in the game
	public abstract Object getWinner();
	
	// Check whether field is correctly setup
	public abstract boolean checkField();
	
	// Check for a victory condition
	public abstract boolean checkVictory();
}
