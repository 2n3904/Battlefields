package com.mc3904.battlefields.signs;


import org.bukkit.block.Sign;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Armor;

public class SignArmor extends BattlefieldSign
{
	Armor armor;
	int index = 0;
	
	public SignArmor(Sign sign, Battlefield field) 
	{
		super(sign, field, "Armor");
		armor = Armor.values()[0];
		reset();
	}

	@Override
	public String getOptionName() 
	{
		if(armor == null)
			return "None";
		return armor.toString();
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(armor == null)
			return;
		int i = armor.ordinal()+1;
		if(i >= Armor.values().length)
			i = 0;
		armor = Armor.values()[i];
		printOption(armor.toString(), field.getSettings().isArmorAllowed(armor));
		return;
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(armor == null)
			return;
		if(field.isActive())
			return;
		printOption(armor.toString(), field.getSettings().toggleArmor(armor));
	}

	@Override
	public void reset() 
	{
		index = 0;
		printOption(getOptionName(), field.getSettings().isArmorAllowed(armor));
	}

}
