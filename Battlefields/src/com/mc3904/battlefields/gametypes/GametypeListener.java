package com.mc3904.battlefields.gametypes;


import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.mc3904.battlefields.players.TeamMember;

public class GametypeListener 
{
	public GametypeListener() {}

	// Game has started
	public void onGameStart() {}
	
	// Game has ended, final cleanup
	public void onGameEnd() {}
	
	// Game was ended prematurely
	public void onGameCancel() {}
	
	// Player respawned on battlefield
	public Location onPlayerRespawn(TeamMember m) {return null;}
	
	// Player quit the battlefield
	public void onPlayerQuit(TeamMember m) {}

	// Player joined the battlefield
	public void onPlayerJoin(TeamMember m) {}
	
	// Player moved within the battlefield
	public boolean onPlayerMove(TeamMember m, Block from, Block to) {return false;}

	// Player was killed by another player
	public boolean onPlayerDeathByPlayer(TeamMember m, TeamMember attacker) {return false;}

	// Player was damaged by another player
	public boolean onPlayerDamageByPlayer(TeamMember m, TeamMember attacker) {return false;}

	// Player was damaged by a mob
	public boolean onPlayerDamageByMob(TeamMember m, Entity entity) {return false;}

	// Player was killed by a mob
	public boolean onPlayerDeathByMob(TeamMember m, Entity entity) {return false;}
	
	// Mob was damaged by a player
	public boolean onMobDamageByPlayer(Entity entity, TeamMember m) {return false;}

	// Mob was killed by a player
	public boolean onMobDeathByPlayer(Entity entity, TeamMember m) {return false;}

	// Player was damaged from natural causes
	public boolean onPlayerDamageByEvent(TeamMember m, DamageCause cause) {return false;}
	
	// Player died from natural causes
	public boolean onPlayerDeathByEvent(TeamMember m, DamageCause cause) {return false;}

	// A click was registered on the battlefield
	public void onClick(TeamMember m, Block clicked, Action action) {}

	// Block was place on battlefield
	public boolean onBlockPlace(TeamMember m, Block placed, Block against) {return false;}

	// Block was destroyed on battlefield
	public boolean onBlockBreak(TeamMember m, Block b) {return false;}

	// Sign was clicked on battlefield
	public void onOptionChange(TeamMember m, String s) {}
}
