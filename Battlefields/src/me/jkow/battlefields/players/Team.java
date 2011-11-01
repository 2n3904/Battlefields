package me.jkow.battlefields.players;

import java.util.ArrayList;
import java.util.List;

import me.jkow.battlefields.field.Battlefield;

//-------------------------------------------------------------------------------------------------------------------//

public class Team 
{
	private final Battlefield field;
	
	// Team color
	private TeamColor color;
	
	// Players in team
	private List<TeamMember> members = new ArrayList<TeamMember>();
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public Team(TeamColor color, Battlefield instance)
	{
		this.field = instance;
		this.color = color;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//

	// Return team color
	public TeamColor getColor() 
	{
		return color;
	}

	//-------------------------------------------------------------------------------------------------------------------//
	
	// Return team's battlefield
	public Battlefield getField() 
	{
		return field;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Return list of all members
	public List<TeamMember> getMembers()
	{
		return members;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Check to see if team has members
	public boolean hasMembers()
	{
		return !members.isEmpty();
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Send a message to all players on the team
	public void sendMessageToAll(String message)
	{
		for(TeamMember m : members)
			m.getPlayer().sendMessage(message);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Reset players
	public void reset()
	{
		for(TeamMember m : members)
		{
			m.reset();
			m.heal();
			//m.restoreInventory();
		}
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public void addMember(TeamMember m)
	{
		members.add(m);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public void removeMember(TeamMember m)
	{
		members.remove(m);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
}
