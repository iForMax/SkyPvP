package me.firas.skypvp.events;

import me.firas.skypvp.Main;
import me.firas.skypvp.generators.koth.KothGenerator;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import me.firas.skypvp.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Event {

    public EventType currentEvent;

    public int minutesEvent;

    public int secondsEvent;

    public EventType nextEvent;

    public EventType previousEvent;

    public void startCooldown(){
        minutesEvent=30;
        secondsEvent=0;
        setCurrentEvent(EventType.Cooldown);
    }
    public void openEvent(Player player){
        Inventory inventory = Bukkit.createInventory(null,27,"Events");
        boolean isstarted;
        if (currentEvent.equals(EventType.KoTH) || nextEvent != null && nextEvent.equals(EventType.KoTH)){
            isstarted=true;
        } else if (currentEvent.equals(EventType.Boss) || nextEvent != null &&  nextEvent.equals(EventType.Boss)){
            isstarted=true;
        } else if (currentEvent.equals(EventType.Emerald)|| nextEvent != null &&  nextEvent.equals(EventType.Emerald)){
            isstarted=true;
        } else {
            isstarted=false;
        }
        if (isstarted){
            inventory.setItem(13,new ItemBuilder(Material.BARRIER,1,0).setDisplayname("&c&lForce Stop").build());
        } else {
            inventory.setItem(12,new ItemBuilder(Material.EMERALD_BLOCK,1,0).setDisplayname("&a&lEmerald Event").build());
            inventory.setItem(13,new ItemBuilder(Material.CHEST,1,0).setDisplayname("&d&lKoTH Event").build());
            inventory.setItem(14,new ItemBuilder(Material.DRAGON_EGG,1,0).setDisplayname("&5&lBoss Event").build());
        }
        player.openInventory(inventory);
    }

    private boolean emeraldEvent = false;
    public boolean isEmeraldEvent(){
        return emeraldEvent;
    }
    public void setEmeraldEvent(boolean b){
        emeraldEvent = b;
    }
    private boolean kothEvent = false;
    public boolean isKothEvent(){
        return kothEvent;
    }
    private String KothEventType = null;

    public void setKothEventType(String type){
        KothEventType = type;
    }
    public String getKothEventType(){
        if (KothEventType == null){
            return "";
        }
        return KothEventType;
    }
    private boolean bossEvent = false;
    public boolean isBossEvent(){
        return bossEvent;
    }
    public void setBossEvent(boolean b){
        bossEvent = b;
    }
    public void setKothEvent(boolean b){
        kothEvent = b;
    }

    public EventType getNextEvent(){
        return nextEvent;
    }

    public void setNextEvent(EventType event){
        nextEvent = event;
    }

    public String getEvent(){
        if (getCurrentEvent() != null){
            if (getCurrentEvent().equals(EventType.Boss)){
                return "Event: Boss";
            } else if (getCurrentEvent().equals(EventType.Emerald)){
                return "Event: Emerald";
            } else if (getCurrentEvent().equals(EventType.KoTH)){
                return "Event: KoTH";
            }
        }
        if (getNextEvent() != null && getNextEvent().equals(EventType.Boss)){
            return "Next: Boss";
        } else if (getNextEvent() != null && getNextEvent().equals(EventType.Emerald)){
            return "Next: Emerald";
        } else if (getNextEvent() != null && getNextEvent().equals(EventType.KoTH)){
            return "Next: KoTH";
        }
        if (currentEvent.equals(EventType.Cooldown)){
            return "Next Event";
        } else {
            return "Nothing (:";
        }
    }
    public void setTimeEvent(int minutes,int seconds){
        minutesEvent = minutes;
        secondsEvent = seconds;
    }
    public void setEvent(EventType type){
        if (type.equals(EventType.KoTH)){
            previousEvent = getCurrentEvent();
            setNextEvent(EventType.KoTH);
            setTimeEvent(0,1);
        }
        if (type.equals(EventType.Emerald)){
            previousEvent = getCurrentEvent();
            setNextEvent(EventType.Emerald);
            setTimeEvent(0,1);
        }
        if (type.equals(EventType.Boss)){
            previousEvent = getCurrentEvent();
            setNextEvent(EventType.Boss);
            setTimeEvent(0,1);
        } else {

        }
    }
    public EventType getCurrentEvent(){
        return currentEvent;
    }

    public void startTime(){
        new BukkitRunnable(){
            @Override
            public void run() {

                if (secondsEvent == 0 && minutesEvent > 0){
                    secondsEvent = 59;
                    minutesEvent--;
                    return;
                }
                if (secondsEvent == 0 && minutesEvent == 0){
                    if (getCurrentEvent().equals(EventType.Cooldown)){
                        if (previousEvent == null){
                            setNextEvent(EventType.Emerald);
                            setCurrentEvent(EventType.Next);
                            minutesEvent=0;
                            secondsEvent=11;
                            return;
                        } else if (previousEvent.equals(EventType.Emerald)){
                            setNextEvent(EventType.KoTH);
                            setCurrentEvent(EventType.Next);
                            minutesEvent=0;
                            secondsEvent=11;
                            return;
                        } else if (previousEvent.equals(EventType.KoTH)){
                            setNextEvent(EventType.Emerald);
                            setCurrentEvent(EventType.Next);
                            minutesEvent=0;
                            secondsEvent=11;
                            return;
                        } else if (previousEvent.equals(EventType.Boss)){
                            setNextEvent(EventType.Emerald);
                            setCurrentEvent(EventType.Next);
                            previousEvent=null;
                            minutesEvent=0;
                            secondsEvent=11;
                            return;
                        }


                    }
                    if (getCurrentEvent().equals(EventType.Boss) || getCurrentEvent().equals(EventType.Emerald) || getCurrentEvent().equals(EventType.KoTH)) {
                        if (getCurrentEvent().equals(EventType.Emerald)){
                            setEmeraldEvent(false);
                        }
                        if (getCurrentEvent().equals(EventType.Boss)){
                            setBossEvent(false);
                            for (Entity entity  :Bukkit.getWorld("world").getEntities()){
                                if (entity instanceof Giant){
                                    for (Entity entity1 : entity.getNearbyEntities(10,10,10)){
                                        if (entity1 instanceof ArmorStand){
                                            entity1.remove();
                                        }
                                    }
                                    entity.remove();
                                }
                            }
                        }
                        if (getCurrentEvent().equals(EventType.KoTH)){
                            setKothEvent(false);
                            previousEvent=null;
                            setKothEventType(null);
                            nextEvent=null;
                        }
                        previousEvent = getCurrentEvent();
                        startCooldown();
                        return;

                    }
                    if (getNextEvent().equals(EventType.Boss)){
                        for (Player player : Bukkit.getOnlinePlayers()){
                            new Title("&c&lEvent","&7Island &cboss &7is now opened",10,20,1).send(player);
                            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL,0.5F,2);
                        }
                        minutesEvent=30;
                        secondsEvent=0;
                        setCurrentEvent(EventType.Boss);
                        setNextEvent(null);
                        Main.getInstance().getBoss().spawnGiant();
                        setBossEvent(true);
                        return;
                    }
                    if (getNextEvent().equals(EventType.Emerald)){
                        for (Player player : Bukkit.getOnlinePlayers()){
                            new Title("&c&lEvent","&aEmerald Generator &7is now opened",10,20,1).send(player);
                            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL,0.5F,2);
                        }
                        minutesEvent=15;
                        secondsEvent=0;
                        setCurrentEvent(EventType.Emerald);
                        setEmeraldEvent(true);
                        setNextEvent(null);
                        return;
                    }
                    if (getNextEvent().equals(EventType.KoTH)){

                        minutesEvent=10;
                        secondsEvent=0;
                        setCurrentEvent(EventType.KoTH);
                        int ran = new Random().nextInt(3)+1;
                        if (ran == 1){
                            setKothEventType("Pyramid");
                        } else if (ran == 2){
                            setKothEventType("Aliens");
                        } else {
                            setKothEventType("Arena");
                        }
                        Bukkit.broadcastMessage(TextHelper.format("&d&lKoTH "+getKothEventType() +" &7is now opened"));
                        setKothEvent(true);
                        KothGenerator.firstTime = true;
                        setNextEvent(null);
                        return;
                    }


                    return;
                }
                secondsEvent--;
            }
        }.runTaskTimer(Main.getInstance(),0,20L);
    }

    public String getTime(){
        if (minutesEvent > 9){
            if (secondsEvent > 9){
                return minutesEvent+":"+secondsEvent;
            } else {
                return minutesEvent+":0"+secondsEvent;
            }
        } else {
            if (secondsEvent > 9){
                return "0"+minutesEvent+":"+secondsEvent;
            } else {
                return "0"+minutesEvent+":0"+secondsEvent;
            }
        }
    }

    public void setCurrentEvent(EventType event){
        currentEvent = event;
    }



}
