package me.firas.skypvp.generators.enderchest;

import me.firas.skypvp.Main;
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


public class EnderchestGenerator {

    public HashSet<Player> viewers = new HashSet<>();
    public WrapperEntityArmorStand entityLiving1;
    public boolean firstTime = true;



    public EnderchestGenerator(WrapperEntityArmorStand entity1){
        entityLiving1 = entity1;


    }
    public void tick(){
        if (entityLiving1.isDespawned()){
            EnderchestManager.pool.remove(entityLiving1.getId(), entityLiving1);
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

                viewers.remove(p);
            } else if(inRange && !canSee) {
                entityLiving1.spawnClient(p);
                entityLiving1.sendUpdatedmetadata(p);

                viewers.add(p);
            }
        }
        Location loc = entityLiving1.getLocation();

        loc.setYaw(entityLiving1.getLocation().getYaw() + 3.8F);
        for (Player p : viewers) {
            ParticleEffect.SPELL_WITCH.display(0F,0,0F,0F,2,new Location(loc.getWorld(), loc.getX(),loc.getY() + 2.1,loc.getZ()),p);
            entityLiving1.teleport(loc,p);
        }

    }
}
