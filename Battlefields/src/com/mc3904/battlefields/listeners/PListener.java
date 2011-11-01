package com.mc3904.battlefields.listeners;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.mc3904.battlefields.Battlefields;
import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.fieldbuilder.Builder;
import com.mc3904.battlefields.fieldbuilder.Tool;
import com.mc3904.battlefields.players.Team;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.signs.BattlefieldSign;
import com.mc3904.battlefields.util.BFRegion;
import com.mc3904.battlefields.util.Format;

//-------------------------------------------------------------------------------------------------------------------//

public class PListener extends PlayerListener 
{
	private final Battlefields plugin;
	
	//-------------------------------------------------------------------------------------------------------------------//
	
    public PListener(Battlefields instance) 
    { 
    	this.plugin = instance; 
    }
    
    //-------------------------------------------------------------------------------------------------------------------//
    
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
    	Player p = event.getPlayer();
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
    	if(m == null)
    		return;
    	Battlefield field = m.getTeam().getField();
    	if(!field.isActive())
    		return;
    	Location respawn = field.getGametype().getListener().onPlayerRespawn(m);
    	if(respawn != null)
    		event.setRespawnLocation(respawn);
    }
    
    //-------------------------------------------------------------------------------------------------------------------//

    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	
    	// TODO Load player stats
    	
    }
    
    //-------------------------------------------------------------------------------------------------------------------//

    public void onPlayerQuit(PlayerQuitEvent event)
    {
    	Player p = event.getPlayer();
    	
    	// TODO Write stats
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
    	if(m != null)
    	{
    		Team team = m.getTeam();
    		Battlefield field = team.getField();
			field.sendMessageToAll(Format.pluginTag + p.getName() + " of " + team.getColor().getTeamTag() + " has quit the game!");
			field.removePlayer(m);
		}
    }
    
    //-------------------------------------------------------------------------------------------------------------------//

    public void onPlayerMove(PlayerMoveEvent event)
    {
    	Block to = event.getTo().getBlock();
    	Block from = event.getFrom().getBlock();
    	if(from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ())
    		return;
    	Player p = event.getPlayer();
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
    	for(Battlefield field : plugin.getBattlefieldManager().getFields())
    	{
    		BFRegion r = field.getRegion();
    		int check = r.checkMovement(p, to, from);
    		if(check < 0)
    		{
    			if(m == null)
    				return;
    			if(field.isActive())
        		{
                    event.setTo(plugin.getLastLocation(p));
    				Format.sendMessage(p, "Cannot leave battlefield during a match!");
        		}
    			else 
    			{
    				Format.sendMessage(p, "Leaving the battlefield '" + ChatColor.WHITE + field.getName() + ChatColor.GRAY + "'.");
    				field.removePlayer(m);
    				r.setPlayerState(p, false);
    			}
    			return;
    		}
    		else if (check > 0)
    		{
    			if(field.isActive())
        		{
                    event.setTo(plugin.getLastLocation(p));
    				Format.sendMessage(p, "Cannot enter battlefield during a match!");
        		}
    			else 
    			{
        			Format.sendMessage(p, "Entering the battlefield '" + ChatColor.WHITE + field.getName() + ChatColor.GRAY + "'.");
    				field.addPlayer(p);
    				r.setPlayerState(p, true);
    			}
    			return;
    		}
    		else
    		{
    			if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() != 0)
    				plugin.setLastLocation(p);
    			if(field.isActive())
        			field.getGametype().getListener().onPlayerMove(m, from, to);
    		}
    	}
    }
    
    //-------------------------------------------------------------------------------------------------------------------//


    public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
    {
    	if(!event.isSneaking())
    		return;
    	Player p = event.getPlayer();
    	Builder fb = plugin.getBattlefieldManager().getBuilder(p);
    	if(fb != null)
    		fb.cycleTool();
    }
    
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
    	Player p = event.getPlayer();
    	// Handle field builder condition
    	Builder fb = plugin.getBattlefieldManager().getBuilder(p);
    	if(fb != null)
    	{
    		if(fb.getTool() != null)
    		{
    			fb.getTool().cycleParameters();
    			event.setCancelled(true);
    		}
    	}
    	// Handle item drops on battlefield
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
    	if(m != null)
    	{
    		if(m.getTeam().getField().isActive())
    		{
    			p.sendMessage(Format.errorTag + "Dropping items is disallowed on the battlefield.");
        		event.setCancelled(true);
    		}
    	}
    }
    
    //-------------------------------------------------------------------------------------------------------------------//

    public void onPlayerInteract(PlayerInteractEvent event)
    {
    	Block b = event.getClickedBlock();
    	Player p = event.getPlayer();
    	Action a = event.getAction();
    	// Handle field building tool operations
    	if(p.getItemInHand().getTypeId() == 276)
   		{
    		Builder fb =  plugin.getBattlefieldManager().getBuilder(p);
   	    	if(fb != null)
   	    	{
   	    		if(fb.getTool() != null)
   	    		{
   	    			Tool tool = fb.getTool();
   	    			switch(a)
   	    			{
   	    			case RIGHT_CLICK_BLOCK: tool.rightClick(b);
   	    				break;
   	    			case LEFT_CLICK_BLOCK: tool.leftClick(b);
	    				break;
   	    			case RIGHT_CLICK_AIR: tool.rightClick();
	    				break;
   	    			case LEFT_CLICK_AIR: tool.leftClick();
	    				break;
	    			default:
	    				break;
   	    			}
   	    		}
   	    	}
   		}
		TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
    	if(m != null)
    	{
    		Battlefield field = m.getTeam().getField();
        	// Handle sign clicks
    		if(b != null)
    		{
    			if(b.getState() instanceof Sign)
        		{
            		Sign sign = (Sign)b.getState();
            		String line = sign.getLine(0);
            		if(line.equals("[Options]"))
            		{
                		BattlefieldSign bs = field.getSign(b);
                		if(bs != null)
                		{
                			if(field.isActive())
            	    			Format.sendMessage(m, "You cannot change settings during a game.");
                			else if(a == Action.LEFT_CLICK_BLOCK)
                				bs.executeOption(m);
                			else if(a == Action.RIGHT_CLICK_BLOCK)
                				bs.cycleOption(m);
                		}
            		}
            	}
    		}
    		if(field.isActive())
        		field.getGametype().getListener().onClick(m, event.getClickedBlock(), event.getAction());
    	}
    }
	
    //-------------------------------------------------------------------------------------------------------------------//
    
	public void onPlayerChat(PlayerChatEvent event)
	{
		Player p = event.getPlayer();
		Builder fb =  plugin.getBattlefieldManager().getBuilder(p);
		if(fb == null)
			return;
		if(fb.getTool() != null)
		{
			Tool tool = fb.getTool();
			if(tool == null)
				return;
			if(tool.isAwaitingText())
			{
				tool.retrieveText(event.getMessage());
				event.setCancelled(true);
			}
		}
	}
	
    //-------------------------------------------------------------------------------------------------------------------//
}