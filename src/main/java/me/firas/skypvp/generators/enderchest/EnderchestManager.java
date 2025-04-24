package me.firas.skypvp.generators.enderchest;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.firas.skypvp.Main;
import me.firas.skypvp.nms.wrapper.living.WrapperEntityArmorStand;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class EnderchestManager {
    public static ConcurrentHashMap<Integer, EnderchestGenerator> pool = new ConcurrentHashMap<>();
    public static void onStartUp(){
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of EnderChest"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("enderchest-"+i);
            if (location == null) continue;
            WrapperEntityArmorStand entityLiving = new WrapperEntityArmorStand();
            entityLiving.setCustomName(TextHelper.text("&7Ender Chest"));
            entityLiving.equip(EnumWrappers.ItemSlot.HEAD,new ItemStack(Material.ENDER_CHEST));
            entityLiving.setCustomNameVisible(true);
            entityLiving.setInvisible(true);
            entityLiving.setMarker(false);
            entityLiving.updateMetadata();
            entityLiving.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()),location.getZ()));
            EnderchestGenerator hologram = new EnderchestGenerator(entityLiving);
            pool.put(entityLiving.getId(), hologram);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, EnderchestGenerator> entry : pool.entrySet()) {
                    try {
                        EnderchestGenerator hologram = entry.getValue();
                        hologram.tick();
                        pool.put(hologram.entityLiving1.getId(), hologram);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 1);
    }
}
