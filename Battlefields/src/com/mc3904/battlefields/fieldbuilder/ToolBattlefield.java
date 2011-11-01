package com.mc3904.battlefields.fieldbuilder;


import org.bukkit.ChatColor;
import org.bukkit.block.Block;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamColor;
import com.mc3904.battlefields.util.BFBlock;
import com.mc3904.battlefields.util.BFRegion;
import com.mc3904.battlefields.util.Format;

public class ToolBattlefield extends Tool 
{
	private String name = null;
	private boolean makingList = false;
	private boolean awaitingYes = false;
	
	private Block[] points = new Block[2];
	
	@Override
	public void rightClick() 
	{
		if(getRegion() == null)
		{
			Format.sendMessage(fb.getPlayer(), "You must define a region first.");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for(Battlefield list : bm.getFields())
			sb.append(list.getName() + ", ");
		Format.sendMessage(p, "Battlefields: " + ChatColor.WHITE + sb.toString());
		Format.sendMessage(fb.getPlayer(), "Please enter in the name of the battlefield.");
		this.queueText();
	}

	@Override
	public void rightClick(Block b) 
	{
		if(isAwaitingText())
			return;
		points[1] = b;
		fb.getPlayer().sendMessage(Format.buildTag + "Second position set to " + Format.formatBlock(b) + ".");
		return;
	}

	@Override
	public void leftClick() 
	{
		if(bm.getField(p) == null)
		{
			Format.sendMessage(fb.getPlayer(), "Enter in the name of the battlefield you wish to delete.");
			return;
		}/*
		return;
		Battlefield field = fm.getField(args[2]);
		if(field == null)
			return "Battlefield " + args[1] + "could not be found.";
		field.clearAll();
		fm.removeField(field);
		fm.saveFields();
		return "Battlefield " + args[1] + " successfully removed!";*/
	}

	@Override
	public void leftClick(Block b) 
	{
		if(isAwaitingText())
			return;
		points[0] = b;
		fb.getPlayer().sendMessage(Format.buildTag + "First position set to " + Format.formatBlock(b) + ".");
		return;
	}

	@Override
	public void cycleParameters() 
	{
		return;
	}
	
	@Override
	protected void registerText(String str)
	{
		if(bm.getField(str) != null)
		{
			Format.sendMessage(fb.getPlayer(), "There is already a field by the name '" + str + "'.");
			this.queueText();
			return;
		}
		BFRegion r = getRegion();
		for(Battlefield field : bm.getFields(r.getWorld()))
		{
			if(field.getRegion().intersects(r))
			{
				Format.sendMessage(p, "Defined region intersects the battlefield '" + field.getName() + "'. Creation failed.");
				return;
			}
		}
		Battlefield field = new Battlefield(str, r, plugin);
		bm.addField(field);
		field.save();
		fb.setField(field);
		Format.sendMessage(p, "Battlefield " + str + " successfully created!");
	}
	
	public BFRegion getRegion()
	{
		if(points[0] != null && points[1] != null)
			return new BFRegion(points[0], points[1]);
		return null;
	}
}
