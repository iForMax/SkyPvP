package me.firas.skypvp.generators.goldGen;

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

import static me.firas.skypvp.Main.getProgressBar;


public class GoldManager {
    public static ConcurrentHashMap<Integer, GoldGenerator> pool = new ConcurrentHashMap<>();
    public static void onStartUp(){
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of Gold"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("gold-"+i);
            if (location == null) continue;
            WrapperEntityArmorStand entityLiving = new WrapperEntityArmorStand();
            entityLiving.setCustomName(TextHelper.text("&8◆ &6&lGold Generator &8◆"));
            entityLiving.setCustomNameVisible(true);
            entityLiving.setInvisible(true);
            entityLiving.setMarker(true);
            entityLiving.updateMetadata();
            WrapperEntityArmorStand entityLiving2 = new WrapperEntityArmorStand();
            entityLiving2.setCustomName(TextHelper.text("&7[" + getProgressBar(0, 60, 5, '■', ChatColor.GREEN, ChatColor.RED) + "&7]"));
            entityLiving2.setCustomNameVisible(true);
            entityLiving2.setInvisible(true);
            entityLiving2.setMarker(true);
            entityLiving2.updateMetadata();
            WrapperEntityArmorStand entityLiving3 = new WrapperEntityArmorStand();
            entityLiving3.setCustomName(TextHelper.text("&e" + TextHelper.text("30")));
            entityLiving3.setCustomNameVisible(true);
            entityLiving3.setInvisible(true);
            entityLiving3.setMarker(true);
            entityLiving3.updateMetadata();
            WrapperEntityArmorStand entityLiving4 = new WrapperEntityArmorStand();
            entityLiving4.equip(EnumWrappers.ItemSlot.HEAD,new ItemStack(Material.GOLD_BLOCK));
            entityLiving4.setCustomNameVisible(false);
            entityLiving4.setInvisible(true);
            entityLiving4.setMarker(true);
            entityLiving4.updateMetadata();
            entityLiving.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()),location.getZ()));
            entityLiving2.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.3D,location.getZ()));
            entityLiving3.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.6D,location.getZ()));
            entityLiving4.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.7D,location.getZ()));

            GoldGenerator hologram = new GoldGenerator(entityLiving, entityLiving2, entityLiving3,entityLiving4);
            pool.put(entityLiving.getId(), hologram);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, GoldGenerator> entry : pool.entrySet()) {
                    try {
                        GoldGenerator hologram = entry.getValue();
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
