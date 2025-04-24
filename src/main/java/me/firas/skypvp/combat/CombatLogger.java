package me.firas.skypvp.combat;

import me.firas.skypvp.Main;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CombatLogger {
    public static boolean isTagged(Player player) {
        Map<UUID, Long> combatLogged = Main.getInstance().getCombatPlayer().getCombatLogged();
        return (combatLogged.containsKey(player.getUniqueId()) && (Long) combatLogged.get(player.getUniqueId()) > System.currentTimeMillis());
    }

    public static String TimeRemaining(Player player) {
        Map<UUID, Long> combatLogged = Main.getPlugin(Main.class).getCombatPlayer().getCombatLogged();
        return String.valueOf(isTagged(player) ? (((Long) combatLogged.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000L) : "&aSafe");
    }
}
