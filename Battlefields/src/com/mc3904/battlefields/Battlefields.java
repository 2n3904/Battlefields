package com.mc3904.battlefields;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mc3904.battlefields.commands.BattlefieldCommandMap;
import com.mc3904.battlefields.field.BattlefieldManager;
import com.mc3904.battlefields.gametypes.GametypeManager;
import com.mc3904.battlefields.listeners.BListener;
import com.mc3904.battlefields.listeners.EListener;
import com.mc3904.battlefields.listeners.PListener;
import com.mc3904.battlefields.players.TeamMember;
import com.mc3904.battlefields.scores.ScoreManager;
import com.mc3904.battlefields.util.BYamlConfiguration;

//-------------------------------------------------------------------------------------------------------------------//

public class Battlefields extends JavaPlugin
{
	// Plugin information
	public static final String version = "0.1";
	
	// Listeners
	private final PListener playerListener = new PListener(this);
	private final EListener entityListener = new EListener(this);
	private final BListener blockListener = new BListener(this);
	 
    // Set up logger
	public final Logger log = Logger.getLogger("Minecraft");
	
	// Setup yaml communication
    public final String directory = "plugins" + File.separator + "Battlefields";
    
    private final File fconfig = new File(directory + File.separator + "config.yml");
    private final File fplayers = new File(directory + File.separator + "players.yml");
    private final File ffields = new File(directory + File.separator + "fields.yml");
    
    private final BYamlConfiguration yconfig = new BYamlConfiguration(fconfig);
    private final BYamlConfiguration yplayers = new BYamlConfiguration(fplayers);
    private final BYamlConfiguration yfields = new BYamlConfiguration(ffields);

	private final ConfigManager cm = new ConfigManager(this, yconfig);
	private final GametypeManager gm = new GametypeManager(this);
	private final ScoreManager sm = new ScoreManager(this, yplayers);
	private final BattlefieldManager fm = new BattlefieldManager(this, yfields);
	
	private Map<Player, Location> lastLocations = new HashMap<Player, Location>();
	
	// PLUGIN ENABLE METHOD
	@Override
	public void onEnable()
	{		
		// Load command map
	    this.getCommand("bf").setExecutor(new BattlefieldCommandMap(this));
		
		// Register listener events
		PluginManager pm = this.getServer().getPluginManager();
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_INTERACT, playerListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_MOVE, playerListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_QUIT, playerListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_RESPAWN, playerListener, org.bukkit.event.Event.Priority.Highest, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_DROP_ITEM, playerListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_TOGGLE_SNEAK, playerListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.PLAYER_CHAT, playerListener, org.bukkit.event.Event.Priority.Lowest, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.SIGN_CHANGE, blockListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.ENTITY_DAMAGE, entityListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.ENTITY_DEATH, entityListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.BLOCK_BREAK, blockListener, org.bukkit.event.Event.Priority.Normal, this);
	    pm.registerEvent(org.bukkit.event.Event.Type.BLOCK_PLACE, blockListener, org.bukkit.event.Event.Priority.Normal, this);

	    // Load items from files
	    cm.loadConfig();
		gm.loadGametypes(new File(directory + File.separator + "gametypes"));
		fm.loadFields();
		// TODO sm.loadStats();
	    
	    log.info("[Battlefields] Version " + version + " was successfully enabled!");
	}

	// Clean up plugin on disable
	@Override
	public void onDisable() {
		log.info("[Battlefields] Version " + version + " disabled.");
		for(TeamMember m : fm.getTeamMemberList())
		{
			//m.restoreInventory();
			m.heal();
			// TODO save scores
		}
	}
    
	public GametypeManager getGametypeManager()
	{
		return gm;
	}
	
	public ScoreManager getScoreManager()
	{
		return sm;
	}
	
	public ConfigManager getConfigManager()
	{
		return cm;
	}
	
	public BattlefieldManager getBattlefieldManager()
	{
		return fm;
	}

	public BYamlConfiguration getConfigFile() 
	{
		return yconfig;
	}
	
	public Location getLastLocation(Player p)
	{
		return this.lastLocations.get(p);
	}

	public Location setLastLocation(Player p)
	{
		return this.lastLocations.put(p, p.getLocation());
	}
	
    //-------------------------------------------------------------------------------------------------------------------//
}