package com.mc3904.battlefields.fieldbuilder;

import java.util.HashMap;
import java.util.Map;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.field.BattlefieldManager;
import com.mc3904.battlefields.util.Format;

public class Builder 
{	
	private enum ToolType
	{
		FIELD("Battlefield", ToolBattlefield.class),
		BLOCK("Block", ToolBlock.class),
		SPAWN("Spawnpoint", ToolSpawn.class),
		REGION("Subregion", ToolRegion.class),
		SIGN("Sign", ToolSign.class);
		
		String name;
		Class<?> tool;
		
		private ToolType(String name, Class<?> c)
		{
			this.name = name;
			this.tool = c;
		}
		
		public Tool getToolInstance(Builder fb)
		{
			try {
				Tool newtool = (Tool)tool.newInstance();
				newtool.initialize(fb);
				return newtool;
			} catch (Exception e) {
				return null;
			}
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
	
	private ToolType tool;

	private Battlefield field;
	private BattlefieldManager bm;
	private Player p;
	
	private Map<ToolType, Tool> tools = new HashMap<ToolType, Tool>();
	
	public Builder(Player p, BattlefieldManager bm, Battlefield field)
	{
		this.field = field;
		this.p = p;
		this.bm = bm;
		for(ToolType type : ToolType.values())
			tools.put(type, type.getToolInstance(this));
		this.tool = ToolType.values()[0];
	}
	
	public Builder(Player p, BattlefieldManager bm)
	{
		this.field = null;
		this.p = p;
		this.bm = bm;
		for(ToolType type : ToolType.values())
			tools.put(type, type.getToolInstance(this));
		this.tool = ToolType.FIELD;
	}
	
	public Tool getTool()
	{
		return tools.get(tool);
	}
		
	public boolean setTool(ToolType tool)
	{
		if(!tools.containsKey(tool))
			return false;
		this.tool = tool;
		return true;
	}
	
	public void cycleTool()
	{
		if(field == null)
		{
			Format.sendMessage(p, "No battlefield selected. Must use the battlefield tool first.");
			return;
		}
		int i = tool.ordinal()+1;
		if(i >= ToolType.values().length)
			i = 0;
		this.tool = ToolType.values()[i];
		Format.sendMessage(p, "Tool type changed to '" + ChatColor.WHITE + tool.toString() + ChatColor.GRAY + "'.");
		return;
	}
	
	public Battlefield getField()
	{
		return field;
	}
	
	public BattlefieldManager getBattlefieldManager()
	{
		return bm;
	}
	
	public void setField(Battlefield field)
	{
		this.field = field;
	}
	
	public Player getPlayer()
	{
		return p;
	}
}
