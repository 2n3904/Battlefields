package com.mc3904.battlefields.commands;

import org.bukkit.entity.Player;

import com.mc3904.battlefields.Battlefields;


public abstract class BattlefieldCommand 
{

	protected Player p;
	protected Battlefields plugin;
	protected String[] args;
	
	public BattlefieldCommand(Player p, String[] args, Battlefields plugin)
	{
		this.p = p;
		this.args = args;
		this.plugin = plugin;
	}
	
	public abstract boolean hasPermission();
	
	public abstract String execute();
}
