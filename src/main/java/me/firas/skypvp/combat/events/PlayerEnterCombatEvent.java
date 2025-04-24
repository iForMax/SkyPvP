package me.firas.skypvp.combat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEnterCombatEvent extends Event {
    private final Player player;

    public Player getPlayer() {
        return this.player;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerEnterCombatEvent(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
