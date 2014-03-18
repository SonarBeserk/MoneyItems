package me.sonarbeserk.moneyitems.listeners;

import me.sonarbeserk.moneyitems.MoneyAPI;
import me.sonarbeserk.moneyitems.MoneyItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

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
public class ItemPickupListener implements Listener {

    public MoneyItems plugin = null;

    public ItemPickupListener(MoneyItems plugin) {

        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void playerNormalPickup(PlayerPickupItemEvent e) {

        if(e.getPlayer() == null || e.getItem() == null) {return;}

        if(e.getItem().getItemStack().getItemMeta().getLore() == null) {return;}

        if(!e.getItem().getItemStack().getItemMeta().getLore().contains("money-item")) {return;}

        boolean remove = false;

        boolean validUUID = false;

        String uuid = null;

        int worth = 0;

        boolean silent = false;

        for(String loreString: e.getItem().getItemStack().getItemMeta().getLore()) {

            if(loreString.startsWith("uuid:")) {

                String splitString[] = loreString.split("\\:");

                if(splitString.length == 1) {remove = true; continue;}

                if(MoneyAPI.getInstance().isUUIDFound(splitString[1])) {

                    validUUID = true;
                    uuid = splitString[1];
                    continue;
                } else {

                    remove = true;
                    continue;
                }
            }

            if(loreString.startsWith("total-worth:")) {

                String splitString[] = loreString.split("\\:");

                if(splitString.length == 1) {remove = true; continue;}

                worth = Integer.parseInt(splitString[1].replaceAll("[a-zA-Z]", ""));

                continue;
            }

            if(loreString.startsWith("silent:true")) {

                silent = true;
            }
        }

        if(remove) {

            e.setCancelled(true);
            e.getItem().remove();
        }

        if(validUUID) {

            String currencyName = null;

            if(worth == 0) {

                currencyName = plugin.economy.currencyNamePlural();
            } else if(worth == 1) {

                currencyName = plugin.economy.currencyNameSingular();
            } else if(worth > 1) {

                currencyName = plugin.economy.currencyNamePlural();
            }

            plugin.economy.depositPlayer(e.getPlayer().getName(), worth);

            if(!silent) {

                plugin.getMessaging().sendMessage(e.getPlayer(), false, true, plugin.getLanguage().getMessage("pickup-money").replace("{amount}", worth + "").replace("{currency}", currencyName));
            }

            MoneyAPI.getInstance().useUUID(uuid);

            e.setCancelled(true);
            e.getItem().remove();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void playerCustomPickup(PlayerPickupItemEvent e) {

        if(e.getPlayer() == null || e.getItem() == null) {return;}

        if(e.getItem().getItemStack().getItemMeta().getLore() == null) {return;}

        if(!e.getItem().getItemStack().getItemMeta().getLore().contains("custom-money-item")) {return;}

        boolean remove = false;

        boolean validUUID = false;

        String uuid = null;

        String currencyName = null;

        String currencyNameSingular = null;

        String currencyNamePlural = null;

        int worth = 0;

        boolean silent = false;

        for(String loreString: e.getItem().getItemStack().getItemMeta().getLore()) {

            if(loreString.startsWith("uuid:")) {

                String splitString[] = loreString.split("\\:");

                if(splitString.length == 1) {remove = true; continue;}

                if(MoneyAPI.getInstance().isUUIDFound(splitString[1])) {

                    validUUID = true;
                    uuid = splitString[1];
                    continue;
                } else {

                    remove = true;
                    continue;
                }
            }

            if(loreString.startsWith("total-worth:")) {

                String splitString[] = loreString.split("\\:");

                if(splitString.length == 1) {remove = true; continue;}

                worth = Integer.parseInt(splitString[1].replaceAll("[a-zA-Z]", ""));

                continue;
            }

            if(loreString.startsWith("currency-name-singular:")) {

                String splitString[] = loreString.split("\\:");

                if(splitString.length == 1) {remove = true; continue;}

                currencyNameSingular = splitString[1];

                continue;
            }

            if(loreString.startsWith("currency-name-plural:")) {

                String splitString[] = loreString.split("\\:");

                if(splitString.length == 1) {remove = true; continue;}

                currencyNamePlural = splitString[1];

                continue;
            }

            if(loreString.startsWith("silent:true")) {

                silent = true;
            }
        }

        if(remove) {

            e.setCancelled(true);
            e.getItem().remove();
        }

        if(validUUID) {

            if(worth == 0) {

                currencyName = currencyNamePlural;
            } else if(worth == 1) {

                currencyName = currencyNameSingular;
            } else if(worth > 1) {

                currencyName = currencyNamePlural;
            }

            if(!silent) {

                plugin.getMessaging().sendMessage(e.getPlayer(), false, true, plugin.getLanguage().getMessage("pickup-money").replace("{amount}", worth + "").replace("{currency}", currencyName));
            }

            MoneyAPI.getInstance().useUUID(uuid);

            e.setCancelled(true);
            e.getItem().remove();
        }
    }
}
