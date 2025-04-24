package me.firas.skypvp.combat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveCombatEvent extends Event {
    private final Player player;

    public Player getPlayer() {
        return this.player;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerLeaveCombatEvent(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
