package me.jkow.battlefields.util;

import java.text.DecimalFormat;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.players.TeamMember;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Format 
{
	public static final String pluginTag = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "BF" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
	public static final String errorTag = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "BF Err" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
	public static final String buildTag = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "BF Edit" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
	public static final String spacer = ChatColor.DARK_GRAY + " | " + ChatColor.GRAY;
	public static final String bar = ChatColor.DARK_GRAY + "-=-------------------------------------------------=-";

	public static void sendMessage(TeamMember m, String s)
	{
		sendMessage(m.getPlayer(), s);
	}
	
	public static void sendMessage(Player p, String s)
	{
		p.sendMessage(pluginTag + s);
	}
	
	public static void sendPluginTitle(Player p)
	{
		Format.sendFancyHeader(p,"v" + Battlefields.version);
	}

	public static void sendFancyHeader(Player p, String s)
	{
		p.sendMessage(ChatColor.DARK_GRAY + "-=---" + ChatColor.DARK_AQUA + "_|_" + ChatColor.DARK_GRAY + "-------------------------------------------=-");
		p.sendMessage(ChatColor.WHITE + "    B a " + ChatColor.AQUA + "|" + ChatColor.WHITE + " t l e f i e l d s       " + s);
		p.sendMessage(ChatColor.DARK_GRAY + "-=----" + ChatColor.AQUA + "|" + ChatColor.DARK_GRAY + "--------------------------------------------=-");
	}

	public static void sendSimpleHeader(Player p, String s)
	{
		p.sendMessage(Format.bar);
		p.sendMessage(Format.pluginTag + s);
		p.sendMessage(Format.bar);
	}

	public static String formatDecimal(Double d)
	{
		DecimalFormat format = new DecimalFormat("#######0.00");
		return format.format(d);
	}
	
	public static String formatTime(int seconds)
	{
		int tminutes = (int)seconds/60;
		int tseconds = seconds - tminutes*60;
		DecimalFormat sec = new DecimalFormat("00");
		DecimalFormat min = new DecimalFormat("#0");
		return min.format(tminutes) + ":" + sec.format(tseconds);
	}
	
	public static String formatBlock(Block b)
	{
		 return ChatColor.WHITE + "(X:"+Integer.toString(b.getX())+", Y:"+Integer.toString(b.getY())+", Z:"+Integer.toString(b.getZ())+")" + ChatColor.GRAY;
	}
}
