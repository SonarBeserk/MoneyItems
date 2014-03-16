package me.sonarbeserk.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
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
public class Messaging {

    private JavaPlugin plugin = null;

    public Messaging(JavaPlugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Sends a commandsender a message
     * @param receiver the commandsender to send a message to
     * @param shouldPrefix should the plugin prefix be added to the message?
     * @param colored if the message should be colored
     * @param msg the message to send
     */
    public void sendMessage(CommandSender receiver, boolean shouldPrefix, boolean colored, String msg) {

        if(receiver == null || msg == null) {return;}

        if(receiver instanceof ConsoleCommandSender) {

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }

        if(colored) {

            if(shouldPrefix) {

                String prefix = plugin.getConfig().getString("settings.prefix").replace("{name}", plugin.getName());

                receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                return;
            }

            receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            return;
        } else {

            if(shouldPrefix) {

                String prefix = plugin.getConfig().getString("settings.prefix").replace("{name}", plugin.getName());

                receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', prefix + msg)));
                return;
            }

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }
    }

    /**
     * Sends a player a message
     * @param receiver the plater to send a message to
     * @param shouldPrefix should the plugin prefix be added to the message?
     * @param colored if the message should be colored
     * @param msg the message to send
     */
    public void sendMessage(Player receiver, boolean shouldPrefix, boolean colored, String msg) {

        if(receiver == null || msg == null) {return;}

        if(colored) {

            if(shouldPrefix) {

                String prefix = plugin.getConfig().getString("settings.prefix").replace("{name}", plugin.getName());

                receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                return;
            }

            receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            return;
        } else {

            if(shouldPrefix) {

                String prefix = plugin.getConfig().getString("settings.prefix").replace("{name}", plugin.getName());

                receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', prefix + msg)));
                return;
            }

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }
    }

    /**
     * Sends a named player a message
     * @param playerName the plater to send a message to
     * @param shouldPrefix should the plugin prefix be added to the message?
     * @param colored if the message should be colored
     * @param msg the message to send
     */
    public void sendMessage(String playerName, boolean shouldPrefix, boolean colored, String msg) {

        Player receiver = plugin.getServer().getPlayer(playerName);

        if(receiver == null || msg == null) {return;}

        if(colored) {

            if(shouldPrefix) {

                String prefix = plugin.getConfig().getString("settings.prefix").replace("{name}", plugin.getName());

                receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                return;
            }

            receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            return;
        } else {

            if(shouldPrefix) {

                String prefix = plugin.getConfig().getString("settings.prefix").replace("{name}", plugin.getName());

                receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', prefix + msg)));
                return;
            }

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }
    }

    /**
     * Logs a debug message to the JavaPlugin's logger
     * @param message the message to log
     */
    public void debug(String message) {

        if(!plugin.getConfig().getBoolean("settings.debug")) {return;}

        plugin.getLogger().info(message);
    }
}
