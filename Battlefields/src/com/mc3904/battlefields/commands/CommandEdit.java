package com.mc3904.battlefields.commands;


import org.bukkit.entity.Player;

import com.mc3904.battlefields.Battlefields;
import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.field.BattlefieldManager;
import com.mc3904.battlefields.fieldbuilder.Builder;

//-------------------------------------------------------------------------------------------------------------------//
// Edit Command
//-------------------------------------------------------------------------------------------------------------------//
// Usage: /bf edit
//-------------------------------------------------------------------------------------------------------------------//

public class CommandEdit extends BattlefieldCommand
{
	public CommandEdit(Player p, String[] args, Battlefields plugin) 
	{
		super(p, args, plugin);
	}

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
