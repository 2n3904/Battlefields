package com.mc3904.battlefields.voting;

import java.util.ArrayList;
import java.util.List;


import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;

import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.util.Format;

public abstract class Vote 
{
	protected Battlefield field;
	protected BukkitScheduler sch;
	protected TeamMember m;
	protected List<TeamMember> yes = new ArrayList<TeamMember>();
	protected List<TeamMember> no = new ArrayList<TeamMember>();
	protected int taskId;
	private boolean isactive = false;
	
	public Vote(Battlefield field, TeamMember m)
	{
		this.field = field;
		this.m = m;
		this.sch = field.plugin.getServer().getScheduler();
		this.no.addAll(field.getPlayers());
	}
	
	public void start()
	{
		field.sendMessageToAll(Format.pluginTag + m.getColoredName() + " has begun a vote.");
		onEnable();
		taskId = sch.scheduleSyncDelayedTask(field.plugin, new Runnable() {
		    public void run() {
		        end();
		    }
		}, 300);
		isactive = true;
	}
	
	public String getResults()
	{
		return "[" + ChatColor.GREEN + Integer.toString(yes.size()) + ChatColor.GRAY +  "/"  + ChatColor.DARK_RED + Integer.toString(no.size()) + ChatColor.GRAY + "]";
	}
	
	public void vote(TeamMember m)
	{
		if(no.remove(m))
		{
			yes.add(m);
			Format.sendMessage(m, "Your vote has been cast. " + getResults());
		}
		else
			Format.sendMessage(m, "You have already cast your vote.");
	}
	
	public void end()
	{
		int y = yes.size();
		int n = no.size();
		field.sendMessageToAll(Format.pluginTag + "Voting has ended. " + getResults());
		if(sch.isQueued(taskId))
			sch.cancelTask(taskId);
		if(y > n)
			onSuccess();
        isactive = false;
	}
	
	public boolean isActive()
	{
		return isactive;
	}
	
	public abstract void onEnable();
	
	public abstract void onSuccess();
	
	public boolean isValid()
	{
		return true;
	}
}
