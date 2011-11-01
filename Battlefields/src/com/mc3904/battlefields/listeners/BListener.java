package com.mc3904.battlefields.listeners;



import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mc3904.battlefields.Battlefields;
import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.gametypes.Gametype;
import com.mc3904.battlefields.players.TeamMember;

public class BListener extends BlockListener {
	
	// LINK PLUGIN
	private final Battlefields plugin;
    public BListener(Battlefields instance) 
    { 
    	this.plugin = instance; 
    }
    
    // PROTECT BATTLEFIELD FROM BLOCK PLACING
    public void onBlockPlace(BlockPlaceEvent event)
    {
    	Block b = event.getBlockPlaced();
    	Battlefield field = plugin.getBattlefieldManager().getField(b);
    	if(field == null)
    		return;
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(event.getPlayer());
		Gametype g = field.getGametype();
		if(!field.isActive() || m == null)
		{
			event.setCancelled(true);
			return;
		}
		else if(g.getListener().onBlockPlace(m, b, event.getBlockAgainst()))
			event.setCancelled(true);
    }
    
    // PROTECT BATTLEFIELD FROM BLOCK BREAKING
    public void onBlockBreak(BlockBreakEvent event)
    {
    	Block b = event.getBlock();
    	Battlefield field = plugin.getBattlefieldManager().getField(b);
    	if(field == null)
    		return;
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(event.getPlayer());
		Gametype g = field.getGametype();
		if(!field.isActive() || m == null)
		{
			event.setCancelled(true);
			return;
		}
		else if(g.getListener().onBlockBreak(m, b))
			event.setCancelled(true);
    }
    
    /* TODO Do we need this??
    // RESTRICT SIGN CREATION FOR NON-ADMINS
    public void onSignChange(SignChangeEvent event)
    {
    	Player p = event.getPlayer();
    	Block sign = event.getBlock();
    	if(event.getLine(0).contains("[ENTER]"))
    	{
    		if(!p.hasPermission("battlefields.builder") && !p.isOp())
    		{
    			p.sendMessage(Format.errorTag + " The content of the first line is restricted to Battlefield administrators.");
    			((Sign)sign).setLine(0, "");
    		}
    	}
    }
    */
}