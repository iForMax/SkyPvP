package me.firas.skypvp.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public final class PlayerKillEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player killer;

    public PlayerKillEvent(Player killer) {
        this.killer = killer;
    }

    public Player getKiller() {
        return this.killer;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
