package me.jkow.battlefields.fieldbuilder;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.players.TeamColor;
import me.jkow.battlefields.util.BFBlock;
import me.jkow.battlefields.util.Format;
import me.jkow.battlefields.util.BFRegion;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

public class ToolRegion extends Tool 
{
	private TeamColor color = TeamColor.NEUTRAL;
	private String currentList = null;
	private boolean makingList = false;
	
	private Block[] points = new Block[2];
	
	@Override
	public void rightClick() 
	{
		if(color != TeamColor.NEUTRAL)
			addRegion(color.toString());
		if(isAwaitingText())
			cancelQueueText();
		StringBuilder sb = new StringBuilder();
		for(String list : field.getSubregionNames())
			sb.append(list + ", ");
		Format.sendMessage(p, "Current Subregions: " + ChatColor.WHITE + sb.toString());
		Format.sendMessage(fb.getPlayer(), "Please enter in the name of the region you wish to create/overwrite.");
		this.queueText();	
		return;
	}

	@Override
	public void rightClick(Block b) 
	{
		points[1] = b;
		fb.getPlayer().sendMessage(Format.buildTag + "Second position set to " + Format.formatBlock(b) + ".");
		return;
	}

	@Override
	public void leftClick() 
	{
		if(field == null)
			return;
		int removed = field.removeRegion(fb.getPlayer().getLocation());
		fb.getPlayer().sendMessage(Format.buildTag + "Removed " + Integer.toString(removed) + " regions from field " + fb.getField().getName() + ".");
		return;
	}

	@Override
	public void leftClick(Block b) 
	{
		points[0] = b;
		fb.getPlayer().sendMessage(Format.buildTag + "First position set to " + Format.formatBlock(b) + ".");
		return;
	}

	@Override
	public void cycleParameters() 
	{
		int i = color.ordinal()+1;
		if(i >= TeamColor.values().length)
			i = 0;
		this.color = TeamColor.values()[i];
		fb.getPlayer().sendMessage(Format.buildTag + "Region tool color changed to '" + color.getColorTag() + "'.");
		currentList = null;
		return;
	}
	
	@Override
	protected void registerText(String str)
	{
		addRegion(str);
	}
	
	public BFRegion getRegion()
	{
		if(points[0] != null && points[1] != null)
			return new BFRegion(points[0], points[1]);
		return null;
	}
	
	private void addRegion(String str)
	{
		BFRegion r = getRegion();
		if(r == null)
			Format.sendMessage(fb.getPlayer(), "Region corners not yet chosen!");
		else if(!field.getRegion().contains(r))
			Format.sendMessage(fb.getPlayer(), "Region must be inside the battlefield.");
		else if(field.addSubregion(str, r))
		{
			Format.sendMessage(fb.getPlayer(), "Added subregion '" + ChatColor.WHITE + str + ChatColor.GRAY + "'.");
			field.save();
		}
		else
			Format.sendMessage(fb.getPlayer(), "Replaced subregion '" + ChatColor.WHITE + str + ChatColor.GRAY + "'.");
	}
}
