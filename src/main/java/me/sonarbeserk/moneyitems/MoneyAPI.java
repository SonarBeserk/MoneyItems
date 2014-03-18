package me.sonarbeserk.moneyitems;

import org.bukkit.Location;
import org.bukkit.Material;

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
public class MoneyAPI {

    private static MoneyAPI instance = null;

    private MoneyItems plugin = null;

    public MoneyAPI(MoneyItems plugin) {

        instance = this;

        this.plugin = plugin;
    }

    /**
     * Returns the current instance of the api
     * @return the current instance of the api
     */
    public static MoneyAPI getInstance() {

        return instance;
    }

    /**
     * Spawns an amount of money at a location
     * @param location the location to spawn the money it
     * @param material the material to use for the money
     * @param stackSize the size of the itemstack to spawn (used for visual purposes)
     * @param worth the total worth of the money spawned
     */
    public void spawnMoney(Location location, Material material, int stackSize, int worth) {

        plugin.spawnMoney(location, material, stackSize, worth);
    }

    /**
     * Spawns an silent amount of money at a location (no chat message is displayed when picked up)
     * @param location the location to spawn the money it
     * @param material the material to use for the money
     * @param stackSize the size of the itemstack to spawn (used for visual purposes)
     * @param worth the total worth of the money spawned
     */
    public void spawnSilentMoney(Location location, Material material, int stackSize, int worth) {

        plugin.spawnSilentMoney(location, material, stackSize, worth);
    }

    /**
     * spawns a custom type of money at a location
     * @param location the loaction to spawn the money at
     * @param material the material to use for the money
     * @param currencyNameSingular the singular name for the currency
     * @param currencyNamePlural the plural name for the currency
     * @param stackSize the size of the itemstack to spawn (used for visual purposes)
     * @param worth the total worth of the money spawned
     */
    public void spawnCustomMoney(Location location, Material material, String currencyNameSingular, String currencyNamePlural, int stackSize, int worth) {

        plugin.spawnCustomMoney(location, material, currencyNameSingular, currencyNamePlural, stackSize, worth);
    }

    /**
     * spawns a silent custom type of money at a location (no chat message is displayed when picked up)
     * @param location the loaction to spawn the money at
     * @param material the material to use for the money
     * @param currencyNameSingular the singular name for the currency
     * @param currencyNamePlural the plural name for the currency
     * @param stackSize the size of the itemstack to spawn (used for visual purposes)
     * @param worth the total worth of the money spawned
     */
    public void spawnSilentCustomMoney(Location location, Material material, String currencyNameSingular, String currencyNamePlural, int stackSize, int worth) {

        plugin.spawnSilentCustomMoney(location, material, currencyNameSingular, currencyNamePlural, stackSize, worth);
    }

    /**
     * Returns if the uuid is found in the list of current uuids
     * @param UUID the uuid to check for
     * @return if the uuid is found in the list of current uuids
     */
    public boolean isUUIDFound(String UUID) {

        return plugin.isUUIDFound(UUID);
    }

    /**
     * Uses up a uuid. The uuid will be removed from the listings
     * @param UUID the uuid to use up
     */
    public void useUUID(String UUID) {

        plugin.useUUID(UUID);
    }

    protected void flushInstance() {

        instance = null;
    }
}
