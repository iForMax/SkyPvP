package me.firas.skypvp.generators.menu;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.firas.skypvp.Main;
import me.firas.skypvp.nms.wrapper.living.WrapperEntityArmorStand;
import me.firas.skypvp.util.TextHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static me.firas.skypvp.generators.help.getHead;


public class MenuManager {

    public static ConcurrentHashMap<Integer, MenuGenerator> pool = new ConcurrentHashMap<>();
    public static HashSet<Integer> list = new HashSet<>();

    public static boolean isEnabled = false;
    public static void reloadStartUp(){

        isEnabled=false;
        for (int in : list){
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(in);
            for (Player player : Bukkit.getOnlinePlayers()){
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            }
        }
        pool.clear();
        list.clear();
        onStartUp();
    }
    public static void onStartUp(){
        if (isEnabled){
            return;
        }
        isEnabled=true;
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of Menu"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("menu-"+i);
            if (location == null) continue;
            WrapperEntityArmorStand entityLiving = new WrapperEntityArmorStand();
            entityLiving.setCustomName(TextHelper.text("&bMenu"));
            entityLiving.setCustomNameVisible(true);
            entityLiving.setInvisible(true);
            entityLiving.setMarker(false);
            entityLiving.updateMetadata();
            entityLiving.setLocation(new Location(location.getWorld(),location.getX(),(location.getY())+0.3,location.getZ()));
            WrapperEntityArmorStand entityLiving2 = new WrapperEntityArmorStand();
            entityLiving2.equip(EnumWrappers.ItemSlot.HEAD,getHead("menu"));
            entityLiving2.setCustomNameVisible(false);
            entityLiving2.setInvisible(true);
            entityLiving2.setMarker(false);
            entityLiving2.updateMetadata();
            entityLiving2.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()+0.2),location.getZ()));
            WrapperEntityArmorStand entityLiving3  = new WrapperEntityArmorStand();
            entityLiving3.setCustomName(TextHelper.text("&eRight-Click"));
            entityLiving3.setCustomNameVisible(true);
            entityLiving3.setInvisible(true);
            entityLiving3.setMarker(false);
            entityLiving3.updateMetadata();
            entityLiving3.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()),location.getZ()));
            WrapperEntityArmorStand entityLiving4  = new WrapperEntityArmorStand();
            entityLiving4.setCustomNameVisible(false);
            entityLiving4.setInvisible(true);
            entityLiving4.setMarker(false);
            entityLiving4.updateMetadata();
            entityLiving4.setLocation(new Location(location.getWorld(),location.getX(),(location.getY()+1),location.getZ()));
            MenuGenerator hologram = new MenuGenerator(entityLiving,entityLiving2,entityLiving3,entityLiving4);
            pool.put(entityLiving.getId(), hologram);
            list.add(entityLiving.getId());
            list.add(entityLiving2.getId());
            list.add(entityLiving3.getId());
            list.add(entityLiving4.getId());
        }
        if (isStarted){
            return;
        }
        start();

    }
    private static boolean isStarted = false;
    public static void start(){
        if (isStarted){
            return;
        }
        isStarted = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, MenuGenerator> entry : pool.entrySet()) {
                    try {
                        MenuGenerator hologram = entry.getValue();
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
