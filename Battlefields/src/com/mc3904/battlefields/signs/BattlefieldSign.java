package com.mc3904.battlefields.signs;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Stringable;

public abstract class BattlefieldSign
{	
	Battlefield field;
	Sign sign;
	String name;
	
	public BattlefieldSign(Sign sign, Battlefield field, String name)
	{
		this.field = field;
		this.sign = sign;
		this.name = name;
	}
	
	public String getDisplayName()
	{
		return name;
	}
	
	public void printOption(String s, Boolean bool)
	{
		String[] split = s.split(" ", 2);
		ChatColor color = ChatColor.DARK_RED;
		if(bool)
			color = ChatColor.DARK_GREEN;
		for(int i=0; i<2; i++)
		{
			if(split.length > i)
				sign.setLine(2+i, color + split[i]);
			else
				sign.setLine(2+i, "");
		}
		sign.update();
	}
	
	public void printOption(String s)
	{
		printOption(s, true);
	}

	public abstract String getOptionName();
	
	public abstract void cycleOption(TeamMember m);
	
	public abstract void executeOption(TeamMember m);
	
	public abstract void reset();
}
