package me.sonarbeserk.moneyitems.events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * ********************************************************************************************************************
 * <p/>
 * MoneyItems - Bukkit plugin that is a developer tool to drop items that when picked up give you money
 * ===========================================================================
 * <p/>
 * Copyright (C) 2014 by SonarBeserk
 * http://dev.bukkit.org/bukkit-plugins/moneyitems/
 * <p/>
 * **********************************************************************************************************************
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * *********************************************************************************************************************
 */
public class CustomMoneyDropEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private Location location = null;

    private ItemStack itemStack = null;

    private String currencyNameSingular = null;

    private String currencyNamePlural = null;

    private int worth = 0;

    private boolean silent;

    /**
     * Creates a CustomMoneyDropEvent
     *
     * @param location             the location the event happened at
     * @param itemStack            the itemstack involved in the event
     * @param currencyNameSingular the singular name of the currency
     * @param currencyNamePlural   the plural name of the currency
     * @param worth                the total worth of the money drop
     * @param silent               if the money was silent when it was picked up
     */
    public CustomMoneyDropEvent(Location location, ItemStack itemStack, String currencyNameSingular, String currencyNamePlural, int worth, boolean silent) {

        this.location = location;

        this.itemStack = itemStack;

        this.currencyNameSingular = currencyNameSingular;

        this.currencyNamePlural = currencyNamePlural;

        this.worth = worth;

        this.silent = silent;
    }

    public static HandlerList getHandlerList() {

        return handlers;
    }

    public HandlerList getHandlers() {

        return handlers;
    }

    public boolean isCancelled() {

        return cancelled;
    }

    public void setCancelled(boolean cancelled) {

        this.cancelled = cancelled;
    }

    /**
     * Returns the location of the drop event
     *
     * @return the location of the drop event
     */
    public Location getLocation() {

        return location;
    }

    /**
     * Returns the itemstack involved
     *
     * @return the itemstack involved
     */
    public ItemStack getItemStack() {

        return itemStack;
    }

    /**
     * Returns the singular name of the currency
     *
     * @return the singular name of the currency
     */
    public String getCurrencyNameSingular() {

        return currencyNameSingular;
    }

    /**
     * Returns the plural name of the currency
     *
     * @return the plural name of the currency
     */
    public String getCurrencyNamePlural() {

        return currencyNamePlural;
    }

    /**
     * Returns the proper name based on the worth. 0 = plural, 1 = singular, >1 = plural
     *
     * @return the proper name based on the worth. 0 = plural, 1 = singular, >1 = plural
     */
    public String getAppropriateCurrencyName() {

        if (worth == 0) {

            return currencyNamePlural;
        } else if (worth == 1) {

            return currencyNameSingular;
        } else if (worth > 1) {

            return currencyNamePlural;
        }

        return null;
    }

    /**
     * Returns the total worth of the item
     *
     * @return the total worth of the item
     */
    public int getWorth() {

        return worth;
    }

    /**
     * Returns if the money is silent (no message sent on pickup)
     *
     * @return if the money is silent (no message sent on pickup)
     */
    public boolean isSilent() {

        return silent;
    }
}
