package me.jkow.battlefields.fieldbuilder;

import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.signs.BattlefieldSign;
import me.jkow.battlefields.signs.SignArmor;
import me.jkow.battlefields.signs.SignClasses;
import me.jkow.battlefields.signs.SignControl;
import me.jkow.battlefields.signs.SignDefaults;
import me.jkow.battlefields.signs.SignOptions;
import me.jkow.battlefields.signs.SignGametypes;
import me.jkow.battlefields.signs.SignWeapons;
import me.jkow.battlefields.util.BFBlock;
import me.jkow.battlefields.util.Format;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class ToolSign extends Tool 
{
	private enum SignType
	{
		GAMETYPE("Gametype"), DEFAULTS("Defaults"), CUSTOM("Options"), CLASS("Classes"), WEAPONS("Weapons"), ARMOR("Armor"), CONTROL("Controls");
		
		private String name;
		
		private SignType(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
	
	private SignType type = SignType.values()[0];
	
	private BattlefieldSign getSignByType(SignType t, Sign sign)
	{
		switch(t)
		{
		case GAMETYPE:
			return new SignGametypes(sign, field);
		case DEFAULTS:
			return new SignDefaults(sign, field);
		case CUSTOM:
			return new SignOptions(sign, field);
		case CLASS:
			return new SignClasses(sign, field);
		case WEAPONS:
			return new SignWeapons(sign, field);
		case ARMOR:
			return new SignArmor(sign, field);
		case CONTROL:
			return new SignControl(sign, field);
		default:
			return null;
		}
	}
	
	@Override
	public void rightClick(Block b) 
	{
		if(!(b.getState() instanceof Sign))
		{
			Format.sendMessage(fb.getPlayer(), "Target block must be a sign.");
			return;
		}
		Sign sign = (Sign)b.getState();
		BattlefieldSign bs = getSignByType(type, sign);
		if(bs == null)
			return;
		field.addSign(bs, new BFBlock(b));
		Format.sendMessage(fb.getPlayer(), "Added sign of type '" + type.toString() + "' to field.");
		sign.setLine(0, "[Options]");
		sign.setLine(1, "[" + bs.getDisplayName() + "]");
		sign.update(true);
		field.save();
		return;
	}

	@Override
	public void leftClick(Block b) 
	{
		if(field.removeSign(b))
			Format.sendMessage(fb.getPlayer(), "Removed sign from field.");
		else
			Format.sendMessage(fb.getPlayer(), "Target block is not a registered sign.");
		return;
	}

	@Override
	public void cycleParameters() 
	{
		int i = type.ordinal()+1;
		if(i >= SignType.values().length)
			i = 0;
		this.type = SignType.values()[i];
		fb.getPlayer().sendMessage(Format.buildTag + "Sign tool type changed to '" + ChatColor.WHITE + type.toString() + ChatColor.GRAY +"'.");
		return;
	}
}
