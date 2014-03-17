package me.sonarbeserk.moneyitems.events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class NormalMoneyDropEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private Location location = null;

    private ItemStack itemStack = null;

    private int worth = 0;

    public NormalMoneyDropEvent(Location location, ItemStack itemStack, int worth) {

        this.location = location;

        this.itemStack = itemStack;

        this.worth = worth;
    }

    public HandlerList getHandlers() {

        return handlers;
    }

    public static HandlerList getHandlerList() {

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
     * @return the location of the drop event
     */
    public Location getLocation() {

        return location;
    }

    /**
     * Returns the itemstack involved
     * @return the itemstack involved
     */
    public ItemStack getItemStack() {

        return itemStack;
    }

    /**
     * Returns the total worth of the item
     * @return the total worth of the item
     */
    public int getWorth() {

        return worth;
    }
}
