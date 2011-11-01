package com.mc3904.battlefields.util;

import org.bukkit.ChatColor;

public enum Weapon 
{
	WOOD_HOE("Wooden Hoe", 290, ChatColor.GOLD),
	WOOD_SWORD("Wooden Sword", 268, ChatColor.GOLD),
	WOOD_SHOVEL("Wooden Shovel", 269, ChatColor.GOLD),
	WOOD_PICK("Wooden Pick", 270, ChatColor.GOLD),
	WOOD_AXE("Wooden Axe", 271, ChatColor.GOLD),
	STONE_HOE("Stone Hoe", 291, ChatColor.DARK_GRAY),
	STONE_SWORD("Stone Sword", 272, ChatColor.DARK_GRAY),
	STONE_SHOVEL("Stone Shovel", 273, ChatColor.DARK_GRAY),
	STONE_PICK("Stone Pick", 274, ChatColor.DARK_GRAY),
	STONE_AXE("Stone Axe", 275, ChatColor.DARK_GRAY),
	IRON_HOE("Iron Hoe", 292, ChatColor.WHITE),
	IRON_SWORD("Iron Sword", 267, ChatColor.WHITE),
	IRON_SHOVEL("Iron Shovel", 256, ChatColor.WHITE),
	IRON_PICK("Iron Pick", 257, ChatColor.WHITE),
	IRON_AXE("Iron Axe", 258, ChatColor.WHITE),
	GOLD_HOE("Golden Hoe", 294, ChatColor.YELLOW),
	GOLD_SWORD("Golden Sword", 283, ChatColor.YELLOW),
	GOLD_SHOVEL("Golden Shovel", 284, ChatColor.YELLOW),
	GOLD_PICK("Golden Pick", 285, ChatColor.YELLOW),
	GOLD_AXE("Golden Axe", 286, ChatColor.YELLOW),
	DIAMOND_HOE("Diamond Hoe", 293, ChatColor.AQUA),
	DIAMOND_SWORD("Diamond Sword", 276, ChatColor.AQUA),
	DIAMOND_SHOVEL("Diamond Shovel", 277, ChatColor.AQUA),
	DIAMOND_PICK("Diamond Pick", 278, ChatColor.AQUA),
	DIAMOND_AXE("Diamond Axe", 279, ChatColor.AQUA),
	BOW("Bow", 261, ChatColor.RED),
	EGG("Egg", 344, ChatColor.WHITE),
	SNOWBALL("Snowball", 332, ChatColor.WHITE),
	UNARMED("Fists", 0, ChatColor.WHITE),
	ARROW("Arrow", 262, ChatColor.RED);
	
	private int id;
	private String name;
	private ChatColor color;
	
	Weapon(String name, int id, ChatColor color)
	{
		this.name = name;
		this.id = id;
		this.color = color;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getTag()
	{
		return color + name + ChatColor.GRAY;
	}
}
