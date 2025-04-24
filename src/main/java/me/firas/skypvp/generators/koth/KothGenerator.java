package me.firas.skypvp.generators.koth;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.firas.skypvp.Main;
import me.firas.skypvp.nms.wrapper.living.WrapperEntityArmorStand;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static me.firas.skypvp.Main.getProgressBar;
import static me.firas.skypvp.generators.help.getHead;

public class KothGenerator {

    public HashSet<Player> viewers = new HashSet<>();
    public WrapperEntityArmorStand entityLiving1;
    public WrapperEntityArmorStand entityLiving2;
    public WrapperEntityArmorStand entityLiving3;
    public WrapperEntityArmorStand entityLiving4;
    public WrapperEntityArmorStand kothPlayer;
    boolean cooldown=false;

    int cminutes;
    int cseconds;
    public int times = 60;
    public int ticks = 20;
    public Player name;

    public static boolean firstTime;

    private String getTime(int minutes,int seconds){
        if (minutes > 9){
            if (seconds > 9){
                return minutes+":"+seconds;
            } else {
                return minutes+":0"+seconds;
            }
        } else {
            if (seconds > 9){
                return "0"+minutes+":"+seconds;
            } else {
                return "0"+minutes+":0"+seconds;
            }
        }
    }
    private void SummonFirework(Location location){
        final Firework f = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();

        fm.addEffect(FireworkEffect.builder()
                .flicker(true)
                .trail(true)
                .with(FireworkEffect.Type.STAR)
                .with(FireworkEffect.Type.BALL)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.AQUA)
                .withColor(Color.YELLOW)
                .withColor(Color.RED)
                .withColor(Color.WHITE)
                .build());

        fm.setPower(0);
        f.setFireworkMeta(fm);

    }

    public KothGenerator(WrapperEntityArmorStand entity1, WrapperEntityArmorStand entity2, WrapperEntityArmorStand entity3, WrapperEntityArmorStand entity4,WrapperEntityArmorStand entity5){
        entityLiving1 = entity1;
        entityLiving2 = entity2;
        entityLiving3 = entity3;
        entityLiving4 = entity4;
        kothPlayer = entity5;
    }
    public void reset(boolean isequip){
        kothPlayer.setCustomName(ChatColor.AQUA+"No One");
        kothPlayer.setCustomNameVisible(true);
        entityLiving3.setCustomName(TextHelper.text("&e0%"));
        entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(0,60,5,'■',ChatColor.GREEN,ChatColor.RED)+"&7]"));
        if (isequip) {
            for (Player player : viewers) {
                entityLiving2.sendUpdatedmetadata(player);
                entityLiving3.sendUpdatedmetadata(player);
                kothPlayer.sendUpdatedmetadata(player);
            }
        }
        else {
            for (Player player : viewers) {
                entityLiving4.equip(EnumWrappers.ItemSlot.HEAD, getHead("questionmark"), player);
                entityLiving2.sendUpdatedmetadata(player);
                entityLiving3.sendUpdatedmetadata(player);
                kothPlayer.sendUpdatedmetadata(player);
            }
        }
    }
    public void tick(){
        if (entityLiving1.isDespawned()){
            KothManager.pool.remove(entityLiving1.getId(), entityLiving1);
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
                kothPlayer.despawn(p);
                viewers.remove(p);
            } else if(inRange && !canSee) {
                entityLiving1.spawnClient(p);
                entityLiving2.spawnClient(p);
                entityLiving3.spawnClient(p);
                entityLiving4.spawnClient(p);
                kothPlayer.spawnClient(p);

                entityLiving1.sendUpdatedmetadata(p);
                entityLiving2.sendUpdatedmetadata(p);
                entityLiving3.sendUpdatedmetadata(p);
                entityLiving4.sendUpdatedmetadata(p);
                kothPlayer.sendUpdatedmetadata(p);

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


        ticks=20;
        for (Player player : viewers){
            kothPlayer.sendUpdatedmetadata(player);
            entityLiving2.sendUpdatedmetadata(player);
            entityLiving3.sendUpdatedmetadata(player);
        }
        if (!Main.getInstance().getEvents().isKothEvent() | !entityLiving1.getCustomName().equalsIgnoreCase(TextHelper.text("&d&lKoTH " + Main.getInstance().getEvents().getKothEventType()))){
            if (firstTime) {
                reset(true);
                firstTime = false;
            }
            if (kothPlayer.getCustomName().equalsIgnoreCase(ChatColor.AQUA+"No One")){
                entityLiving3.setCustomName(TextHelper.text("&c&lCLOSED"));
                for (Player player : viewers){
                    entityLiving4.equip(EnumWrappers.ItemSlot.HEAD,getHead("reversemark"),player);
                    entityLiving3.sendUpdatedmetadata(player);
                }
            } else {
                reset(true);
            }

            return;
        }

        if (cooldown) {
            if (cseconds != 0) {
                cseconds--;
                entityLiving3.setCustomName(ChatColor.RED + getTime(cminutes, cseconds));
                entityLiving2.setCustomName(TextHelper.text("&7Cooldown"));
                kothPlayer.setCustomNameVisible(false);
                for (Player player : viewers){
                    entityLiving2.sendUpdatedmetadata(player);
                    entityLiving3.sendUpdatedmetadata(player);
                    kothPlayer.sendUpdatedmetadata(player);
                }
            }
            if (cminutes <= 0 && cseconds <= 0) {
                cooldown = false;
                Bukkit.broadcastMessage(TextHelper.format("&d&lKoTH &7is no longer in cooldown!"));
                reset(false);
                return;
            }
            if (cminutes != 0 && cseconds == 0) {
                cminutes--;
                cseconds = 59;
                entityLiving3.setCustomName(ChatColor.RED + getTime(cminutes, cseconds));
                entityLiving2.setCustomName(TextHelper.text("&7Cooldown"));
                kothPlayer.setCustomNameVisible(false);
                for (Player player : viewers){
                    entityLiving2.sendUpdatedmetadata(player);
                    entityLiving3.sendUpdatedmetadata(player);
                    kothPlayer.sendUpdatedmetadata(player);
                }
                return;
            }
            return;
        }
        boolean gen = false;
        boolean reset = false;
        int numb = 0;
        for (Player player : viewers){
            kothPlayer.sendUpdatedmetadata(player);
        }
        for (Entity player : entityLiving1.getLocation().getWorld().getNearbyEntities(entityLiving1.getLocation(),2,2,2)){
            if (player instanceof Player) {
                if (((Player) player).getAllowFlight()){
                    continue;
                }
                numb++;
                if (!gen) {
                    gen = true;
                }
                if (times == 60 && name == null){
                    name = (Player) player;
                    kothPlayer.setCustomName(TextHelper.text("&e"+name.getDisplayName()));
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwner(name.getName());
                    skull.setItemMeta(skullMeta);
                    for (Player players : viewers)
                        entityLiving4.equip(EnumWrappers.ItemSlot.HEAD,skull,players);
                    Bukkit.broadcastMessage(TextHelper.format("&9"+name.getDisplayName()+" &7is now capturing the "+entityLiving1.getCustomName()));
                }
                if (name == player){
                    name = (Player) player;
                    reset=false;
                    break;
                }
                reset=true;
            }
        }

        if (reset || numb == 0){
            times=60;
            name=null;
            reset(false);
            return;
        }
        if (gen) {
            times--;
            if (times == 0) {
                entityLiving3.setCustomName(TextHelper.text("&e100%"));
                entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(60-times,60,5,'■',ChatColor.GREEN,ChatColor.RED)+"&7]"));
                for (Player player : viewers){
                    kothPlayer.sendUpdatedmetadata(player);
                    entityLiving2.sendUpdatedmetadata(player);
                    entityLiving3.sendUpdatedmetadata(player);
                }
                Random rand = new Random();
                Location location = name.getLocation();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        this.cancel();
                        SummonFirework(location);
                    }
                }.runTask(Main.getInstance());
                int r = new Random().nextInt(48)+1;
                String found;
                if (r <= 3){
                    name.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(1));
                    found = "&7Common Package";
                }
                else if (r <= 5){
                    name.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(2));
                    found = "&bRare Package";
                }
                else if (r <= 6){
                    name.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(3));
                    found = "&5Epic Package";
                }
                else if (r <= 7){
                    name.getInventory().addItem(new ItemStack(Material.EMERALD,10));
                    found = "&a10 Emerald";
                }
                else if (r <= 12){
                    ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                    enchantmentStorageMeta.addStoredEnchant(Enchantment.DAMAGE_ALL, 3, true);
                    itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemStack.setItemMeta(itemMeta);
                    name.getInventory().addItem(itemStack);
                    found = "&7Sharpness III";
                }
                else if (r <= 17){
                    ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                    enchantmentStorageMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                    itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemStack.setItemMeta(itemMeta);
                    name.getInventory().addItem(itemStack);
                    found = "&7Protection III";
                }
                else if (r <= 19){
                    ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                    enchantmentStorageMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemStack.setItemMeta(itemMeta);
                    name.getInventory().addItem(itemStack);
                    found = "&7Protection IV";
                }
                else if (r <= 24){
                    name.getInventory().addItem(new ItemStack(Material.IRON_INGOT,64));
                    found = "&764 Iron";
                }
                else if (r <= 27){
                    name.getInventory().addItem(new ItemStack(Material.GOLD_INGOT,32));
                    found = "&632 Gold";
                }
                else if (r <= 30){
                    ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                    enchantmentStorageMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                    itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemStack.setItemMeta(itemMeta);
                    name.getInventory().addItem(itemStack);
                    found = "&7Fire Aspect I";
                }
                else if (r <= 35){
                    Main.getInstance().get(name).addStats("points",25);
                    found = "&a25 Points";
                }
                else if (r <= 38){
                    Main.getInstance().get(name).addStats("points",35);
                    found = "&a35 Points";
                }
                else if (r <= 40){
                    Main.getInstance().get(name).addStats("points",40);
                    found = "&a40 Points";
                }
                else if (r <= 45){
                    Main.getInstance().get(name).addStats("coins",50);
                    found = "&650 Coins";
                }
                else {
                    Main.getInstance().get(name).addStats("coins",100);
                    found = "&6100 Coins";
                }
                name.sendMessage(TextHelper.format("&aYou found "+found));
                for (Player player : viewers)
                    entityLiving4.equip(EnumWrappers.ItemSlot.HEAD,getHead("cooldown"),player);

                name=null;
                times = 60;
                cooldown=true;
                cminutes=2;
                cseconds=0;
                return;
            }
            int percent = (60-times) * 100 / 60;
            double b = Math.round(percent * 10.0) / 10.0;
            int s = (int)b;
            entityLiving3.setCustomName(TextHelper.text("&e"+getColored(s)+"%"));
            entityLiving2.setCustomName(TextHelper.text("&7["+getProgressBar(60-times,60,5,'■',ChatColor.GREEN,ChatColor.RED)+"&7]"));

        }
        for (Player player : viewers){
            entityLiving2.sendUpdatedmetadata(player);
            entityLiving3.sendUpdatedmetadata(player);
        }

    }
    public String getColored(int n){
        if (n < 20){
            return "&e"+n;
        } else if (n < 40){
            return "&a"+n;
        } else if (n < 60){
            return "&6"+n;
        } else if (n < 80){
            return "&c"+n;
        } else {
            return "&4"+n;
        }
    }
}
