package com.mc3904.battlefields.field;

import java.util.HashMap;
import java.util.Map;

import com.mc3904.battlefields.util.Armor;
import com.mc3904.battlefields.util.Weapon;


public class BattlefieldSettings 
{
	
	Armor[] armor = new Armor[4];
	Map<Weapon, Boolean> weapons = new HashMap<Weapon, Boolean>();
	
	public int time_limit = 5;
	public boolean spawn_protection = true;
	public boolean join_during = false;
	public boolean instant_spawn = true;
	public boolean allow_classes = true;
	public boolean team_kills = false;
	
	public BattlefieldSettings()
	{
		for(Weapon w : Weapon.values())
			weapons.put(w, false);
	}
	
	public boolean isArmorAllowed(Armor a)
	{
		if(armor[a.getSlot()] == a)
			return true;
		return false;
	}

	public boolean isWeaponAllowed(Weapon w)
	{
		return weapons.get(w);
	}
	
	public boolean toggleArmor(Armor a)
	{
		if(isArmorAllowed(a))
		{
			armor[a.getSlot()] = null;
			return false;
		}
		armor[a.getSlot()] = a;
		return true;
	}
	
	public boolean toggleWeapon(Weapon w)
	{
		boolean b = !weapons.get(w);
		weapons.put(w, b);
		return b;
	}
	
	public boolean getFlag(Options o)
	{
		switch(o)
		{
		case TIME_LIMIT: return true;
		case ALLOW_CLASSES: return allow_classes;
		case SPAWN_PROTECTION: return spawn_protection;
		case JOIN_DURING: return join_during;
		case INSTANT_SPAWN: return instant_spawn;
		default: return false;
		}
	}
	
	public void changeSetting(Options o)
	{
		switch(o)
		{
		case TIME_LIMIT:
			time_limit += 5;
			if(time_limit > 30)
				time_limit = 5;
			return;
		case ALLOW_CLASSES:
			allow_classes = !allow_classes;
			return;
		case SPAWN_PROTECTION:
			spawn_protection = !spawn_protection;
			return;
		case JOIN_DURING:
			join_during = !join_during;
			return;
		case INSTANT_SPAWN:
			instant_spawn = !instant_spawn;
			return;
		default:
			return;
		}
	}
}
