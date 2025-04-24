package me.firas.skypvp.generators.xpGen;

import me.firas.skypvp.Main;
import me.firas.skypvp.nms.wrapper.living.WrapperEntityArmorStand;
import me.firas.skypvp.util.ParticleEffect;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;

import static me.firas.skypvp.Main.getProgressBar;

public class XpGenerator {

    public HashSet<Player> viewers = new HashSet<>();
    public WrapperEntityArmorStand entityLiving1;
    public WrapperEntityArmorStand entityLiving2;
    public WrapperEntityArmorStand entityLiving3;
    public WrapperEntityArmorStand entityLiving4;

    public int times = 1;
    public int ticks = 2;


    public boolean firstTime = true;



    public XpGenerator(WrapperEntityArmorStand entity1, WrapperEntityArmorStand entity2, WrapperEntityArmorStand entity3, WrapperEntityArmorStand entity4){
        entityLiving1 = entity1;
        entityLiving2 = entity2;
        entityLiving3 = entity3;
        entityLiving4 = entity4;

    }
    public void tick(){
        if (entityLiving1.isDespawned()){
            XpManager.pool.remove(entityLiving1.getId(), entityLiving1);
            return;
        }
        Location hologramLoc = entityLiving1.getLocation();
        for(Player p : Bukkit.getOnlinePlayers()) {

            Location loc = p.getLocation();

            boolean canSee = viewers.contains(p); // hashset
            if(canSee && !p.isOnline()){
                viewers.remove(p);
                continue;
            }

            boolean inRange = hologramLoc.distanceSquared(loc) <= 600.0D;
            if(!inRange && canSee) {
                entityLiving1.despawn(p);
                entityLiving2.despawn(p);
                entityLiving3.despawn(p);
                entityLiving4.despawn(p);
                viewers.remove(p);
            } else if(inRange && !canSee) {
                entityLiving1.spawnClient(p);
                entityLiving2.spawnClient(p);
                entityLiving3.spawnClient(p);
                entityLiving4.spawnClient(p);
                entityLiving1.sendUpdatedmetadata(p);
                entityLiving2.sendUpdatedmetadata(p);
                entityLiving3.sendUpdatedmetadata(p);
                entityLiving4.sendUpdatedmetadata(p);
                viewers.add(p);
            }
        }
        Location loc = entityLiving4.getLocation();

        loc.setYaw(entityLiving4.getLocation().getYaw() + 5F);
        for (Player p : viewers) {
            entityLiving4.teleport(loc,p);
        }
        if (!(ticks <= 0)){
            ticks--;
            return;
        }

        ticks=2;
        boolean istrue = false;
        for (Entity entity : entityLiving1.getLocation().getWorld().getNearbyEntities(entityLiving1.getLocation(),8,2,8)){
            if (entity instanceof  Player){
                Player player = (Player) entity;
                if (player.getAllowFlight()) continue;
                istrue=true;
                break;
            }
        }
        if (istrue) {
            if (times <= 0) {
                times=1;
                entityLiving3.setCustomName(TextHelper.text("&e"+times));
                entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(1-times,1,5,'■', ChatColor.GREEN,ChatColor.RED)+"&7]"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        this.cancel();
                        Bukkit.getWorld(entityLiving1.getLocation().getWorld().getUID()).spawn(entityLiving1.getLocation(), EntityType.THROWN_EXP_BOTTLE.getEntityClass()).setVelocity(new Vector(0, 0.2, 0));
                    }
                }.runTask(Main.getInstance());
            } else {
                times--;
                entityLiving3.setCustomName(TextHelper.text("&e"+times));
                entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(1-times,1,5,'■', ChatColor.GREEN,ChatColor.RED)+"&7]"));
            }

        } else {
            entityLiving3.setCustomName(TextHelper.text("&c&lSTOPPED"));
        }
        for (Player player : viewers){
            entityLiving2.sendUpdatedmetadata(player);
            entityLiving3.sendUpdatedmetadata(player);
        }

    }
}
