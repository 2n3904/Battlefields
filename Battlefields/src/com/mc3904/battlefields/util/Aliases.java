package com.mc3904.battlefields.util;

import org.bukkit.ChatColor;

public class Aliases {
	// RETURN ARMOR NAME
	public static Armor getArmor(int ID)
	{
		switch(ID)
		{
		case 299: return Armor.LEATHER_PLATE;
		case 301: return Armor.LEATHER_BOOTS;
		case 300: return Armor.LEATHER_PANTS;
		case 298: return Armor.LEATHER_HELMET;
		case 307: return Armor.IRON_PLATE;
		case 309: return Armor.IRON_BOOTS;
		case 308: return Armor.IRON_PANTS;
		case 306: return Armor.IRON_HELMET;
		case 315: return Armor.GOLD_PLATE;
		case 317: return Armor.GOLD_BOOTS;
		case 316: return Armor.GOLD_PANTS;
		case 314: return Armor.GOLD_HELMET;
		case 303: return Armor.CHAINMAIL_PLATE;
		case 305: return Armor.CHAINMAIL_BOOTS;
		case 304: return Armor.CHAINMAIL_PANTS;
		case 302: return Armor.CHAINMAIL_HELMET;
		case 311: return Armor.DIAMOND_PLATE;
		case 313: return Armor.DIAMOND_BOOTS;
		case 312: return Armor.DIAMOND_PANTS;
		case 310: return Armor.DIAMOND_HELMET;
		default: return null;
		}
	}
	
	// RETURN WEAPON NAME
	public static String getWeapon(int ID)
	{
		switch(ID)
		{
		case 290: return ChatColor.GOLD + "Wooden Hoe" + ChatColor.GRAY;
		case 268: return ChatColor.GOLD + "Wooden Sword" + ChatColor.GRAY;
		case 269: return ChatColor.GOLD + "Wooden Shovel" + ChatColor.GRAY;
		case 270: return ChatColor.GOLD + "Wooden Pick" + ChatColor.GRAY;
		case 271: return ChatColor.GOLD + "Wooden Axe" + ChatColor.GRAY;
		case 291: return ChatColor.DARK_GRAY + "Stone Hoe" + ChatColor.GRAY;
		case 272: return ChatColor.DARK_GRAY + "Stone Sword" + ChatColor.GRAY;
		case 273: return ChatColor.DARK_GRAY + "Stone Shovel" + ChatColor.GRAY;
		case 274: return ChatColor.DARK_GRAY + "Stone Pick" + ChatColor.GRAY;
		case 275: return ChatColor.DARK_GRAY + "Stone Axe" + ChatColor.GRAY;
		case 292: return ChatColor.WHITE + "Iron Hoe" + ChatColor.GRAY;
		case 267: return ChatColor.WHITE + "Iron Sword" + ChatColor.GRAY;
		case 256: return ChatColor.WHITE + "Iron Shovel" + ChatColor.GRAY;
		case 257: return ChatColor.WHITE + "Iron Pick" + ChatColor.GRAY;
		case 258: return ChatColor.WHITE + "Iron Axe" + ChatColor.GRAY;
		case 294: return ChatColor.YELLOW + "Golden Hoe" + ChatColor.GRAY;
		case 283: return ChatColor.YELLOW + "Golden Sword" + ChatColor.GRAY;
		case 284: return ChatColor.YELLOW + "Golden Shovel" + ChatColor.GRAY;
		case 285: return ChatColor.YELLOW + "Golden Pick" + ChatColor.GRAY;
		case 286: return ChatColor.YELLOW + "Golden Axe" + ChatColor.GRAY;
		case 293: return ChatColor.AQUA + "Diamond Hoe" + ChatColor.GRAY;
		case 276: return ChatColor.AQUA + "Diamond Sword" + ChatColor.GRAY;
		case 277: return ChatColor.AQUA + "Diamond Shovel" + ChatColor.GRAY;
		case 278: return ChatColor.AQUA + "Diamond Pick" + ChatColor.GRAY;
		case 279: return ChatColor.AQUA + "Diamond Axe" + ChatColor.GRAY;
		case 261: return ChatColor.RED + "Bow" + ChatColor.GRAY;
		case 344: return ChatColor.WHITE + "Egg" + ChatColor.GRAY;
		case 332: return ChatColor.WHITE + "Snowball" + ChatColor.GRAY;
		case 262: return ChatColor.GOLD + "Arrow" + ChatColor.GRAY;
		default: return "Unarmed";
		}
	}
}
