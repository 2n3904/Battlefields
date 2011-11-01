package com.mc3904.battlefields.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

//-------------------------------------------------------------------------------------------------------------------//

public class BFRegion implements Stringable
{	
	private Block a;
	private Block b;
	
	private List<Player> players = new ArrayList<Player>();
	
	private World world;
	
	private int x_min, x_max, y_min, y_max, z_min, z_max;
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Constructor
	public BFRegion(Block a, Block b)
	{
		this.a = a;
		this.b = b;
		this.x_min = Math.min(a.getX(), b.getX());
		this.x_max = Math.max(a.getX(), b.getX());
		this.y_min = Math.min(a.getY(), b.getY());
		this.y_max = Math.max(a.getY(), b.getY());
		this.z_min = Math.min(a.getZ(), b.getZ());
		this.z_max = Math.max(a.getZ(), b.getZ());
		this.world = a.getWorld();
		for(Player p : world.getPlayers())
		{
			if(this.contains(p))
				players.add(p);
		}
	}
	
	public BFRegion() {}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Check to see if player entered or exited region
	public int checkMovement(Player p, Block to, Block from)
	{
		boolean isinside = contains(p);
		boolean isregistered = players.contains(p);
		if(isinside && !isregistered)
			return 1;
		else if(!isinside && isregistered)
			return -1;
		return 0;
	}
	
	public void setPlayerState(Player p, boolean bool)
	{
		if(!bool)
			players.remove(p);
		else if(!players.contains(p))
			players.add(p);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Return list of players in region
	public List<Player> getPlayers()
	{
		return players;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Check to see if region contains given entity
	public boolean contains(Player p)
	{
		return contains(p.getLocation().getBlock());
	}
	
	public boolean contains(Location loc)
	{
		return contains(loc.getBlock());
	}
	
	public boolean contains(Block block)
	{
	    int x_check = block.getX();
	    int y_check = block.getY();
	    int z_check = block.getZ();
	    return (x_check >= x_min && x_check <= x_max && y_check >= y_min && y_check <= y_max && z_check >= z_min && z_check <= z_max && world == block.getWorld());
	}

	public boolean contains(BFRegion r)
	{
		return (r.x_max <= x_max && r.x_min >= x_min && r.y_max <= y_max && r.y_min >= y_min && r.z_max <= z_max && r.z_min >= z_min && r.getWorld() == world);
	}
	
	public boolean intersects(BFRegion r)
	{
		return (r.x_max >= x_min && r.x_min <= x_max && r.y_max >= y_min && r.y_min <= y_max && r.z_max >= z_min && r.z_min <= z_max && r.getWorld() == world);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Get volume of the region
	public int getVolume()
	{
		return (x_max-x_min)*(y_max-y_min)*(z_max-z_min);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Get the area of the region from bird's eye view
	public int getArea()
	{
		return (x_max-x_min)*(z_max-z_min);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Return two defining corners of the region
	public Location[] getCornerLocations()
	{
		Location[] corners = new Location[2];
		corners[0] = a.getLocation();
		corners[1] = b.getLocation();
		return corners;
	}
	
	public Block[] getCornerBlocks()
	{
		Block[] corners = new Block[2];
		corners[0] = a;
		corners[1] = b;
		return corners;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//

	public World getWorld() 
	{
		return world;
	}
	
	public Set<Block> getBlocks()
	{
		Set<Block> blocks = new HashSet<Block>();
		for(int x = x_min; x <= x_max; x++)
		{
			for(int y = y_min; y <= y_max; y++)
			{
				for(int z = z_min; z <= z_max; z++)
					blocks.add(world.getBlockAt(x, y, z));
			}
		}
		return blocks;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	@Override
	public String toObjString() 
	{
    	return  world.getName() + "," + 
    			Integer.toString(x_min) + "," + 
    			Integer.toString(x_max) + "," + 
    			Integer.toString(y_min) + "," +
    			Integer.toString(y_max) + "," + 
    			Integer.toString(z_min) + "," + 
    			Integer.toString(z_max);
	}

	@Override
	public boolean parseObjString(String s) 
	{
		String[] split = s.split(",");
	    if((world = Bukkit.getServer().getWorld(split[0])) == null || split.length != 7)
	    {
	    	return false;
	    }
	  	try {
	  			x_min = Integer.parseInt(split[1]);
	  			x_max = Integer.parseInt(split[2]);
	  			y_min = Integer.parseInt(split[3]);
	  			y_max = Integer.parseInt(split[4]);
	  			z_min = Integer.parseInt(split[5]);
	  			z_max = Integer.parseInt(split[6]);
	  	}
	  	catch(NumberFormatException e) {
	  		return false;
	  	}
	  	return true;
	}
}
