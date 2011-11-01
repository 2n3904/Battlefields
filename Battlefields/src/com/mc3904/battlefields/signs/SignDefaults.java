package com.mc3904.battlefields.signs;


import org.bukkit.block.Sign;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.field.Options;
import com.mc3904.battlefields.players.TeamMember;

public class SignDefaults extends BattlefieldSign
{
	private Options option;
	
	public SignDefaults(Sign sign, Battlefield field) 
	{
		super(sign, field, "Defaults");
		this.option = Options.values()[0];
		reset();
	}

	@Override
	public String getOptionName() 
	{
		return option.getName();
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(field.getHost() != m)
			return;
		int i = option.ordinal()+1;
		if(i >= Options.values().length)
			i = 0;
		this.option = Options.values()[i];
		printOption(option.getName(), field.getSettings().getFlag(option));
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(field.getHost() != m)
			return;
		if(field.isActive())
			return;
		field.getSettings().changeSetting(option);
		printOption(option.getName(), field.getSettings().getFlag(option));
	}

	@Override
	public void reset() 
	{
		this.option = Options.values()[0];
		printOption(option.getName(), field.getSettings().getFlag(option));
	}
}
