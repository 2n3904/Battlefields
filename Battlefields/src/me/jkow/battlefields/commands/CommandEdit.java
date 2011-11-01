package me.jkow.battlefields.commands;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.field.BattlefieldManager;
import me.jkow.battlefields.fieldbuilder.Builder;

import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//
// Edit Command
//-------------------------------------------------------------------------------------------------------------------//
// Usage: /bf edit
//-------------------------------------------------------------------------------------------------------------------//

public class CommandEdit implements BattlefieldCommand
{
	private Player p;
	private Battlefields plugin;
	private String[] args;
    
	//-------------------------------------------------------------------------------------------------------------------//
	
	public CommandEdit(Player p, String[] args, Battlefields plugin)
	{
		this.p = p;
		this.args = args;
		this.plugin = plugin;
	}
    
	//-------------------------------------------------------------------------------------------------------------------//

	@Override
	public String execute() 
	{
		BattlefieldManager bm = plugin.getBattlefieldManager();
		if(bm.removeBuilder(p) != null)
		{
			return "Edit mode disabled.";
		}
		Battlefield field =bm.getField(p);
		Builder bld = new Builder(p, bm, field);
		plugin.getBattlefieldManager().addBuilder(p, bld);
		if(field == null)
			return "Edit mode selected. Must create a battlefield.";
		else
			return "Edit mode selected for battlefield: " + field.getName();
	}
    
	//-------------------------------------------------------------------------------------------------------------------//

	@Override
	public boolean hasPermission() 
	{
		if(!p.hasPermission("battlefields.admin") && !p.isOp())
			return false;
		return true;
	}
    
	//-------------------------------------------------------------------------------------------------------------------//
}
