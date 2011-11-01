package com.mc3904.battlefields.gametypes;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.bukkit.plugin.InvalidDescriptionException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import com.mc3904.battlefields.players.TeamColor;

public final class GametypeDescriptionFile 
{
    private static final Yaml yaml = new Yaml(new SafeConstructor());
    private String name = null;
    private String version = null;
    private String main = null;
    private String author = null;
    private String id = null;
    private String[] options = null;
    private List<String> scoretypes = new ArrayList<String>();
    private List<TeamColor> allowedteams = new ArrayList<TeamColor>();

    @SuppressWarnings("unchecked")
    public GametypeDescriptionFile(final InputStream stream) throws InvalidDescriptionException {
        loadMap((Map<String, Object>) yaml.load(stream));
    }

    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getFullName() {
        return name + " v" + version;
    }

    public String getMain() {
        return main;
    }
    
    public String[] getOptions() {
        return options;
    }
    
    public List<String> getScoreTypes() {
    	return scoretypes;
    }
    
    public List<TeamColor> getAllowedTeams() {
    	return allowedteams;
    }

    private void loadMap(Map<String, Object> map) throws InvalidDescriptionException {
        try {
            name = map.get("name").toString();
            if (!name.matches("^[A-Za-z0-9 _.-]+$")) {
                throw new InvalidDescriptionException("name '" + name + "' contains invalid characters.");
            }
        } catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "name is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidDescriptionException(ex, "name is of wrong type");
        }
        
        try {
            id = map.get("id").toString();
        } catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "id is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidDescriptionException(ex, "id is of wrong type");
        }

        try {
            version = map.get("version").toString();
        } catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "version is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidDescriptionException(ex, "version is of wrong type");
        }

        try {
            main = map.get("main").toString();
        } catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "main is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidDescriptionException(ex, "main is of wrong type");
        }

        if (map.containsKey("author")) {
            try {
                author = map.get("author").toString();
            } catch (ClassCastException ex) {
                throw new InvalidDescriptionException(ex, "author is of wrong type");
            }
        }
        
        if (map.containsKey("author")) {
            try {
            	ArrayList<String> extra = (ArrayList<String>) map.get("options");
            	if(!extra.isEmpty())
            		options = extra.toArray(new String[0]);
            } catch (ClassCastException ex) {
                throw new InvalidDescriptionException(ex, "options is of wrong type");
            }
        }
        
        if (map.containsKey("scores")) {
            try {
            	ArrayList<String> extra = (ArrayList<String>) map.get("stats");
            	scoretypes.addAll(extra);
            } catch (ClassCastException ex) {
                throw new InvalidDescriptionException(ex, "scoretype is of wrong type");
            }
        }
        
        if (map.containsKey("teams")) {
            try {
            	ArrayList<String> extra = (ArrayList<String>) map.get("teams");
            	for(String s : extra)
            	{
            		for(TeamColor color : TeamColor.values())
            		{
            			if(color.toString().equalsIgnoreCase(s) && !allowedteams.contains(color))
            				allowedteams.add(color);
            		}
            	}
            	if(allowedteams.isEmpty())
            		throw new InvalidDescriptionException("must have at least one allowed team");
            } catch (ClassCastException ex) {
                throw new InvalidDescriptionException(ex, "team color is of wrong type");
            }
        }
    }
}