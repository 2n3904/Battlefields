package me.jkow.battlefields.signs;

import me.jkow.battlefields.ConfigManager;
import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.field.BattlefieldManager;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.gametypes.GametypeManager;
import me.jkow.battlefields.gametypes.GametypePlugin;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

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
