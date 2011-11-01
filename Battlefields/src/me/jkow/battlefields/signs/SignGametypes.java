package me.jkow.battlefields.signs;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.gametypes.GametypeManager;
import me.jkow.battlefields.gametypes.GametypePlugin;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class SignGametypes extends BattlefieldSign
{
	GametypeManager gm;
	String[] gametypes;
	int index = 0;
	
	public SignGametypes(Sign sign, Battlefield field) 
	{
		super(sign, field, "Gametype");
		gm = field.plugin.getGametypeManager();
		gametypes = gm.getGametypes();
		index = 0;
		printOption(gametypes[index], false);
	}

	@Override
	public String getOptionName() 
	{
		if(gametypes == null)
			return "None";
		return gametypes[index];
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(gametypes == null)
			return;
		if(field.getHost() == null)
			return;
		if(field.getHost().getPlayer() != m.getPlayer())
			return;
		index++;
		if(index >= gametypes.length)
			index = 0;
		boolean bool = false;
		Gametype g = field.getGametype();
		if(g != null)
		{
			if(g.getGametypePlugin() == gm.getGametype(gametypes[index]))
				bool = true;
		}
		printOption(gametypes[index], bool);
		return;
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(gametypes == null)
			return;
		if(field.getHost() == null)
			return;
		if(field.getHost().getPlayer() != m.getPlayer())
			return;
		if(field.isActive())
			return;
		GametypePlugin gp = gm.getGametype(gametypes[index]);
		if(gp == null)
			return;
		Gametype g = gp.newGametype(field);
		if(g == null)
		{
			Bukkit.getLogger().warning("[Battlefields] Could not retrieve gametype '" + gametypes[index] + "'.");
		}
		field.sendMessageToAll(Format.pluginTag + "Gametype changed to " + gametypes[index] + ".");
		field.setGametype(g);
		printOption(gametypes[index], true);
	}

	@Override
	public void reset() {}
}
