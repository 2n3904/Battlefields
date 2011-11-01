package com.mc3904.battlefields.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BFBlock implements Stringable
{
    public int x;
    public int y;
    public int z;
    public World w;

    public BFBlock(Block b)
    {
        x = b.getX();
        y = b.getY();
        z = b.getZ();
        w = b.getWorld();
    }
    
    public BFBlock() {}
    
    public Block getBlock()
    {
    	return w.getBlockAt(new Location(w,x,y,z));
    }
    
    public BFLocation getLocation()
    {
    	return new BFLocation(w, x, y, z);
    }
    
    public boolean equals(Block b)
    {
    	if(w != b.getWorld())
        	return false;
        if  ( b.getX() != this.x )
            return false;
        if  ( b.getY() != this.y )
            return false;
        if  ( b.getZ() != this.z )
            return false;
    	return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( o == null )
            return false;
        if ( this.getClass() != o.getClass() )
            return false;
        BFBlock bc = (BFBlock) o;
        if(w != bc.w)
        	return false;
        if  ( bc.x != this.x )
            return false;
        if  ( bc.y != this.y )
            return false;
        if  ( bc.z != this.z )
            return false;
        return true;
    }

	@Override
	public String toObjString() 
	{
    	return  w.getName() + "," + 
    			Integer.toString(x) + "," + 
    			Integer.toString(y) + "," + 
    			Integer.toString(z);
	}

	@Override
	public boolean parseObjString(String s) 
	{
		String[] split = s.split(",");
	    if((w = Bukkit.getServer().getWorld(split[0])) == null || split.length != 4)
	    {
	    	return false;
	    }
	  	try {
	  			x = Integer.parseInt(split[1]);
	  			y = Integer.parseInt(split[2]);
	  			z = Integer.parseInt(split[3]);
	  	}
	  	catch(NumberFormatException e) {
	  		return false;
	  	}
	  	return true;
	}
}
