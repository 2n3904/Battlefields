package me.jkow.battlefields.players;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum TeamColor {
	NEUTRAL(ChatColor.DARK_GRAY + "[ "+ChatColor.WHITE+""+ChatColor.DARK_GRAY+" ] "+ChatColor.GRAY, "None", ChatColor.WHITE, DyeColor.WHITE), 
	RED(ChatColor.DARK_GRAY + "[ "+ChatColor.RED+"Red Team"+ChatColor.DARK_GRAY+" ] "+ChatColor.GRAY, "Red", ChatColor.RED, DyeColor.RED), 
	BLUE(ChatColor.DARK_GRAY + "[ "+ChatColor.BLUE+"Blue Team"+ChatColor.DARK_GRAY+" ] "+ChatColor.GRAY, "Blue", ChatColor.BLUE, DyeColor.BLUE), 
	YELLOW(ChatColor.DARK_GRAY + "[ "+ChatColor.GOLD+"Yellow Team"+ChatColor.DARK_GRAY+" ] "+ChatColor.GRAY, "Yellow", ChatColor.YELLOW, DyeColor.YELLOW), 
	GREEN(ChatColor.DARK_GRAY + "[ "+ChatColor.GREEN+"Green Team"+ChatColor.DARK_GRAY+" ] "+ChatColor.GRAY, "Green", ChatColor.GREEN, DyeColor.GREEN);
	
	private String tag, colortag;
	private ChatColor color;
	private DyeColor dye;
	private TeamColor(String tag, String colortag, ChatColor color, DyeColor dye)
	{
		this.tag = tag;
		this.colortag = colortag;
		this.color = color;
		this.dye = dye;
	}
	
	public String getTeamTag()
	{
		return tag;
	}
	
	@Override
	public String toString()
	{
		return colortag;
	}
	
	public ChatColor getChatColor()
	{
		return color;
	}
	
	public DyeColor getDyeColor()
	{
		return dye;
	}
	
	public String getColorTag()
	{
		return colorize(colortag);
	}
	
	public String colorize(String string)
	{
		return color + string + ChatColor.GRAY;
	}
}
