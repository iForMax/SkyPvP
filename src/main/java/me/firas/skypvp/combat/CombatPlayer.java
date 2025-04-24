package me.firas.skypvp.combat;

import me.firas.skypvp.Main;
import me.firas.skypvp.combat.events.PlayerEnterCombatEvent;
import me.firas.skypvp.combat.events.PlayerLeaveCombatEvent;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.*;

public class CombatPlayer {

    public final Map<UUID, Long> combatLogged;

    public final HashMap<Player, Player> combatPlayer = new HashMap<>();
    private final Set<UUID> players;
    private final Map<UUID, Integer> taskActionBar;
    private final Map<UUID, Integer> taskCombat;
    public Map<UUID, Long> getCombatLogged() {
        return this.combatLogged;
    }
    public boolean isInCombat(Player p){
        return combatLogged.containsKey(p.getUniqueId());
    }

    public CombatPlayer() {
        this.combatLogged = new HashMap<>();
        this.taskActionBar = new HashMap<>();
        this.taskCombat = new HashMap<>();
        this.players = new HashSet<>();
        addOnlinePlayers();
    }

    private void addOnlinePlayers() {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers())
            this.players.add(player.getUniqueId());
    }

    public void addPlayer(Player player) {
        this.players.add(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUniqueId());
        this.combatLogged.remove(player.getUniqueId());
        this.combatPlayer.remove(player);
        if (this.taskCombat.get(player.getUniqueId()) != null)
            stopTasks(player);
    }



    public void addCombat(Player player) {
        if (player.getGameMode().equals(GameMode.SPECTATOR)){
            return;
        }
        if (isTagged(player)) {
            stopTasks(player);
            PlayerEnterCombatEvent enterCombatEvent = new PlayerEnterCombatEvent(player);
            Main.getInstance().getServer().getPluginManager().callEvent(enterCombatEvent);
        }
        this.combatLogged.put(player.getUniqueId(), System.currentTimeMillis() + 15000L);
        this.taskCombat.put(player.getUniqueId(), Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (this.taskActionBar.containsKey(player.getUniqueId()))
                    Main.getInstance().getServer().getScheduler().cancelTask((Integer) this.taskActionBar.get(player.getUniqueId()));

                if (this.combatLogged.containsKey(player.getUniqueId())) {
                    PlayerLeaveCombatEvent leaveCombatEvent = new PlayerLeaveCombatEvent(player);
                    Main.getInstance().getServer().getPluginManager().callEvent(leaveCombatEvent);
                    this.combatLogged.remove(player.getUniqueId());
                    this.combatPlayer.remove(player);
                }
            }, 300L).getTaskId());
    }

    private void stopTasks(Player player) {
        if (this.taskActionBar.containsKey(player.getUniqueId()))
            Main.getInstance().getServer().getScheduler().cancelTask((Integer) this.taskActionBar.get(player.getUniqueId()));
        if (this.taskCombat.containsKey(player.getUniqueId()))
            Main.getInstance().getServer().getScheduler().cancelTask((Integer) this.taskCombat.get(player.getUniqueId()));
    }

    private boolean isTagged(Player player) {
        return (this.combatLogged.containsKey(player.getUniqueId()) && (Long) this.combatLogged.get(player.getUniqueId()) > System.currentTimeMillis());
    }
}
