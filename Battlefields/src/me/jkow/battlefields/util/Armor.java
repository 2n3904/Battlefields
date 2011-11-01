package me.jkow.battlefields.util;

import org.bukkit.ChatColor;

public enum Armor 
{		
	LEATHER_PLATE("Leather Chestplate", 299, ChatColor.GOLD, 1),
	LEATHER_BOOTS("Leather Boots", 301, ChatColor.GOLD, 3),
	LEATHER_PANTS("Leather Leggings", 300, ChatColor.GOLD, 2),
	LEATHER_HELMET("Leather Helmet", 298, ChatColor.GOLD, 0),
	IRON_PLATE("Iron Chestplate", 307, ChatColor.WHITE, 1),
	IRON_BOOTS("Iron Boots", 309, ChatColor.WHITE, 3),
	IRON_PANTS("Iron Leggings", 308, ChatColor.WHITE, 2),
	IRON_HELMET("Iron Helmet", 306, ChatColor.WHITE, 0),
	GOLD_PLATE("Gold Chestplate", 315, ChatColor.YELLOW, 1),
	GOLD_BOOTS("Gold Boots", 317, ChatColor.YELLOW, 3),
	GOLD_PANTS("Gold Leggings", 316, ChatColor.YELLOW, 2),
	GOLD_HELMET("Gold Helmet", 314, ChatColor.YELLOW, 0),
	CHAINMAIL_PLATE("Chainmail Chestplate", 303, ChatColor.DARK_GRAY, 1),
	CHAINMAIL_BOOTS("Chainmail Boots", 305, ChatColor.DARK_GRAY, 3),
	CHAINMAIL_PANTS("Chainmail Leggings", 304, ChatColor.DARK_GRAY, 2),
	CHAINMAIL_HELMET("Chainmail Helmet", 302, ChatColor.DARK_GRAY, 0),
	DIAMOND_PLATE("Diamond Chestplate", 311, ChatColor.AQUA, 1),
	DIAMOND_BOOTS("Diamond Boots", 313, ChatColor.AQUA, 3),
	DIAMOND_PANTS("Diamond Leggings", 312, ChatColor.AQUA, 2),
	DIAMOND_HELMET("Diamond Helmet", 310, ChatColor.AQUA, 0);
	
	private int id;
	private String name;
	private ChatColor color;
	private int slot;
	
	Armor(String name, int id, ChatColor color, int slot)
	{
		this.name = name;
		this.id = id;
		this.color = color;
		this.slot = slot;
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
	
	public int getSlot()
	{
		return slot;
	}
	
	public String getTag()
	{
		return color + name + ChatColor.GRAY;
	}
}
