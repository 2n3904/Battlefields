package me.jkow.battlefields.voting;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;

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
