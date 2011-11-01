package me.jkow.battlefields.commands;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.util.Format;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//

public class BattlefieldCommandMap implements CommandExecutor
{
	Battlefields plugin;
    
	//-------------------------------------------------------------------------------------------------------------------//
    
	public BattlefieldCommandMap(Battlefields plugin)
	{
		this.plugin = plugin;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
    // Command Handler
	//-------------------------------------------------------------------------------------------------------------------//
    
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		Player p = (Player) sender;
		// Info command
		if(args.length == 0)
			return true;
		String cmd = args[0].toLowerCase();
		BattlefieldCommand bfcmd;
		// Get command
		if(cmd.equals("edit"))
			bfcmd = new CommandEdit(p, args, plugin);
		else if(cmd.equals("score"))
			bfcmd = new CommandScore(p, args, plugin);
		else if(cmd.equals("stats"))
			bfcmd = new CommandStats(p, args, plugin);
		else if(cmd.equals("top"))
			bfcmd = new CommandTop(p, args, plugin);
		else if(cmd.equals("vote"))
			bfcmd = new CommandVote(p, args, plugin);
		// If no command was found, return
		else return true;
		// Try to execute command
		if(bfcmd.hasPermission())
		{
			String msg = bfcmd.execute();
			if(msg != null)
				p.sendMessage(Format.pluginTag + msg);
		}
		else
			p.sendMessage(Format.errorTag + "You do not have permission to use this command.");	
		return true;
	}
}
