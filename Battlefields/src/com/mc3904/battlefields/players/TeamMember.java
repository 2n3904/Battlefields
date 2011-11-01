package com.mc3904.battlefields.players;

import java.util.HashMap;


import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.mc3904.battlefields.field.Battlefield;

//-------------------------------------------------------------------------------------------------------------------//

public class TeamMember 
{
	private final Team team;
	private final Battlefield field;
	private Player p;
	
	// Inventory saves
	private ItemStack[] savedInv;
	private ItemStack[] savedArmor;
	
	// Special kill event variables
	public int killstreak;
	public int multikill;
	
	private TeamMemberClass playerclass;
	
	// Last entity/event to damage the player
	public Object lastDamageCause;

	// Scheduler Ids
	public int multiKillTaskId;
	public int giveItemsTaskId;
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public TeamMember(Player p, Team t)
	{
		this.team = t;
		this.field = t.getField();
		this.p = p;
		//this.playerclass = field.plugin.getSettings().getPlayerClass(this, null);
		reset();
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public void onGameStart()
	{
		if(playerclass == null)
			return;
		PlayerInventory pi = p.getInventory();
		playerclass.setPlayerInventory(p);
	}
	
	public Player getPlayer() 
	{
		return p;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Send message to a player
	public void sendMessage(String message)
	{
		p.sendMessage(message);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//

	// Return player's team
	public Team getTeam() 
	{
		return team;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Return team-colored name of player
	public String getColoredName()
	{
		return team.getColor().getChatColor() + p.getName() + ChatColor.GRAY;		
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Return name of player
	public String getName()
	{
		return p.getName();		
	}
	
	public void heal()
	{
		p.setHealth(20);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public void spawn()
	{
		Location spawnpoint;
		TeamColor color;
		// Reset player inv and health
		heal();
		giveItemsTaskId = team.getField().plugin.getServer().getScheduler().scheduleSyncDelayedTask(team.getField().plugin, new Runnable() {
		    public void run() {
		    	replaceInventory();
		    	getPlayer().setFireTicks(0);
		    }
		}, 1);
		// Find spawnpoint
		if((color = team.getColor()) == TeamColor.NEUTRAL)
			spawnpoint = team.getField().getRandomSpawn(color.toString());
		else
			spawnpoint = team.getField().getRandomSpawn();
		// Teleport to spawn
		p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
		p.teleport(team.getField().getRandomSpawn(team.getColor().toString()));
		p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
		p.setNoDamageTicks(60);
	}
	
	//-------------------------------------------------------------------------------------------------------------------//

	//-------------------------------------------------------------------------------------------------------------------//

	//-------------------------------------------------------------------------------------------------------------------//
	public int getDamageTaken(int damage)
	{
		if(playerclass == null)
			return damage;
		int n = damage-playerclass.getDamageReduction();
		if(n < 1)
			n = 1;
		return n;
	}
	
	public int getDamageDealt(int damage)
	{
		if(playerclass == null)
			return damage;
		return damage+playerclass.getDamageBonus();
	}
	
	public void setPlayerClass(TeamMemberClass c)
	{
		this.playerclass = c;
	}
	
	public TeamMemberClass getPlayerClass()
	{
		return playerclass;
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public void saveInventory()
	{
		PlayerInventory inv = p.getInventory();
        savedInv = inv.getContents();
        savedArmor = inv.getArmorContents();
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	@SuppressWarnings("deprecation")
	public void replaceInventory()
	{/*
		Settings settings = getTeam().getField().plugin.getSettings();
		PlayerInventory inv = p.getInventory();
		inv.clear();
		
		//TODO GET ARMOR SETTINGS LOADED
		for(int i=0; i<settings.s_weapons.size(); i++)
        {
        	int ID = settings.s_weapons.get(i);        	
        	switch(ID)
    		{
        	case 261: 
        		inv.addItem(new ItemStack(ID, 1));
        			for(int j=0; j<settings.s_ammo; j++)
        				inv.addItem(new ItemStack(262, 64));
        		break;
    		case 344: case 332: 
        			for(int j=0; j<settings.s_ammo; j++)
        				inv.addItem(new ItemStack(ID, 64));
        		break;
    		default: inv.addItem(new ItemStack(ID, 1));
    			break;
    		}
        }
		p.updateInventory();*/
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	@SuppressWarnings("deprecation")
	public void onGameEnd() 
	{
        PlayerInventory PlayerInv = p.getInventory();
        PlayerInv.setContents(savedInv);
        PlayerInv.setArmorContents(savedArmor);
        p.updateInventory();
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	public void reset()
	{
		lastDamageCause = null;
		if(team.getField().plugin.getServer().getScheduler().isQueued(multiKillTaskId))
			team.getField().plugin.getServer().getScheduler().cancelTask(multiKillTaskId);
		if(team.getField().plugin.getServer().getScheduler().isQueued(giveItemsTaskId))
			team.getField().plugin.getServer().getScheduler().cancelTask(giveItemsTaskId);
	}

	//-------------------------------------------------------------------------------------------------------------------//
	
	// Player kill handling
	public void kill()
	{
		/*
		p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
		scores.get("def_kills").add();
		killstreak++;
		if(multiKillTaskId != -1)
		{
			if(team.getField().plugin.getServer().getScheduler().isQueued(multiKillTaskId))
				team.getField().plugin.getServer().getScheduler().cancelTask(multiKillTaskId);
		}
		multikill++;
    	if(multikill > scores.get("def_multikill").value())
    		scores.get("def_multikill").set(multikill);
		multiKillTaskId = team.getField().plugin.getServer().getScheduler().scheduleSyncDelayedTask(team.getField().plugin, new Runnable() {
		    public void run() {
		        multikill = 0;
		        multiKillTaskId = -1;
		    }
		}, 60);
		switch(multikill)
		{
		case 0: case 1:
			break;
		case 2: team.getField().sendMessageToAll(Messages.pluginTag + getColoredName() + " got a " + ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + " DOUBLE KILL " + ChatColor.DARK_GRAY + "]");
			break;
		case 3: team.getField().sendMessageToAll(Messages.pluginTag + getColoredName() + " got a " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + " TRIPLE KILL " + ChatColor.DARK_GRAY + "]");
			break;
		case 4: team.getField().sendMessageToAll(Messages.pluginTag + getColoredName() + " got a " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + " QUADRUPLE KILL " + ChatColor.DARK_GRAY + "]");
			break;
		case 5: team.getField().sendMessageToAll(Messages.pluginTag + getColoredName() + " got a " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + " QUINTUPLE KILL " + ChatColor.DARK_GRAY + "]");
			break;
		default: team.getField().sendMessageToAll(Messages.pluginTag + getColoredName() + " is on a " + ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + " RAMPAGE " + ChatColor.DARK_GRAY + "]");
			break;	
		}
		if (killstreak > 2)
			team.getField().sendMessageToAll(Messages.pluginTag + getColoredName() + " is on a " + ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "KILLSTREAK : " + Integer.toString(killstreak) + ChatColor.DARK_GRAY + "]");
		killstreak = 0;*/
	}
	
	//-------------------------------------------------------------------------------------------------------------------//
	
	// Player death handling
	public void die()
	{
		/*p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
		// Check to see if they got a record killstreak
		if(killstreak > scores.get("killstreak"))
			scores.get("def_killstreak").put(killstreak);
		// Give player a death
		scores.get("def_deaths").add();
		// Reset special kill variables
		killstreak = 0;
		multikill = 0;
		spawn();*/
	}

	public void setLastDamageCause(Object o) 
	{
		this.lastDamageCause = o;
	}

	public Object getLastDamageCause() 
	{
		return lastDamageCause;
	}
	//-------------------------------------------------------------------------------------------------------------------//
}
