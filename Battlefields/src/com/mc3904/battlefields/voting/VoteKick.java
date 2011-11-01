package com.mc3904.battlefields.voting;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Format;


public class VoteKick extends Vote
{
	private TeamMember kick;
	
	public VoteKick(Battlefield field, TeamMember m, TeamMember kick) {
		super(field, m);
		this.kick = kick;
	}

	@Override
	public void onSuccess() 
	{
		field.kickPlayer(kick);
		field.sendMessageToAll(Format.pluginTag + "Player " + kick.getColoredName() + " has been kicked from the game.");
	}

	@Override
	public void onEnable() 
	{
		field.sendMessageToAll(Format.pluginTag + "Kick player " + kick.getColoredName() + "?");
	}
}
