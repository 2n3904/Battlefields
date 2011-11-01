package com.mc3904.battlefields.gametypes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

import com.mc3904.battlefields.Battlefields;

public class GametypeManager 
{
	private HashMap<String, GametypePlugin> gametypes = new HashMap<String, GametypePlugin>();
	private Battlefields plugin;
	
	private final Map<Pattern, GametypeLoader> fileAssociations = new HashMap<Pattern, GametypeLoader>();
	
	
	public GametypeManager(Battlefields plugin)
	{
		this.plugin = plugin;
	}
	
	public GametypePlugin getGametype(String s)
	{
		return gametypes.get(s);
	}
	
	public String[] getGametypes()
	{
		return gametypes.keySet().toArray(new String[0]);
	}
	
	public void registerInterface() throws IllegalArgumentException 
	{
        GametypeLoader instance = new GametypeLoader(this);

        Pattern[] patterns = instance.getPluginFileFilters();

        synchronized (this) {
            for (Pattern pattern : patterns) {
                fileAssociations.put(pattern, instance);
            }
        }
     }
	
	
	public GametypePlugin loadGametype(File file) throws InvalidPluginException, InvalidDescriptionException
	{
		 Pattern filter = Pattern.compile("\\.jar$");
		 String name = file.getName();
         Matcher match = filter.matcher(name);
         GametypePlugin result = null;
         if (match.find()) 
         {
             GametypeLoader loader = new GametypeLoader(this);
             result = loader.loadGametype(file);
         }
         if(result != null) 
         {
             gametypes.put(result.getDescription().getId(), result);
         }
         return result;
	}
	
	public List<GametypePlugin> loadGametypes(File directory)
	{
		plugin.log.info("[Battlefields] Loading Gametypes...");
		List<GametypePlugin> result = new ArrayList<GametypePlugin>();
        File[] files = directory.listFiles();      
        
        boolean allFailed = false;
        boolean finalPass = false;

        LinkedList<File> filesList = new LinkedList<File>(Arrays.asList(files));
        while(!allFailed || finalPass) 
        {
            allFailed = true;
            Iterator<File> itr = filesList.iterator();

            while(itr.hasNext()) 
            {
                File file = itr.next();
                GametypePlugin gametype = null;

                try {
                	gametype = loadGametype(file);
                } catch (InvalidPluginException ex) {
                    plugin.log.info("FAILURE : Could not load '" + file.getAbsolutePath() + "'. May be defunct.");
                    ex.printStackTrace();
                    itr.remove();
                } catch (InvalidDescriptionException ex) {
                	plugin.log.info("FAILURE : Could not load '" + file.getAbsolutePath() + "'. Invalid gametype.yml." + ex.getMessage());
                    itr.remove();
                }
                if(gametype != null) 
                {
                    plugin.log.info("SUCCESS : Loaded gametype '" + gametype.getDescription().getFullName() + "'.");
                    result.add(gametype);
                }
            }
        }
        return result;
	}

	public Battlefields getPlugin() 
	{
		return plugin;
	}
}
