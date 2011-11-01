package me.jkow.battlefields.commands;


public interface BattlefieldCommand 
{
	public boolean hasPermission();
	
	public String execute();
}
