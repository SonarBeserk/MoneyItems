package me.sonarbeserk.moneyitems;

import me.sonarbeserk.moneyitems.utils.BCrypt;
import me.sonarbeserk.moneyitems.utils.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
public class MoneyAPI {

    private static MoneyAPI instance = null;

    private Data data = null;

    private List<String> uuids = null;

    public MoneyAPI(Data data) {

        instance = this;

        this.data = data;

        if(data.get("uuids") != null) {

            uuids = (List<String>) data.get("uuids");
        } else {

            uuids = new ArrayList<String>();
        }
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

        if(material == null || location == null || amount == 0 || worth == 0) {return;}

        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<String>();

        lore.add("money-item");
        lore.add("item-worth:" + worth);

        Random uuidRandom = new Random();

        String hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt());

        while(uuids.contains(hashedUuid + "|" + amount)) {

            hashedUuid = BCrypt.hashpw(String.valueOf(uuidRandom.nextInt()), BCrypt.gensalt());
        }

        uuids.add(hashedUuid + "|" + amount);

        lore.add("uuid:" + hashedUuid + "|" + amount);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        location.getWorld().dropItemNaturally(location, itemStack);
    }
}
