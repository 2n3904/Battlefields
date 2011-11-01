package me.jkow.battlefields.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BFLocation extends Location implements Stringable
{
	public BFLocation(Location l)
	{
		super(l.getWorld(), l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
	}
	
	public BFLocation(World world, double x, double y, double z) 
	{
		super(world, x, y, z);
	}
	
	public BFLocation() 
	{
		super(null, 0, 0, 0);
	}
	
	public boolean equals(BFLocation comp)
	{
		return comp.getBlock() == getBlock();
	}

	@Override
	public String toObjString() 
	{
    	return  getWorld().getName() + "," + 
    			Double.toString(getX()) + "," + 
    			Double.toString(getY()) + "," + 
    			Double.toString(getZ()) + "," + 
    			Float.toString(getPitch()) + "," + 
    			Float.toString(getYaw());
	}

	@Override
	public boolean parseObjString(String s) 
	{
		String[] split = s.split(",");
		World w = Bukkit.getServer().getWorld(split[0]);
	    if(w == null || split.length != 6)
	    	return false;
	    setWorld(w);
	  	try {
	  			setX(Double.parseDouble(split[1]));
	  			setY(Double.parseDouble(split[2]));
	  			setZ(Double.parseDouble(split[3]));
	  			setPitch(Float.parseFloat(split[4]));
	  			setYaw(Float.parseFloat(split[5]));
	  	}
	  	catch(NumberFormatException e) {
	  		return false;
	  	}
	  	return true;
	}
}