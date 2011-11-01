package me.jkow.battlefields.commands;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.players.TeamMember;

import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//
// Score Command
//-------------------------------------------------------------------------------------------------------------------//
// Usage: /bf score, /bf score <team>
//-------------------------------------------------------------------------------------------------------------------//

public class CommandScore implements BattlefieldCommand
{
	private Player p;
	private Battlefields plugin;
	private String[] args;
    
	//-------------------------------------------------------------------------------------------------------------------//
	
	public CommandScore(Player p, String[] args, Battlefields plugin)
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
		if(m != null)
		{
			Gametype g = m.getTeam().getField().getGametype();
			if(g != null)
			{
				
			}
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
