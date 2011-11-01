
package me.jkow.battlefields.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.jkow.battlefields.Battlefields;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

//-------------------------------------------------------------------------------------------------------------------//

public class BYamlConfiguration extends YamlConfiguration
{
	private File file;
	
	public BYamlConfiguration(File f)
	{
		this.file = f;
		try {
			this.load(f);
		} catch (Throwable e) {
			Bukkit.getLogger().warning("[Battlefields] ERROR : Could not load '" + f.getName() + "'.");
		}
	}
	
	public void save()
	{
		try {
			this.save(file);
		} catch (Exception e) 
		{
			Bukkit.getLogger().warning("[Battlefields] ERROR : Could not save config to file '" + file.getName() + "'.");
		}
	}
	
	public boolean setObj(String root, Object o)
	{
		if(!(o instanceof Stringable))
			return false;
		Stringable obj = (Stringable)o;
		set(root, obj.toObjString());
		return true;
	}
	
	public boolean setObjList(String root, List<?> olist)
	{
		List<String> slist = new ArrayList<String>();
		for(Object o : olist)
		{
			if(o instanceof Stringable)
			{
				Stringable obj = (Stringable)o;
				slist.add(obj.toObjString());
			}
		}
		set(root, slist);
		return true;
	}
	
	public Object getObj(String root, Class<?> c)
	{
		String s = getString(root);
		if(s == null)
			return null;
		if(!Stringable.class.isAssignableFrom(c))
			return null;
		Stringable obj;
			try {
				obj = (Stringable)c.newInstance();
			} catch (Exception e) {
				Bukkit.getServer().getLogger().info("[Battlefields] ERROR : Invalid object in '" + file.getName() + "' at '" + root + "'.");
				return null;
			}
		if(obj.parseObjString(s))
			return obj;
		return null;
	}
	
	public List<?> getObjList(String root, Class<?> c)
	{
		List<Object> olist = new ArrayList<Object>();
		List<String> slist = (List<String>)getList(root);
		if(slist == null)
			return olist;
		if(!Stringable.class.isAssignableFrom(c))
			return olist;
		for(String s : slist)
		{
			try {
			Stringable obj = (Stringable)c.newInstance();
			if(obj.parseObjString(s))
				olist.add(obj);
			} 
			catch (Exception e) {
				Bukkit.getServer().getLogger().info("[Battlefields] ERROR : Invalid object in '" + file.getName() + "' at '" + root + "'.");
			}
		}
		return olist;
	}
}