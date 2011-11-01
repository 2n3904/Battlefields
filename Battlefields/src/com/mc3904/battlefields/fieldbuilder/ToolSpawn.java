package com.mc3904.battlefields.fieldbuilder;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamColor;
import com.mc3904.battlefields.util.BFBlock;
import com.mc3904.battlefields.util.BFLocation;
import com.mc3904.battlefields.util.Format;


public class ToolSpawn extends Tool 
{
	private TeamColor color = TeamColor.NEUTRAL;
	private String currentList = null;
	
	@Override
	public void rightClick() 
	{
		BFLocation loc = new BFLocation(p.getLocation());
		if(color != TeamColor.NEUTRAL)
		{
			field.addSpawn(color.toString(), loc);
			fb.getPlayer().sendMessage(Format.buildTag + "Added spawn at " + Format.formatBlock(loc.getBlock()) + " to set '" + color.toString() + "'.");
			field.save();
		}
		else if(currentList != null)
		{
			if(field.addSpawn(currentList, loc))
			{
				fb.getPlayer().sendMessage(Format.buildTag + "Added spawn at " + Format.formatBlock(loc.getBlock()) + " to set '" + currentList + "'.");
				field.save();
			}
			else
				fb.getPlayer().sendMessage(Format.buildTag + "Spawn at " + Format.formatBlock(loc.getBlock()) + " already in set '" + currentList + "'.");
		}
		else if(!isAwaitingText())
		{
			StringBuilder sb = new StringBuilder();
			for(String list : field.getSpawnNames())
				sb.append(list + ", ");
			Format.sendMessage(p, "Current Spawn Sets: " + ChatColor.WHITE + sb.toString());
			Format.sendMessage(fb.getPlayer(), "Please enter in the name of the spawn set you are creating/appending.");
			this.queueText();
		}
		return;
	}
	
	@Override
	public void leftClick() 
	{
		fb.getPlayer().sendMessage(Format.buildTag + "Deleted " + color.getColorTag() + " spawn on field " + fb.getField().getName() + ".");
		field.save();
		return;
	}

	@Override
	public void cycleParameters() 
	{
		int i = color.ordinal()+1;
		if(i >= TeamColor.values().length)
			i = 0;
		this.color = TeamColor.values()[i];
		Format.sendMessage(fb.getPlayer(), "Spawn tool color changed to '" + color.getColorTag() + "'.");
		currentList = null;
		return;
	}
	
	@Override
	protected void registerText(String str)
	{
		if(field.getSpawns(str) != null)
			Format.sendMessage(fb.getPlayer(), "Selecting set '" + str + "'.");
		else
			Format.sendMessage(fb.getPlayer(), "Creating new empty spawn set '" + str + "'.");
		currentList = str;
	}
}