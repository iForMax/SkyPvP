package me.firas.skypvp.generators.koth;

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
import static me.firas.skypvp.generators.help.getHead;


public class KothManager {
    public static ConcurrentHashMap<Integer, KothGenerator> pool = new ConcurrentHashMap<>();
    public static void onStartUp(){
        for (int i = 1; i <= 3; i++) {
            Location loc;
            String type;
            if (i == 1){
                loc = Main.getInstance().getGeneratorsHandler().getLocation("koth-arena");
                type = "Arena";
            } else if (i == 2){
                loc = Main.getInstance().getGeneratorsHandler().getLocation("koth-pyramid");
                type = "Pyramid";

            } else {
                loc = Main.getInstance().getGeneratorsHandler().getLocation("koth-aliens");
                type = "Aliens";
            }
            Location location = loc;
            if (location == null) continue;
            WrapperEntityArmorStand entityLiving = new WrapperEntityArmorStand();
            entityLiving.setCustomName(TextHelper.text("&d&lKoTH "+type));
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
            entityLiving3.setCustomName(TextHelper.text("&e0%"));
            entityLiving3.setCustomNameVisible(true);
            entityLiving3.setInvisible(true);
            entityLiving3.setMarker(true);
            entityLiving3.updateMetadata();
            WrapperEntityArmorStand entityLiving4 = new WrapperEntityArmorStand();
            entityLiving4.equip(EnumWrappers.ItemSlot.HEAD,getHead("questionmark"));
            entityLiving4.setCustomNameVisible(false);
            entityLiving4.setArms(true);
            entityLiving4.setInvisible(true);
            entityLiving4.setMarker(true);
            entityLiving4.updateMetadata();
            WrapperEntityArmorStand entityLiving5 = new WrapperEntityArmorStand();
            entityLiving5.setCustomName(TextHelper.text("&bNo One"));
            entityLiving5.setCustomNameVisible(true);
            entityLiving5.setInvisible(true);
            entityLiving5.setMarker(true);
            entityLiving5.updateMetadata();
            entityLiving.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()),location.getZ()));
            entityLiving2.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.6D,location.getZ()));
            entityLiving3.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.9D,location.getZ()));
            entityLiving4.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.7D,location.getZ()));
            entityLiving5.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()) - 0.3D,location.getZ()));

            KothGenerator hologram = new KothGenerator(entityLiving, entityLiving2, entityLiving3,entityLiving4,entityLiving5);
            pool.put(entityLiving.getId(), hologram);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, KothGenerator> entry : pool.entrySet()) {
                    try {
                        KothGenerator hologram = entry.getValue();
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
