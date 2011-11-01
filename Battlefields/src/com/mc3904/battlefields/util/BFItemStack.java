package com.mc3904.battlefields.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class BFItemStack extends ItemStack implements Stringable
{
	private static final long serialVersionUID = -5536701022006021395L;

	public BFItemStack(int type, int amount) 
	{
		super(type, amount);
	}
	
	public BFItemStack()
	{
		super(0);
	}

	@Override
	public String toObjString() 
	{
    	return  Integer.toString(getTypeId()) + "," + 
    			Integer.toString(getAmount());
	}

	@Override
	public boolean parseObjString(String s) 
	{
		String[] split = s.split(",");
	  	try {
	  			setType(Material.getMaterial(Integer.parseInt(split[0])));
	  			setAmount(Integer.parseInt(split[1]));
	  	}
	  	catch(Exception e) {
	  		return false;
	  	}
	  	return true;
	}
}
