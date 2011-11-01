package me.jkow.battlefields.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.scores.ScoreManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//
// Top Command
//-------------------------------------------------------------------------------------------------------------------//
// Usage: /bf top, /bf top <typeindex>, /bf top types
//-------------------------------------------------------------------------------------------------------------------//

public class CommandTop implements BattlefieldCommand
{
	private Player p;
	private Battlefields plugin;
	private String[] args;
	private ScoreManager sm;
    
	//-------------------------------------------------------------------------------------------------------------------//
	
	public CommandTop(Player p, String[] args, Battlefields plugin)
	{
		this.p = p;
		this.args = args;
		this.plugin = plugin;
		this.sm = plugin.getScoreManager();
	}
    
	//-------------------------------------------------------------------------------------------------------------------//

	@Override
	public String execute() 
	{/*
		if(args.length > 1)
		{
			// Display indexed score types for reference
			if(args[1].equals("types"))
			{
				Messages.messageHeader(p, "Score Types:");
				int i = 1;
				StringBuilder sb = new StringBuilder();
				for(String s : sm.getStatList())
				{
					sb.append(ChatColor.DARK_GRAY + "["+ ChatColor.WHITE + Integer.toString(i) + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + s + " , ");
					i++;
				}
				p.sendMessage(sb.toString());
				return null;
			}
			// Sort scores by type and display top
			else
			{
				int amount = 8, type = 0;
				try
				{
					type = Integer.parseInt(args[1]);
					if(args.length >2)
						amount = Integer.parseInt(args[2]);
				}
				catch(NumberFormatException e)
				{
					return "Enter integer values only.";
				}
				Stat stat = plugin.getSettings().getScoreByIndex(type);
				if(stat == null)
					return "Incorrect score index.";
				Messages.messageHeader(p, "Top Scores : " + stat.getName());
				sendScores(p, sortScoresByType(stat.getId()), amount);
				return null;
			}
		}
		// Display top points
		else
		{
			Messages.messageHeader(p, "Top Scores : Points");
			sendScores(p, sortScoresByPoints(), 8);
			return null;
		}*/return null;
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

	public void sendScores(Player p, List<String> scores, int length)
	{
		for(String s : scores)
		{
			p.sendMessage(s);
		}
	}
}
