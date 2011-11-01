package me.jkow.battlefields.signs;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.field.Options;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.gametypes.GametypeManager;
import me.jkow.battlefields.gametypes.GametypePlugin;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Armor;
import me.jkow.battlefields.util.Format;
import me.jkow.battlefields.util.Weapon;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SignControl extends BattlefieldSign
{
	private enum Control
	{
		START("Start"),
		RESET("Reset");
		
		private String name;
		
		private Control(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
		
	}
	
	Control control;
	int index = 0;
	
	public SignControl(Sign sign, Battlefield field) 
	{
		super(sign, field, "Controls");
		reset();
	}

	@Override
	public String getOptionName() 
	{
		if(control == null)
			return "None";
		return control.toString();
	}

	@Override
	public void cycleOption(TeamMember m) 
	{
		if(control == null)
			return;
		int i = control.ordinal()+1;
		if(i >= Control.values().length)
			i = 0;
		control = Control.values()[i];
		printOption(control.toString(), true);
		return;
	}

	@Override
	public void executeOption(TeamMember m) 
	{
		if(control == null)
			return;
		if(field.isActive())
			return;
		switch(control)
		{
		case START:
			if(!field.startGametype())
				Format.sendMessage(m, "Unable to start a new game at this time!");
			break;
		case RESET:
			Format.sendMessage(m, "Unable to reset the battlefield at this time!");
			break;
		default:
			break;
		}
	}

	@Override
	public void reset() 
	{
		control = Control.values()[0];
		printOption(getOptionName(), true);
	}

}
