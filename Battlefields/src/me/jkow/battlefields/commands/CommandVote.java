package me.jkow.battlefields.commands;

import java.util.List;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.voting.Vote;
import me.jkow.battlefields.voting.VoteCustom;
import me.jkow.battlefields.voting.VoteHost;
import me.jkow.battlefields.voting.VoteKick;

import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//
// Top Command
//-------------------------------------------------------------------------------------------------------------------//
// Usage: /bf top, /bf top <typeindex>, /bf top types
//-------------------------------------------------------------------------------------------------------------------//

public class CommandVote implements BattlefieldCommand
{
	private Player p;
	private Battlefields plugin;
	private String[] args;
    
	//-------------------------------------------------------------------------------------------------------------------//
	
	public CommandVote(Player p, String[] args, Battlefields plugin)
	{
		this.p = p;
		this.args = args;
		this.plugin = plugin;
	}
    
	//-------------------------------------------------------------------------------------------------------------------//

	@Override
	public String execute() 
	{
		TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
		if(m == null)
			return "You must be on a battlefield to vote.";
		Battlefield field = m.getTeam().getField();
		Vote v;
		if(args.length == 1)
		{
			v = field.getVote();
			if(v == null)
				return "No vote is currently in progress.";
			v.vote(m);
			return null;
		}
		else if(args.length > 2)
		{
			if(args[1].equalsIgnoreCase("kick"))
			{
				List<Player> kick = plugin.getServer().matchPlayer(args[2]);
				if(kick.isEmpty())
					return args[2] + " could not be found.";
				TeamMember k = plugin.getBattlefieldManager().getPlayer(kick.get(0));
				if(k == null)
					return args[2] + " is not playing on a battlefield.";
				if(k.getTeam().getField() != field)
					return args[2] + " is not playing on your battlefield.";
				v = new VoteKick(field, m, k);
			}
			else if(args[1].equalsIgnoreCase("host"))
			{
				if(field.getHost() == m)
					return "You are already the game host.";
				v = new VoteHost(field, m);
			}
			else
				v = new VoteCustom(field, m, args[2]);
			if(field.setVote(v))
				return null;
			return "You cannot begin a vote at this time.";
			
		}
		return null;
	}
    
	//-------------------------------------------------------------------------------------------------------------------//

	@Override
	public boolean hasPermission() 
	{
		if(!p.hasPermission("battlefields.player") && !p.isOp())
			return false;
		return true;
	}

	//-------------------------------------------------------------------------------------------------------------------//
}
