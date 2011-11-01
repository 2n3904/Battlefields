package com.mc3904.battlefields.signs;


import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.gametypes.Gametype;
import com.mc3904.battlefields.gametypes.GametypeManager;
import com.mc3904.battlefields.gametypes.GametypePlugin;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Armor;
import com.mc3904.battlefields.util.Format;
import com.mc3904.battlefields.util.Weapon;

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
