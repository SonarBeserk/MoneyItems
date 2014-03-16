package me.sonarbeserk.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

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
public class Language {

    private JavaPlugin plugin = null;

    private FileConfiguration locale = null;
    private File localeFile = null;

    public Language(JavaPlugin plugin) {

        //lang directory check/create if not found
        new File(plugin.getDataFolder(), "lang").mkdir();

        this.plugin = plugin;

        saveDefault();
    }

    public void reload() {

        if (plugin.getConfig() == null) {

            plugin.reloadConfig();
        }

        saveDefault();

        if (localeFile == null) {

            localeFile = new File(plugin.getDataFolder() + "/" + "lang", plugin.getConfig().getString("settings.language") + ".yml");
        }

        locale = YamlConfiguration.loadConfiguration(localeFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource("lang" + "/" + plugin.getConfig().getString("settings.language") + ".yml");

        if (defConfigStream != null) {

            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            locale.setDefaults(defConfig);
        } else {

            plugin.getLogger().severe("Unsupported language file in use. No defaults able to be loaded.");
            return;
        }
    }

    private FileConfiguration getFileConfiguration() {

        if (locale == null) {

            reload();
        }

        return locale;
    }

    private void saveDefault() {

        if (plugin.getConfig() == null) {

            plugin.reloadConfig();
        }

        if (plugin.getConfig().getString("settings.language") == null || plugin.getConfig().getString("settings.language").equalsIgnoreCase("")) {

            plugin.getLogger().warning("Invalid Language Specified! Disabling.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        if (localeFile == null) {

            localeFile = new File(plugin.getDataFolder() + "/" + "lang" + "/" + plugin.getConfig().getString("settings.language") + ".yml");
        }

        if (!localeFile.exists()) {

            plugin.saveResource("lang" + "/" + plugin.getConfig().getString("settings.language") + ".yml", false);
        }
    }

    private void save() {

        if (locale == null || localeFile == null) {return;}

        try {

            getFileConfiguration().save(localeFile);
        } catch (IOException ex) {

            plugin.getLogger().log(Level.SEVERE, "Could not save language file to " + localeFile, ex);
        }
    }

    /**
     * Sets the message specified, does not persist
     * @param messagePath the path to the message
     * @param message the message
     */
    public void setMessage(String messagePath, String message) {

        getFileConfiguration().set(messagePath, message);
    }

    /**
     * Returns the message specified if found
     * @param messagePath the path to the message
     * @return the message specified if found
     */
    public String getMessage(String messagePath) {

        if (getFileConfiguration().get(messagePath) == null) {

            plugin.getLogger().severe("(" + messagePath + ") in locale could not be found!");
            return null;
        }

        return getFileConfiguration().getString(messagePath);
    }
}
