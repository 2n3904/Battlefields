package com.mc3904.battlefields.voting;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Format;


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