package me.sonarbeserk.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/***********************************************************************************************************************
 *
 * BeserkUtils - Premade classes for use in my bukkit plugins
 * ===========================================================================
 *
 * Copyright (C) 2014 by SonarBeserk
 * https://github.com/SonarBeserk/BeserkUtils
 *
 ***********************************************************************************************************************
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ***********************************************************************************************************************/
public class Data {

    private JavaPlugin plugin = null;

	private FileConfiguration data = null;
	private File dataFile = null;
	
	public Data(JavaPlugin plugin) {
		
		this.plugin = plugin;
	}

    /**
     * Reloads the data file
     */
	public void reload() {

        saveDefault();

		if(dataFile == null) {
			
			dataFile = new File(plugin.getDataFolder(), "data.yml");
		}
		
		data = YamlConfiguration.loadConfiguration(dataFile);
		
		InputStream defConfigStream = plugin.getResource("data.yml");
		
		if(defConfigStream != null) {
			
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			data.setDefaults(defConfig);
		} else {
			
			plugin.getLogger().severe("Unable to load data.");
			return;
		}
	}
	
	private FileConfiguration getFileConfiguration() {
		
		if(data == null) {
			
			reload();
		}
		
		return data;
	}

    /**
     * Saves the default version of the data file if it was not found
     */
	private void saveDefault() {
		
		if(dataFile == null) {
			
			dataFile = new File(plugin.getDataFolder() + "/" + "data.yml");
		}
		
		if(!dataFile.exists()) {
			
			plugin.saveResource("data.yml", false);
			return;
		}
	}

    /**
     * Saves the current state of the data file, also destroys any comments in the file
     */
	public void save() {
		
		if((data == null)||(dataFile == null)) {return;}
		
		try {
			
			getFileConfiguration().save(dataFile);
		} catch (IOException ex) {
			
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + dataFile, ex);
		}
	}

    /**
     * Sets the value at the path specified
     * @param path the path to set at
     * @param value the value to set
     */
    public void set(String path, Object value) {

        data.set(path, value);
    }

    /**
     * Returns a data entry from the path specified
     * @param path the path to the entry
     * @return a data entry from the path specified
     */
    public Object get(String path) {

        return getFileConfiguration().get(path);
    }

    /**
     * Returns the configuration section at the path specified
     * @param path the path to the configuration section
     * @return the configuration section at the path specified
     */
    public ConfigurationSection getConfigurationSection(String path) {

        return getFileConfiguration().getConfigurationSection(path);
    }

    /**
     * Creates a configuration section with a map at the path specified
     * @param path the path to create the section at
     * @param map the value to set
     */
    public void createSection(String path, Map<?, ?> map) {

        getFileConfiguration().createSection(path, map);
    }
}
