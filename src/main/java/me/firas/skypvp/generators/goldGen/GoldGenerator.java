package me.firas.skypvp.generators.goldGen;

import me.firas.skypvp.Main;
import me.firas.skypvp.generators.ironGen.IronManager;
import me.firas.skypvp.nms.wrapper.living.WrapperEntityArmorStand;
import me.firas.skypvp.util.ParticleEffect;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;

import static me.firas.skypvp.Main.getProgressBar;

public class GoldGenerator {

    public HashSet<Player> viewers = new HashSet<>();
    public WrapperEntityArmorStand entityLiving1;
    public WrapperEntityArmorStand entityLiving2;
    public WrapperEntityArmorStand entityLiving3;
    public WrapperEntityArmorStand entityLiving4;

    public int times = 15;
    public int ticks = 20;

    public boolean firstTime = true;



    public GoldGenerator(WrapperEntityArmorStand entity1, WrapperEntityArmorStand entity2, WrapperEntityArmorStand entity3, WrapperEntityArmorStand entity4){
        entityLiving1 = entity1;
        entityLiving2 = entity2;
        entityLiving3 = entity3;
        entityLiving4 = entity4;

    }
    public void tick(){
        if (entityLiving1.isDespawned()){
            GoldManager.pool.remove(entityLiving1.getId(), entityLiving1);
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

        loc.setYaw(entityLiving4.getLocation().getYaw() + 3.8F);
        for (Player p : viewers) {
            entityLiving4.teleport(loc,p);
        }
        if (!(ticks <= 0)){
            ticks--;
            return;
        }

        ticks=20;
        boolean istrue = false;
        for (Entity entity : entityLiving1.getLocation().getWorld().getNearbyEntities(entityLiving1.getLocation(),8,3,8)){
            if (entity instanceof  Player){
                Player player = (Player) entity;
                if (player.getAllowFlight()) continue;
                istrue=true;
                break;
            }
        }
        for (Entity entity : entityLiving3.getLocation().getWorld().getNearbyEntities(entityLiving3.getLocation(),0,2,0)){
            if (entity instanceof Item){
                ItemStack itemStack = ((Item) entity).getItemStack();
                if (itemStack != null && itemStack.getType().equals(Material.GOLD_INGOT)){
                    if (itemStack.getAmount() >= 10){
                        Location location2 = entity.getLocation();
                        entityLiving3.setCustomName(TextHelper.text("&c✖ &lFULL &c✖"));
                        ParticleEffect.FLAME.display(0F,0,0F,0.1F,20,location2.add(0,0.8,0),11);
                        for (Player player : viewers){
                            entityLiving2.sendUpdatedmetadata(player);
                            entityLiving3.sendUpdatedmetadata(player);
                        }
                        return;
                    }
                }
            }
        }
        if (istrue) {
            if (times <= 0) {
                times=15;
                entityLiving3.setCustomName(TextHelper.text("&e"+times));
                entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(15-times,15,5,'■', ChatColor.GREEN,ChatColor.RED)+"&7]"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        this.cancel();

                        Bukkit.getWorld(entityLiving3.getLocation().getWorld().getUID()).dropItem(entityLiving3.getLocation(), new ItemStack(Material.GOLD_INGOT)).setVelocity(new Vector(0, 0.2, 0));
                        Bukkit.getWorld(entityLiving3.getLocation().getWorld().getUID()).playSound(entityLiving3.getLocation(), Sound.ITEM_PICKUP,1,1);
                        ParticleEffect.FIREWORKS_SPARK.display(0.05F,0,0.05F,0.05F,25,entityLiving3.getLocation(),11);
                    }
                }.runTask(Main.getInstance());
            } else {
                times--;
                entityLiving3.setCustomName(TextHelper.text("&e"+times));
                entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(15-times,15,5,'■', ChatColor.GREEN,ChatColor.RED)+"&7]"));
            }

        } else {
            entityLiving3.setCustomName(TextHelper.text("&c&lSTOPPED"));
            ParticleEffect.SMOKE_LARGE.display(0F,0,0F,0.05F,10,entityLiving3.getLocation(),11);
        }
        for (Player player : viewers){
            entityLiving2.sendUpdatedmetadata(player);
            entityLiving3.sendUpdatedmetadata(player);
        }

    }
}
