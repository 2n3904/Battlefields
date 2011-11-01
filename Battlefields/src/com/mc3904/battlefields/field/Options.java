package com.mc3904.battlefields.field;


public enum Options
{
	TIME_LIMIT("Time Limit"),
	JOIN_DURING("Join During"),
	ALLOW_CLASSES("Classes"),
	SPAWN_PROTECTION("Spawn Protection"),
	INSTANT_SPAWN("Instant Spawn");
	
	String name;
	
	private Options(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}