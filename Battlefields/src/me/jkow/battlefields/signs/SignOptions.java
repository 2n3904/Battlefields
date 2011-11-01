package me.jkow.battlefields.signs;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.gametypes.GametypeManager;
import me.jkow.battlefields.players.TeamMember;

import org.bukkit.block.Sign;

public class SignOptions extends BattlefieldSign
{
	GametypeManager gm;
	String[] options;
	int index = 0;
	
	public SignOptions(Sign sign, Battlefield field) 
	{
		super(sign, field, "Options");
		gm = field.plugin.getGametypeManager();
		reset();
	}
	
	@Override
	public String getOptionName() 
	{
		if(options == null)
			return "None";
		return options[index];
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(field.getHost() == null)
			return;
		if(field.getHost().getPlayer() != m.getPlayer())
			return;
		if(options == null)
			return;
		index++;
		if(index >= options.length)
			index = 0;
		printOption(getOptionName());
		return;
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(options == null)
			return;
		Gametype g = field.getGametype();
		if(g == null)
			return;
		g.getListener().onOptionChange(m, options[index]);
	}

	@Override
	public void reset() 
	{
		Gametype g = field.getGametype();
		if(g == null)
		{
			printOption("None", false);
			return;
		}
		String[] opt = g.getGametypePlugin().getDescription().getOptions();
		options = opt;
		index = 0;
		printOption(getOptionName());
		sign.update();
	}
}
