package me.jkow.battlefields.voting;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;

public class VoteHost extends Vote
{	
	public VoteHost(Battlefield field, TeamMember m) {
		super(field, m);
	}

	@Override
	public void onSuccess() 
	{
		field.setHost(m);
		field.sendMessageToAll(Format.pluginTag + "Player " + m.getColoredName() + " is now the game host.");
	}

	@Override
	public void onEnable() 
	{
		field.sendMessageToAll(Format.pluginTag + "Change host to player " + m.getColoredName() + "?");
	}
}
