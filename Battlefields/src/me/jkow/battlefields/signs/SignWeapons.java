package me.jkow.battlefields.signs;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.gametypes.GametypeManager;
import me.jkow.battlefields.gametypes.GametypePlugin;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;
import me.jkow.battlefields.util.Weapon;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

public class SignWeapons extends BattlefieldSign
{
	Weapon weapon;
	int index = 0;
	
	public SignWeapons(Sign sign, Battlefield field) 
	{
		super(sign, field, "Weapons");
		weapon = Weapon.values()[0];
		reset();
	}

	@Override
	public String getOptionName() 
	{
		if(weapon == null)
			return "None";
		return weapon.toString();
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(weapon == null)
			return;
		int i = weapon.ordinal()+1;
		if(i >= Weapon.values().length)
			i = 0;
		weapon = Weapon.values()[i];
		printOption(weapon.toString(), field.getSettings().isWeaponAllowed(weapon));
		return;
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(weapon == null)
			return;
		if(field.isActive())
			return;
		printOption(weapon.toString(), field.getSettings().toggleWeapon(weapon));
	}

	@Override
	public void reset() 
	{
		index = 0;
		printOption(getOptionName(), field.getSettings().isWeaponAllowed(weapon));
	}

}
