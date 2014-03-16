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
     * @param material the material to use for the money
     * @param location the location to spawn the money it
     * @param amount the amount of money to spawn (used for visual purposes)
     * @param worth the total worth of the money spawned
     */
    public void spawnMoney(Material material, Location location, int amount, int worth) {

        plugin.spawnMoney(material, location, amount, worth);
    }

    /**
     * Returns if the uuid is found in the list of current uuids
     * @param UUID the uuid to check for
     * @return if the uuid is found in the list of current uuids
     */
    public boolean isUUIDFound(String UUID) {

        return plugin.isUUIDFound(UUID);
    }
}
