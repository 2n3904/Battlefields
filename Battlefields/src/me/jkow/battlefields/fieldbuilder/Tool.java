package me.jkow.battlefields.fieldbuilder;

import me.jkow.battlefields.Battlefields;
import me.jkow.battlefields.field.Battlefield;
import me.jkow.battlefields.field.BattlefieldManager;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class Tool 
{
	protected Builder fb;
	protected Player p;
	protected Battlefield field;
	protected BattlefieldManager bm;
	protected Battlefields plugin;
	private boolean awaitingtext = false;
	private int textTaskId = -1;
	
	public void initialize(Builder fb)
	{
		this.fb = fb;
		this.p = fb.getPlayer();
		this.field = fb.getField();
		this.bm = fb.getBattlefieldManager();
		this.plugin = bm.getPlugin();
	}
	
	public void rightClick() {}
	
	public void rightClick(Block b) {}
	
	public void leftClick() {}
	
	public void leftClick(Block b) {}
	
	public void cycleParameters() {}
	
	protected void registerText(String s) {}
	
	public void retrieveText(String s) 
	{
		registerText(s);
		cancelQueueText();
	}
	
	protected void cancelQueueText()
	{
		Bukkit.getScheduler().cancelTask(textTaskId);
		this.awaitingtext = false;
	}
	
	protected void queueText()
	{
		this.awaitingtext = true;
		textTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				cancelQueueText();
			}
		}, 300);
	}
	
	public boolean isAwaitingText()
	{
		return awaitingtext;
	}
}
