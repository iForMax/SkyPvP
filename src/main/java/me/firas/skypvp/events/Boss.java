package me.firas.skypvp.events;

import com.avaje.ebean.validation.NotNull;
import io.netty.channel.local.LocalAddress;
import lombok.With;
import me.firas.skypvp.Main;
import me.firas.skypvp.util.ActionBar;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.ParticleEffect;
import me.firas.skypvp.util.TextHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Boss {
    Location location = Main.getInstance().getLocationHandler().getLocation("giant");

    public int level = 1;
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

    public void fireSummon(Location location, Vector vector){
        new BukkitRunnable() {
            Location locations = location;
            Vector dir = vector.normalize();
            double t = 0;

            @Override
            public void run() {
                t += 0.2;
                double x = dir.getX() * t;
                double y = dir.getY() * t + 1.5;
                double z = dir.getZ() * t;
                locations.add(x,y,z);
                ParticleEffect.LAVA.display(0,0,0,0,1,locations,25);
                for (Entity entity : locations.getWorld().getNearbyEntities(locations,0.5,0.5,0.5)) {
                    if (entity instanceof Player) {
                        if (entity.getFireTicks() > 0) continue;
                        entity.setFireTicks(20 * 10);
                        ((Player) entity).playSound(entity.getLocation(),Sound.FIZZ,1,1);
                    }
                }
                locations.subtract(x,y,z);
                if (t > 40){
                    this.cancel();
                }

            }
        }.runTaskTimer(Main.getInstance(),0,1);
    }
    public void updateLevel(int levels) {
        if (levels == 2) {
            level = 2;
            Main.getInstance().getBoss().giant().setHealth(500);
            for (Entity entity : giant().getNearbyEntities(20, 10, 20)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                }
            }
            int number = 0;

            while (number <= 10) {
                Location location2 = giant().getLocation().add(getRandom(-5, 5), 1, getRandom(-5, 5));
                Zombie zombie = (Zombie) giant().getLocation().getWorld().spawnEntity(location2, EntityType.ZOMBIE);
                zombie.getEquipment().clear();
                zombie.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(100000,0));
                zombie.addPotionEffect(PotionEffectType.SPEED.createEffect(100000,1));
                zombie.setMaxHealth(100);
                zombie.setHealth(100);
                zombie.setCustomNameVisible(true);
                zombie.setBaby(false);
                zombie.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                zombie.getEquipment().setBootsDropChance(0);
                zombie.getEquipment().setChestplateDropChance(0);
                zombie.getEquipment().setLeggingsDropChance(0);
                zombie.getEquipment().setHelmetDropChance(0);
                zombie.getEquipment().setItemInHandDropChance(0);
                number++;
            }
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, -1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, 0).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(-1, 0, 0).normalize());

            new BukkitRunnable() {
                int numbers = 6;
                @Override

                public void run() {
                    if (numbers <= 0) {
                        this.cancel();
                        return;
                    }
                    numbers--;
                    Location location2 = giant().getLocation().add(getRandom(-5, 5), 0, getRandom(-5, 5));
                    location.getWorld().strikeLightning(location2.add((getRandom(-5, 5)), 0, getRandom(-5, 5)));
                }
            }.runTaskTimer(Main.getInstance(),0,15);

        } else if (levels == 3) {
            level = 3;

            Main.getInstance().getBoss().giant().setHealth(1000);
            for (Entity entity : giant().getNearbyEntities(20, 10, 20)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                }
            }
            int number = 0;

            while (number <= 10) {
                Location location2 = giant().getLocation().add(getRandom(-5, 5), 1, getRandom(-5, 5));
                Skeleton zombie = (Skeleton) giant().getLocation().getWorld().spawnEntity(location2, EntityType.SKELETON);
                zombie.getEquipment().clear();
                zombie.setMaxHealth(100);
                zombie.setHealth(100);
                zombie.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(100000,0));
                zombie.setCustomNameVisible(true);
                zombie.getEquipment().setItemInHand(new ItemBuilder(Material.BOW, 1, 0).addEnchant(Enchantment.ARROW_FIRE, 3).build());
                zombie.getEquipment().setBootsDropChance(0);
                zombie.getEquipment().setChestplateDropChance(0);
                zombie.getEquipment().setLeggingsDropChance(0);
                zombie.getEquipment().setHelmetDropChance(0);
                zombie.getEquipment().setItemInHandDropChance(0);

                number++;
            }
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, -1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(-1, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, 0).normalize());
            new BukkitRunnable() {
                int numbers = 8;
                @Override

                public void run() {
                    if (numbers <= 0) {
                        this.cancel();
                        return;
                    }
                    numbers--;
                    Location location2 = giant().getLocation().add(getRandom(-5, 5), 0, getRandom(-5, 5));
                    location.getWorld().strikeLightning(location2.add((getRandom(-5, 5)), 0, getRandom(-5, 5)));
                }
            }.runTaskTimer(Main.getInstance(),0,15);
        } else if (levels == 4) {
            level = 4;

            Main.getInstance().getBoss().giant().setHealth(1500);
            for (Entity entity : giant().getNearbyEntities(20, 10, 20)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                }
            }
            int number = 0;

            while (number <= 10) {
                Location location2 = giant().getLocation().add(getRandom(-5, 5), 1, getRandom(-5, 5));
                Creeper zombie = (Creeper) giant().getLocation().getWorld().spawnEntity(location2, EntityType.CREEPER);
                zombie.getEquipment().clear();
                zombie.setMaxHealth(100);
                zombie.setHealth(100);
                zombie.setCustomNameVisible(true);
                zombie.getEquipment().setBootsDropChance(0);
                zombie.getEquipment().setChestplateDropChance(0);
                zombie.getEquipment().setLeggingsDropChance(0);
                zombie.getEquipment().setHelmetDropChance(0);
                zombie.getEquipment().setItemInHandDropChance(0);
                if (number >= 7) {
                    zombie.setPowered(true);
                }
                number++;
            }
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, -1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(-1, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, -1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(-1, 0, 0).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, 0).normalize());
            new BukkitRunnable() {
                int numbers = 10;
                @Override

                public void run() {
                    if (numbers <= 0) {
                        this.cancel();
                        return;
                    }
                    numbers--;
                    Location location2 = giant().getLocation().add(getRandom(-5, 5), 0, getRandom(-5, 5));
                    location.getWorld().strikeLightning(location2.add((getRandom(-5, 5)), 0, getRandom(-5, 5)));
                }
            }.runTaskTimer(Main.getInstance(),0,15);
        } else if (levels == 5) {
            level = 5;


            Main.getInstance().getBoss().giant().setHealth(2000);
            for (Entity entity : giant().getNearbyEntities(20, 10, 20)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                }
            }
            int number = 0;

            while (number <= 10) {
                Location location2 = giant().getLocation().add(getRandom(-5, 5), 1, getRandom(-5, 5));
                Skeleton zombie = (Skeleton) giant().getLocation().getWorld().spawnEntity(location2, EntityType.SKELETON);
                zombie.getEquipment().clear();
                zombie.setSkeletonType(Skeleton.SkeletonType.WITHER);
                zombie.addPotionEffect(PotionEffectType.SPEED.createEffect(100000,2));
                zombie.setMaxHealth(200);
                zombie.setHealth(200);
                zombie.setCustomNameVisible(true);
                zombie.getEquipment().setBootsDropChance(0);
                zombie.getEquipment().setChestplateDropChance(0);
                zombie.getEquipment().setLeggingsDropChance(0);
                zombie.getEquipment().setHelmetDropChance(0);
                zombie.getEquipment().setItemInHandDropChance(0);
                zombie.getEquipment().setHelmet(new ItemBuilder(Material.IRON_HELMET, 1, 0).build());
                zombie.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE, 1, 0).build());
                zombie.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS, 1, 0).build());
                zombie.getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS, 1, 0).build());
                zombie.getEquipment().setItemInHand(new ItemBuilder(Material.IRON_SWORD, 1, 0).addEnchant(Enchantment.DAMAGE_ALL, 3).build());

                number++;
            }
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, -1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(-1, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, -1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(-1, 0, 0).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(0, 0, 1).normalize());
            fireSummon(giant().getLocation().add(0, 0, 0), new Vector(1, 0, 0).normalize());
            new BukkitRunnable() {
                int numbers = 16;
                @Override

                public void run() {
                    if (numbers <= 0) {
                        this.cancel();
                        return;
                    }
                    numbers--;
                    Location location2 = giant().getLocation().add(getRandom(-5, 5), 0, getRandom(-5, 5));
                    location.getWorld().strikeLightning(location2.add((getRandom(-5, 5)), 0, getRandom(-5, 5)));
                }
            }.runTaskTimer(Main.getInstance(),0,15);
        }
    }


    public Giant giant(){
        for (Entity entity  : location.getWorld().getEntities()){
            if (entity instanceof Giant){
                return (Giant) entity;
            }
        }
        return null;
    }


    public void spawnGiant(){
        Giant giant = (Giant) location.getWorld().spawnEntity(location, EntityType.GIANT);
        giant.setMaxHealth(2000);
        giant.setHealth(100);
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) giant).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
        ArmorStand standLevel = (ArmorStand) location1.getWorld().spawnEntity(location1, EntityType.ARMOR_STAND);
        ArmorStand stand = (ArmorStand) location1.getWorld().spawnEntity(location1.add(0,-0.4D,0), EntityType.ARMOR_STAND);
        standLevel.setBasePlate(false);
        standLevel.setArms(false);
        standLevel.setVisible(false);
        standLevel.setGravity(false);
        standLevel.setCanPickupItems(false);
        standLevel.setSmall(false);
        standLevel.setCustomName(TextHelper.text("&a&lI"));
        standLevel.setCustomNameVisible(true);
        standLevel.setMarker(true);
        stand.setBasePlate(false);
        stand.setArms(false);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setCanPickupItems(false);
        stand.setSmall(false);
        stand.setCustomNameVisible(true);
        stand.setMarker(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (giant.isDead()){
                    this.cancel();
                    stand.remove();
                    standLevel.remove();
                    return;
                }
                stand.setCustomName(TextHelper.text((int) giant.getHealth()/2+"&c❤"));
                if (level == 1){
                    standLevel.setCustomName(TextHelper.text("&a&lI"));
                } else if (level == 2){
                    standLevel.setCustomName(TextHelper.text("&e&lII"));
                } else if (level == 3){
                    standLevel.setCustomName(TextHelper.text("&6&lIII"));
                } else if (level == 4){
                    standLevel.setCustomName(TextHelper.text("&c&lIV"));
                } else if (level == 5){
                    standLevel.setCustomName(TextHelper.text("&4&lLast Level"));
                }

            }
        }.runTaskTimer(Main.getInstance(),0,20);
    }
    Location locationStand = Main.getInstance().getLocationHandler().getLocation("giant-health");
    Location location1 = locationStand;









}
