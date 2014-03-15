package me.sonarbeserk.moneyitems;

import me.sonarbeserk.moneyitems.utils.Data;
import me.sonarbeserk.moneyitems.utils.Language;
import me.sonarbeserk.moneyitems.utils.Messaging;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/***********************************************************************************************************************
 *
 * MoneyItems - Bukkit plugin that is a developer tool to drop items that when picked up give you money
 * ===========================================================================
 *
 * Copyright (C) 2014 by SonarBeserk
 * http://dev.bukkit.org/bukkit-plugins/moneyitems/
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
public class MoneyItems extends JavaPlugin {

    public static Economy economy = null;

    private Language language = null;

    private Data data = null;

    private Messaging messaging = null;

    public void onEnable() {

        saveDefaultConfig();

        language = new Language(this);

        data = new Data(this);

        messaging = new Messaging(this);

        if(getServer().getPluginManager().getPlugin("Vault") != null && getServer().getPluginManager().getPlugin("Vault").isEnabled()) {

            setupEconomy();

            if(economy == null) {

                getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("warning-no-economy-found"))));
            } else {

                getLogger().info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("hooked-vault"))));
            }
        } else {

            getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("warning-vault-not-found"))));
        }
    }

    private boolean setupEconomy() {

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if(economyProvider != null) {

            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    /**
     * Returns the language in use
     * @return the language in use
     */
    public Language getLanguage() {

        return language;
    }

    /**
     * Returns the data instance
     * @return the data instance
     */
    public Data getData() {

        return data;
    }

    /**
     * Returns the plugin messaging instance
     * @return the plugin messaging instance
     */
    public Messaging getMessaging() {

        return messaging;
    }

    public void onDisable() {

        data = null;

        messaging = null;

        language = null;
    }
}
