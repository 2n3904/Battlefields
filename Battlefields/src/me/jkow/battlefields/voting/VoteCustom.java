package me.jkow.battlefields.voting;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;

public class VoteCustom extends Vote
{
	private String message;
	
	public VoteCustom(Battlefield field, TeamMember m, String message) {
		super(field, m);
		this.message = message;
	}

	@Override
	public void onSuccess() 
	{
		field.sendMessageToAll(Format.pluginTag + "Vote successful for: " + message);
	}

	@Override
	public void onEnable() 
	{
		field.sendMessageToAll(Format.pluginTag + message);
	}
}