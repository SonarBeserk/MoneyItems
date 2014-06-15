package me.sonarbeserk.moneyitems;

import me.sonarbeserk.moneyitems.events.CustomMoneyDropEvent;
import me.sonarbeserk.moneyitems.events.NormalMoneyDropEvent;
import me.sonarbeserk.moneyitems.listeners.ItemPickupListener;
import me.sonarbeserk.moneyitems.utils.BCrypt;
import me.sonarbeserk.plugin.BeserkJavaPlugin;
import me.sonarbeserk.plugin.BeserkUpdatingJavaPlugin;
import me.sonarbeserk.updating.UpdateListener;
import me.sonarbeserk.updating.Updater;
import me.sonarbeserk.utils.Data;
import me.sonarbeserk.utils.Language;
import me.sonarbeserk.utils.Messaging;
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
public class MoneyItems extends BeserkUpdatingJavaPlugin {

    public Economy economy = null;

    private List<String> uuids = null;

    public void onEnable() {
        super.onEnable();

        if(getData().get("uuids") != null) {

            uuids = (List<String>) getData().get("uuids");
        } else {

            uuids = new ArrayList<String>();
        }

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

        new MoneyAPI(this);

        getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);
    }

    private int getProjectID () {
        return 00000; // Replace when releasing
    }

    private boolean setupEconomy() {

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if(economyProvider != null) {

            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    protected void spawnMoney(Location location, Material material, int stackSize, int worth) {

        if(material == null || location == null || stackSize == 0 || worth == 0) {return;}

        ItemStack itemStack = new ItemStack(material, stackSize);

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("money-item");
        lore.add("total-worth:" + worth);

        Random uuidRandom = new Random();

        String hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;

        boolean added = false;

        while(!added) {

            if(!MoneyAPI.getInstance().isUUIDFound(hashedUuid)) {

                uuids.add(hashedUuid);
                added = true;
            } else {

                hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;
            }
        }

        lore.add("uuid:" + hashedUuid);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        NormalMoneyDropEvent event = new NormalMoneyDropEvent(location, itemStack, worth, false);

        getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {return;}

        location.getWorld().dropItemNaturally(location, itemStack);
    }

    protected void spawnSilentMoney(Location location, Material material, int stackSize, int worth) {

        if(material == null || location == null || stackSize == 0 || worth == 0) {return;}

        ItemStack itemStack = new ItemStack(material, stackSize);

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("money-item");
        lore.add("total-worth:" + worth);

        Random uuidRandom = new Random();

        String hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;

        boolean added = false;

        while(!added) {

            if(!MoneyAPI.getInstance().isUUIDFound(hashedUuid)) {

                uuids.add(hashedUuid);
                added = true;
            } else {

                hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;
            }
        }

        lore.add("uuid:" + hashedUuid);

        lore.add("silent:true");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        NormalMoneyDropEvent event = new NormalMoneyDropEvent(location, itemStack, worth, true);

        getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {return;}

        location.getWorld().dropItemNaturally(location, itemStack);
    }

    protected void spawnCustomMoney(Location location, Material material, String currencyNameSingular, String currencyNamePlural, int stackSize, int worth) {

        if(location == null || material == null || currencyNameSingular == null || currencyNamePlural == null || stackSize == 0 || worth == 0) {return;}

        if(currencyNameSingular.equalsIgnoreCase("") || currencyNameSingular.equalsIgnoreCase(" ") || currencyNamePlural.equalsIgnoreCase("") || currencyNamePlural.equalsIgnoreCase(" ")) {return;}

        ItemStack itemStack = new ItemStack(material, stackSize);

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("custom-money-item");
        lore.add("total-worth:" + worth);

        Random uuidRandom = new Random();

        String hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;

        boolean added = false;

        while(!added) {

            if(!MoneyAPI.getInstance().isUUIDFound(hashedUuid)) {

                uuids.add(hashedUuid);
                added = true;
            } else {

                hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;
            }
        }

        lore.add("uuid:" + hashedUuid);

        lore.add("currency-name-singular:" + currencyNameSingular);
        lore.add("currency-name-plural:" + currencyNamePlural);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        CustomMoneyDropEvent event = new CustomMoneyDropEvent(location, itemStack, currencyNameSingular, currencyNamePlural, worth, false);

        getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {return;}

        location.getWorld().dropItemNaturally(location, itemStack);
    }

    protected void spawnSilentCustomMoney(Location location, Material material, String currencyNameSingular, String currencyNamePlural, int stackSize, int worth) {

        if(location == null || material == null || currencyNameSingular == null || currencyNamePlural == null || stackSize == 0 || worth == 0) {return;}

        if(currencyNameSingular.equalsIgnoreCase("") || currencyNameSingular.equalsIgnoreCase(" ") || currencyNamePlural.equalsIgnoreCase("") || currencyNamePlural.equalsIgnoreCase(" ")) {return;}

        ItemStack itemStack = new ItemStack(material, stackSize);

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("custom-money-item");
        lore.add("total-worth:" + worth);

        Random uuidRandom = new Random();

        String hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;

        boolean added = false;

        while(!added) {

            if(!MoneyAPI.getInstance().isUUIDFound(hashedUuid)) {

                uuids.add(hashedUuid);
                added = true;
            } else {

                hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt()) + "|" + stackSize;
            }
        }

        lore.add("uuid:" + hashedUuid);

        lore.add("silent:true");

        lore.add("currency-name-singular:" + currencyNameSingular);
        lore.add("currency-name-plural:" + currencyNamePlural);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        CustomMoneyDropEvent event = new CustomMoneyDropEvent(location, itemStack, currencyNameSingular, currencyNamePlural, worth, true);

        getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {return;}

        location.getWorld().dropItemNaturally(location, itemStack);
    }

    protected boolean isUUIDFound(String UUID) {

        if(uuids == null || uuids.size() == 0) {return false;}

        if(uuids.contains(UUID)) {return true;}

        return false;
    }

    protected void useUUID(String UUID) {

        if(uuids == null || uuids.size() == 0) {return;}

        if(!uuids.contains(UUID)) {return;}

        uuids.remove(UUID);
    }

    private boolean saveData() {
        return false;
    }

    public void onDisable() {

        if(MoneyAPI.getInstance() != null) {

            MoneyAPI.getInstance().flushInstance();
        }

        getData().set("uuids", uuids);

        super.onDisable();
    }
}
