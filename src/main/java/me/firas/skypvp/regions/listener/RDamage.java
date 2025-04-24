package me.firas.skypvp.regions.listener;

import lombok.Data;
import me.firas.skypvp.Main;
import me.firas.skypvp.regions.Region;
import me.firas.skypvp.regions.Regions;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@Data
public class RDamage implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player target = (Player) event.getEntity();
        Player player = null;
        if (event.getDamager() instanceof Projectile) {
            if (((Projectile) event.getDamager()).getShooter() instanceof Player)
                player = (Player) ((Projectile) event.getDamager()).getShooter();
        } else if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();
        }
        if (player == null)
            return;
        if (player == target)
            return;
        for (Region region : Main.getInstance().regionMap.values()) {
            if (Main.getInstance().getRegionsHandler().getManager().getBoolean("regions."+region.getName()+".pvp")) return;
            if (region.contains(player.getLocation()) || region.contains(target.getLocation())) {
                event.setCancelled(true);
                return;
            }

        }
        Main.getInstance().getCombatPlayer().addCombat(player);
        Main.getInstance().getCombatPlayer().addCombat(target);
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                Player player = (Player) event.getEntity();
                if (shooter.equals(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void fallDamage(EntityDamageEvent event){
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        event.setCancelled(true);
    }
}
