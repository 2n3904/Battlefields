package com.mc3904.battlefields.gametypes;
 
 import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;

import com.mc3904.battlefields.Battlefields;
import com.mc3904.battlefields.field.Battlefield;
import com.mc3904.battlefields.scores.Score;
import com.mc3904.battlefields.scores.ScoreType;
import com.mc3904.battlefields.util.BFLocation;
import com.mc3904.battlefields.util.BYamlConfiguration;

public abstract class GametypePlugin
{
	 private String directory = null;
     private boolean isEnabled = false;
     private boolean initialized = false;
     private GametypeLoader loader = null;
     private Server server = null;
     private Battlefields plugin = null;
     private File file = null;
     private GametypeDescriptionFile description = null;
     private GametypeClassLoader classLoader = null;
     private BYamlConfiguration config = null;
     
     private Map<String, ScoreType> scores = new HashMap<String, ScoreType>();
     
     private String configFile = null;
 
     public GametypePlugin() {}
 
     public String getDirectory() 
     {
         return directory;
     }
 
     public final GametypeLoader getPluginLoader() 
     {
         return loader;
     }
 
     public final Server getServer() 
     {
         return server;
     }
     
     public ScoreType getScoreType(String type)
     {
    	 return scores.get(type);
     }
     
     public List<String> getScoreTypes()
     {
    	 return new ArrayList<String>(scores.keySet());
     }
 
     public final boolean isEnabled() 
     {
         return isEnabled;
     }
 
     protected File getFile() 
     {
         return file;
     }
 
     public GametypeDescriptionFile getDescription() 
     {
         return description;
     }
     
     public BYamlConfiguration getConfig() 
     {
         return config;
     }
     
     public boolean debugField(Battlefield field)
     {
    	 return false;
     }
     
     public InputStream getResource(String filename) 
     {
         if (filename == null) {
             throw new IllegalArgumentException("Filename cannot be null");
         }
         return getClassLoader().getResourceAsStream(filename);
     }
 
     protected GametypeClassLoader getClassLoader() {
         return classLoader;
     }
 
     protected final void initialize(GametypeLoader loader, Server server,
             GametypeDescriptionFile description, File file,
             GametypeClassLoader classLoader) {
         if (!initialized) {
             this.initialized = true;
             this.loader = loader;
             this.server = server;
             this.file = file;
             this.plugin = loader.getGametypeManager().getPlugin();
             this.description = description;
             this.directory = plugin.directory + File.separator + "gametypes";
             this.classLoader = classLoader;
             this.configFile = description.getId() + ".yml";
             this.config = new BYamlConfiguration(new File(directory + File.separator + configFile));
             for(String type : description.getScoreTypes())
            	 scores.put(type, new ScoreType(plugin.getScoreManager(), type));
             if(config != null)
             {
            	ConfigurationSection pscores;
            	for(ScoreType type : scores.values())
            	{
             		type.setPoints(config.getInt("scores." + type.getName() + ".points", 1));
             		type.setReward(config.getInt("scores." + type.getName() + ".reward", 1));
            		pscores = config.getConfigurationSection("players." + type);
            		if(pscores != null)
             		{
             			for(String str : pscores.getKeys(false))
             			{
             				int score = config.getInt("players." + type + "." + str, 0);
             				scores.get(type).addPlayerScore(new Score(type, str, score));
             			}
             		}
            	}
             }
             onEnable();
         }
     }
     
     public abstract Gametype newGametype(Battlefield field);
     
     public abstract void onEnable();
 
     public boolean isInitialized() {
         return initialized;
     }
 }