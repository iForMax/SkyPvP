package me.firas.skypvp.trails;

import me.firas.skypvp.Main;
import me.firas.skypvp.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TrailListener implements Listener {

    public static ConcurrentHashMap<Entity, Effect> trails = new ConcurrentHashMap<>();
    public static boolean started = false;

    public static void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            try {
                if (trails.isEmpty()){
                    return;
                }
                for(Map.Entry<Entity, Effect> entry : trails.entrySet()) {
                    Entity entity = entry.getKey();
                    Effect effect = entry.getValue();
                    if(entity == null || entity.isDead() || entity.isOnGround()) {
                        trails.remove(entity);
                        continue;
                    }
                    if (entry.getValue().equals(Effect.COLOURED_DUST)){
                        entity.getWorld().playEffect(new Location(entity.getWorld(),entity.getLocation().getX(),entity.getLocation().getY(),entity.getLocation().getZ()), effect,20);
                        entity.getWorld().playEffect(new Location(entity.getWorld(),entity.getLocation().getX(),entity.getLocation().getY(),(entity.getLocation().getZ())+0.1), effect, 20);
                        entity.getWorld().playEffect(new Location(entity.getWorld(),(entity.getLocation().getX()+0.1),entity.getLocation().getY(),entity.getLocation().getZ()), effect, 20);
                        entity.getWorld().playEffect(new Location(entity.getWorld(),entity.getLocation().getX(),entity.getLocation().getY()+0.1,entity.getLocation().getZ()), effect, 20);
                        entity.getWorld().playEffect(new Location(entity.getWorld(),entity.getLocation().getX(),entity.getLocation().getY()-0.1,entity.getLocation().getZ()), effect, 20);
                    } else{
                        entity.getWorld().playEffect(entity.getLocation(), effect, 20);

                    }

                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }, 1, 2);
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        Entity entity = e.getProjectile();
        if(entity instanceof Arrow) {
            Arrow arrow = (Arrow) e.getProjectile();
            for (Region region : Main.getInstance().regionMap.values()) {
                if (region.contains(e.getEntity().getLocation())) {
                    e.setCancelled(true);
                    ((Player) e.getEntity()).updateInventory();
                    return;
                }
            }
            String trail = Main.getInstance().get((Player) arrow.getShooter()).getTrail();
            Effect effect = Effect.HEART;
            if (trail.equalsIgnoreCase("None")) return;
            if (trail.equalsIgnoreCase("heart"))
                effect = Effect.HEART;
            if (trail.equalsIgnoreCase("flame"))
                effect = Effect.LAVA_POP;
            if (trail.equalsIgnoreCase("rainbow"))
                effect = Effect.COLOURED_DUST;
            if (trail.equalsIgnoreCase("magic")){
                effect = Effect.WITCH_MAGIC;
            }
            if(!started) {
                started = true;
                start();
            }
            trails.put(entity, effect);
        }
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow))return;
        event.getEntity().remove();
    }
}
