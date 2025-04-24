package me.firas.skypvp.util;

import lombok.Builder;

import java.util.HashMap;
import java.util.UUID;
@Builder
public class Cooldown {
    private final String name;
    private final long duration;
    private final HashMap<UUID, HashMap<String, Long>> cooldowns;
    public boolean isOnCooldown(UUID player) {
        if (getCooldownSeconds(player) == 0) return false;
        return true;
    }
    public void addCooldown(UUID player) {
        if (!cooldowns.containsKey(player)) {
            cooldowns.put(player, new HashMap<>());
        }
        cooldowns.get(player).put(name, System.currentTimeMillis() + (duration * 1000));
    }
    public int getCooldownSeconds(UUID player) {
        if (!cooldowns.containsKey(player)) return 0;
        if (!cooldowns.get(player).containsKey(name)) return 0;
        long remainingTime = cooldowns.get(player).get(name) - System.currentTimeMillis();
        if (remainingTime <= 0) {
            cooldowns.get(player).remove(name);
            return 0;
        }
        return (int) Math.ceil(remainingTime / 1000.0);
    }
}
