package com.mc3904.battlefields.signs;


import org.bukkit.block.Sign;

import com.mc3904.battlefields.ConfigManager;
import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Format;

public class SignClasses extends BattlefieldSign
{
	ConfigManager cm;
	String[] classes;
	int index = 0;
	
	public SignClasses(Sign sign, Battlefield field) 
	{
		super(sign, field, "Class");
		cm = field.plugin.getConfigManager();
		classes = cm.getPlayerClassList();
		reset();
	}

	@Override
	public String getOptionName() 
	{
		if(classes == null)
			return "None";
		return classes[index];
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(classes == null)
			return;
		index++;
		if(index >= classes.length)
			index = 0;
		printOption(getOptionName(), field.getSettings().allow_classes);
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(!field.getSettings().allow_classes)
		{
			Format.sendMessage(m, "The current settings do not allow class selection.");
			return;
		}
		if(classes == null)
			return;
		if(field.isActive())
			return;
		Format.sendMessage(m, "Class changed to " + classes[index] + ".");
	}

	@Override
	public void reset() 
	{
		index = 0;
		printOption(getOptionName(), field.getSettings().allow_classes);
	}

}
