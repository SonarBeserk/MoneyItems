package me.sonarbeserk.moneyitems;

import me.sonarbeserk.moneyitems.listeners.ItemPickupListener;
import me.sonarbeserk.moneyitems.listeners.PlayerTestListener;
import me.sonarbeserk.moneyitems.utils.BCrypt;
import me.sonarbeserk.moneyitems.utils.Data;
import me.sonarbeserk.moneyitems.utils.Language;
import me.sonarbeserk.moneyitems.utils.Messaging;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Economy economy = null;

    private Language language = null;

    private Data data = null;

    private Messaging messaging = null;

    private List<String> uuids = null;

    public void onEnable() {

        saveDefaultConfig();

        language = new Language(this);

        data = new Data(this);

        messaging = new Messaging(this);

        if(getServer().getPluginManager().getPlugin("Vault") != null && getServer().getPluginManager().getPlugin("Vault").isEnabled()) {

            setupEconomy();

            if(economy == null) {

                getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("severe-no-economy-found"))));
                getServer().getPluginManager().disablePlugin(this);
            } else {

                getLogger().info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("hooked-vault"))));
            }
        } else {

            getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("severe-vault-not-found"))));
            getServer().getPluginManager().disablePlugin(this);
        }

        if(!getServer().getPluginManager().isPluginEnabled(this)) {return;}

        if(data.get("uuids") != null) {

            uuids = (List<String>) data.get("uuids");
        } else {

            uuids = new ArrayList<String>();
        }

        new MoneyAPI(this);

        getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);
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

    protected void spawnMoney(Material material, Location location, int amount, int worth) {

        if(material == null || location == null || amount == 0 || worth == 0) {return;}

        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("money-item");
        lore.add("total-worth:" + worth);

        Random uuidRandom = new Random();

        String hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + amount;

        boolean added = false;

        while(!added) {

            if(!MoneyAPI.getInstance().isUUIDFound(hashedUuid)) {

                uuids.add(hashedUuid);
                added = true;
            } else {

                hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + amount;
            }
        }

        lore.add("uuid:" + hashedUuid);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        location.getWorld().dropItemNaturally(location, itemStack);
    }

    protected boolean isUUIDFound(String UUID) {

        if(uuids == null || uuids.size() == 0) {return false;}

        if(uuids.contains(UUID)) {return true;}

        return false;
    }

    public void onDisable() {

       MoneyAPI.getInstance().flushInstance();

        data = null;

        messaging = null;

        language = null;
    }
}
