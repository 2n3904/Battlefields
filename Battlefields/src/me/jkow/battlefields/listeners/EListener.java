package me.jkow.battlefields.listeners;


import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.field.BattlefieldManager;
import me.jkow.battlefields.gametypes.Gametype;
import me.jkow.battlefields.players.Team;
import me.jkow.battlefields.players.TeamMember;
import me.jkow.battlefields.util.Format;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

//-------------------------------------------------------------------------------------------------------------------//

// PLAYER LISTENER EXTENSION CLASS
public class EListener extends EntityListener 
{
	private final Battlefields plugin;
	
	//-------------------------------------------------------------------------------------------------------------------//
	
    public EListener(Battlefields instance) 
    { 
    	this.plugin = instance; 
    }
	
	//-------------------------------------------------------------------------------------------------------------------//
        
    // ON PLAYER DEATH
    public void onEntityDeath(EntityDeathEvent event)
    {
    	if(!(event.getEntity() instanceof HumanEntity))
    		return;
    	HumanEntity dead = (HumanEntity) event.getEntity();
    	if(!(dead instanceof Player))
    		return;
		Player p = (Player) dead;
		TeamMember m = plugin.getBattlefieldManager().getPlayer(p);
		if(m == null)
			return;
		Battlefield field = m.getTeam().getField();
		event.getDrops().clear();
		Object lastdamage = m.getLastDamageCause();
		if(lastdamage == null) 
			return;
		if(!field.isActive())
			return;
		if(lastdamage instanceof DamageCause)
		{
			DamageCause cause = (DamageCause)lastdamage;
			field.getGametype().getListener().onPlayerDeathByEvent(m, cause);
		}
		else if(lastdamage instanceof TeamMember)
		{
			TeamMember killer = (TeamMember)lastdamage;
			field.getGametype().getListener().onPlayerDeathByPlayer(m, killer);
		}
		else if(lastdamage instanceof Entity)
		{
			Entity killer = (Entity)lastdamage;
			field.getGametype().getListener().onPlayerDeathByMob(m, killer);
		}
		if(field.getSettings().instant_spawn)
		{
			Location respawn = field.getGametype().getListener().onPlayerRespawn(m);
			if(respawn == null)
				respawn = field.getRandomSpawn();
			p.teleport(respawn);
			p.setHealth(20);
		}
    }
	
	//-------------------------------------------------------------------------------------------------------------------//
    
    // ON PLAYER DAMAGE
    public void onEntityDamage(EntityDamageEvent event)
    {
    	if(event.isCancelled())
    		return;
    	if(!(event.getEntity() instanceof HumanEntity))
    		return;
    	HumanEntity target = (HumanEntity)event.getEntity();
    	if(!(target instanceof Player))
    		return;
    	Player victim = (Player)target;
    	if(event instanceof EntityDamageByEntityEvent)
    	{
            EntityDamageByEntityEvent act = (EntityDamageByEntityEvent)event;
            if(act.getDamager() instanceof HumanEntity)
            {
            	HumanEntity source = (HumanEntity)act.getDamager();
                if(source instanceof Player)
                {
                    Player attacker = (Player) source;
                    checkDamage(attacker, victim, act);
                }
            }
            if(act.getDamager() instanceof Projectile)
            {
            	Projectile proj = (Projectile)act.getDamager();
                if(proj.getShooter() instanceof HumanEntity)
                {
                    HumanEntity source = (HumanEntity)proj.getShooter();
                	if(source instanceof Player)
                    {
                		 Player attacker = (Player) source;
                		 checkDamage(attacker, victim, act);
                			 
                    }
                }
            }
        }
    	TeamMember m = plugin.getBattlefieldManager().getPlayer(victim);
    	if(m != null)
        {
    		Gametype g = m.getTeam().getField().getGametype();
    		DamageCause c = event.getCause();
    		if(g != null)
    			g.getListener().onPlayerDamageByEvent(m, c);
			m.setLastDamageCause(c);
        }	
    } 
	
	//-------------------------------------------------------------------------------------------------------------------//
    
    public void checkDamage(Player attacker, Player victim, EntityDamageByEntityEvent act)
    {
    	BattlefieldManager fm = plugin.getBattlefieldManager();
    	TeamMember victimMember = fm.getPlayer(victim);
        TeamMember attackerMember = fm.getPlayer(attacker);
    	if(attacker != null && victim != null)
        {
        	Team victimTeam = victimMember.getTeam();
        	Team attackerTeam = attackerMember.getTeam();
        	Battlefield field = victimTeam.getField();
        	victimMember.setLastDamageCause(attackerMember);
        	Gametype gametype = field.getGametype();
        	// Was it a teamkill?
        	if(victimTeam == attackerTeam && field.getSettings().team_kills)
        	{
        		attacker.sendMessage(Format.errorTag + "Friendly fire is disabled in this battlefield!");
        		act.setCancelled(true);
        		return;
        	}
        	// Has game started?
        	if(gametype == null)
        	{
        		attacker.sendMessage(Format.errorTag + "No combat allowed during intermission.");
            	act.setCancelled(true);
            	return;
        	}
        	else 
        	{
        		act.setCancelled(gametype.getListener().onPlayerDamageByPlayer(victimMember, attackerMember));
        		return;
        	}
        	// TODO Was the weapon used allowed?
        }
        // Check for damage through battlefield boundaries
        if(attacker != null || victim != null)
        {
        	attacker.sendMessage(Format.errorTag + "No combat allowed during intermission.");
        	act.setCancelled(true);
        	return;
        }
    }
	
	//-------------------------------------------------------------------------------------------------------------------//
}