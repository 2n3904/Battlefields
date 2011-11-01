package me.jkow.battlefields.commands;

import java.util.Map;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.scores.PlayerScores;
import me.jkow.battlefields.scores.ScoreManager;
import me.jkow.battlefields.util.Format;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//
// Stats Command
//-------------------------------------------------------------------------------------------------------------------//
// Usage: /bf stats <playername>, /bf stats
//-------------------------------------------------------------------------------------------------------------------//

public class CommandStats implements BattlefieldCommand
{
	private Player p;
	private Battlefields plugin;
	private String[] args;
    
	//-------------------------------------------------------------------------------------------------------------------//
	
	public CommandStats(Player p, String[] args, Battlefields plugin)
	{
		this.p = p;
		this.args = args;
		this.plugin = plugin;
	}
    
	//-------------------------------------------------------------------------------------------------------------------//

	@Override
	public String execute() 
	{
		if(args.length < 2)
			return viewStats(p, p.getName());
		if(args[1].equalsIgnoreCase("list"))
			return viewScores(p, args[1]);
		else
			return viewStats(p, args[1]);
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

	private String viewStats(Player p, String name)
	{
		ScoreManager sm = plugin.getScoreManager();
		PlayerScores pscores = sm.getPlayerScores(name);
		if(pscores == null)
			return "Player " + ChatColor.WHITE + name + ChatColor.GRAY + " has no battlefield history!";
		Format.sendFancyHeader(p, name + "'s Statistics");
		Format.sendMessage(p, "Total Points: " + pscores.getTotalPoints());
		Format.sendMessage(p, "Total Games Won: " + pscores.getTotalGamesWon());
		Format.sendMessage(p, "Total Games Lost: " + pscores.getTotalGamesLost());
		Format.sendMessage(p, "Gametypes:");
		Map<String, Integer[]> games = pscores.getGameStats();
		for(String game : games.keySet())
			Format.sendMessage(p, "    " + game + ": [" + ChatColor.DARK_GREEN + Integer.toString(games.get(game)[1]) + ChatColor.GRAY + "/" + ChatColor.DARK_RED + Integer.toString(games.get(game)[0]) + ChatColor.GRAY + "]");
		return null;
	}
	
	private String viewScores(Player p, String name)
	{
		return null;
	}
    
	//-------------------------------------------------------------------------------------------------------------------//
}
