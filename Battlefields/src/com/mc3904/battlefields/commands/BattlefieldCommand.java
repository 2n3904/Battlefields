package com.mc3904.battlefields.commands;


public interface BattlefieldCommand 
{
	public boolean hasPermission();
	
	public String execute();
}
