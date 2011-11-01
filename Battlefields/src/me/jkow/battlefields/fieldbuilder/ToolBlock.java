package me.jkow.battlefields.fieldbuilder;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.players.TeamColor;
import me.jkow.battlefields.util.BFBlock;
import me.jkow.battlefields.util.Format;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

public class ToolBlock extends Tool 
{
	private TeamColor color = TeamColor.NEUTRAL;
	private Block lastBlock = null;
	private String currentList = null;
	private boolean makingList = false;
	
	@Override
	public void rightClick(Block b) 
	{
		lastBlock = b;
		if(color != TeamColor.NEUTRAL)
		{
			field.addBlock(color.toString(), new BFBlock(b));
			fb.getPlayer().sendMessage(Format.buildTag + "Added block at " + Format.formatBlock(b) + " to set '" + color.toString() + "'.");
			field.save();
		}
		else if(currentList != null)
		{
			if(field.addBlock(currentList, new BFBlock(lastBlock)))
			{
				fb.getPlayer().sendMessage(Format.buildTag + "Added block at " + Format.formatBlock(b) + " to set '" + currentList + "'.");
				field.save();
			}
			else
				fb.getPlayer().sendMessage(Format.buildTag + "Block at " + Format.formatBlock(b) + " already in set '" + currentList + "'.");
		}
		else if(!isAwaitingText())
		{
			Format.sendMessage(fb.getPlayer(), "Current Block Sets:");
			StringBuilder sb = new StringBuilder();
			for(String list : field.getBlockNames())
				sb.append(list + ", ");
			Format.sendMessage(p, "Current Block Sets: " + ChatColor.WHITE + sb.toString());
			Format.sendMessage(fb.getPlayer(), "Please enter in the name of the block set you are creating/appending.");
			this.queueText();
		}
		return;
	}
	
	@Override
	public void leftClick(Block b) 
	{
		fb.getPlayer().sendMessage(Format.buildTag + "Deleted " + color.getColorTag() + " block on field " + fb.getField().getName() + ".");
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
		Format.sendMessage(fb.getPlayer(), "Block tool color changed to '" + color.getColorTag() + "'.");
		currentList = null;
		return;
	}
	
	@Override
	protected void registerText(String str)
	{
		if(field.getBlocks(str) != null)
			Format.sendMessage(fb.getPlayer(), "Selecting set '" + str + "'.");
		else
			Format.sendMessage(fb.getPlayer(), "Creating new empty block set '" + str + "'.");
		currentList = str;
	}
}
