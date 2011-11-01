package com.mc3904.battlefields.gametypes;
 
 import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;


import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.yaml.snakeyaml.error.YAMLException;

import com.mc3904.battlefields.Battlefields;
 
 public class GametypeLoader {
     private final GametypeManager gm;
     protected final Pattern[] fileFilters = new Pattern[] {
         Pattern.compile("\\.jar$"),
     };
     protected final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
     protected final Map<String, GametypeClassLoader> loaders = new HashMap<String, GametypeClassLoader>();
 
     public GametypeLoader(GametypeManager gm) 
     {
         this.gm = gm;
     }
     
     public GametypePlugin loadGametype(File file) throws InvalidPluginException, InvalidDescriptionException
     {
         GametypePlugin result = null;
         GametypeDescriptionFile description = null;
 
         if (!file.exists()) {
             throw new InvalidPluginException(new FileNotFoundException(String.format("%s does not exist", file.getPath())));
         }
         try {
             JarFile jar = new JarFile(file);
             JarEntry entry = jar.getJarEntry("gametype.yml");
 
             if (entry == null) {
                 throw new InvalidPluginException(new FileNotFoundException("Jar does not contain gametype.yml"));
             }
             InputStream stream = jar.getInputStream(entry);
             description = new GametypeDescriptionFile(stream);
             stream.close();
             jar.close();
         } catch (IOException ex) {
             throw new InvalidPluginException(ex);
         } catch (YAMLException ex) {
             throw new InvalidPluginException(ex);
         }
 
         File dataFolder = new File(file.getParentFile(), description.getName());
         if (dataFolder.exists() && !dataFolder.isDirectory()) {
             throw new InvalidPluginException(new Exception(String.format(
                 "Projected datafolder: '%s' for %s (%s) exists and is not a directory",
                 dataFolder,
                 description.getName(),
                 file
             )));
         }
 
         GametypeClassLoader loader = null;
 
         try {
             URL[] urls = new URL[1];
 
             urls[0] = file.toURI().toURL();
             loader = new GametypeClassLoader(this, urls, getClass().getClassLoader());
             Class<?> jarClass = Class.forName(description.getMain(), true, loader);
             Class<? extends GametypePlugin> plugin = jarClass.asSubclass(GametypePlugin.class);
             Constructor<? extends GametypePlugin> constructor = plugin.getConstructor();
             result = constructor.newInstance();
         } catch (Throwable ex) {
             throw new InvalidPluginException(ex);
         }
         
         // Load plugin to battlefields!
         result.initialize(this, gm.getPlugin().getServer(), description, file, loader);
 
         loaders.put(description.getName(), loader);
 
         return result;
     }
 
     protected File getDataFolder(File file) 
     {
         File dataFolder = null;
 
         String filename = file.getName();
         int index = file.getName().lastIndexOf(".");
 
         if (index != -1) {
             String name = filename.substring(0, index);
 
             dataFolder = new File(file.getParentFile(), name);
         } else {
        	 
             dataFolder = new File(file.getParentFile(), filename + "_");
         }
 
         return dataFolder;
     }
     
     public Pattern[] getPluginFileFilters() {
         return fileFilters;
     }
 
     public Class<?> getClassByName(final String name) {
         Class<?> cachedClass = classes.get(name);
 
         if (cachedClass != null) {
             return cachedClass;
         } else {
             for (String current : loaders.keySet()) {
                 GametypeClassLoader loader = loaders.get(current);
 
                 try {
                     cachedClass = loader.findClass(name, false);
                 } catch (ClassNotFoundException cnfe) {}
                 if (cachedClass != null) {
                     return cachedClass;
                 }
             }
         }
         return null;
     }
 
     public void setClass(final String name, final Class<?> clazz) {
         if (!classes.containsKey(name)) {
             classes.put(name, clazz);
             
             if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                 Class<? extends ConfigurationSerializable> serializable = (Class<? extends ConfigurationSerializable>)clazz;
                 ConfigurationSerialization.registerClass(serializable);
             }
         }
     }
     
     public void removeClass(String name) {
         Class<?> clazz = classes.remove(name);
         
         if ((clazz != null) && (ConfigurationSerializable.class.isAssignableFrom(clazz))) {
             Class<? extends ConfigurationSerializable> serializable = (Class<? extends ConfigurationSerializable>)clazz;
             ConfigurationSerialization.unregisterClass(serializable);
         }
     }
     
     public GametypeManager getGametypeManager()
     {
    	 return gm;
     }
     

     public Battlefields getPlugin()
     {
    	 return gm.getPlugin();
     }
 }