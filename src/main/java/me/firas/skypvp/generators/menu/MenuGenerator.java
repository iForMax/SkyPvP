package me.firas.skypvp.generators.menu;

import me.firas.skypvp.nms.wrapper.living.WrapperEntityArmorStand;
import me.firas.skypvp.util.ParticleEffect;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class MenuGenerator {

    public HashSet<Player> viewers = new HashSet<>();
    public WrapperEntityArmorStand entityLiving1;
    public WrapperEntityArmorStand entityLiving2;
    public WrapperEntityArmorStand entityLiving3;
    public WrapperEntityArmorStand entityLiving4;
    Location location;



    public boolean firstTime = true;

    String menu = "&bMenu";
    int ticks =40;
    int number = 40;
    boolean animation = false;
    int currentTick = 0; //counter
    int maxTick = 20; //cycle time (fall and rise)
    double length = 0.2; // 1/2 of Y cycle
    double a = 2 * length / Math.pow(maxTick/2, 2); //Calculation of uniform acceleration
    boolean isFall = true;
    int degree = 0;
    int deAgree = 0;

    public MenuGenerator(WrapperEntityArmorStand entity1, WrapperEntityArmorStand entity2,WrapperEntityArmorStand entity3,WrapperEntityArmorStand entity4){
        entityLiving1 = entity1;
        entityLiving2 = entity2;
        entityLiving3 = entity3;
        entityLiving4 = entity4;


    }
    public void tick(){
        if (entityLiving1.isDespawned()){
            MenuManager.pool.remove(entityLiving1.getId(), entityLiving1);
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
                entityLiving1.sendUpdatedmetadata(p);
                entityLiving2.spawnClient(p);
                entityLiving2.sendUpdatedmetadata(p);
                entityLiving3.spawnClient(p);
                entityLiving3.sendUpdatedmetadata(p);
                entityLiving4.spawnClient(p);
                entityLiving4.sendUpdatedmetadata(p);
                viewers.add(p);
            }
        }
        if (animation){
            if (number == 40){
                entityLiving1.setCustomName(TextHelper.text("&fM&benu"));
            } if (number == 37){
                entityLiving1.setCustomName(TextHelper.text("&fMe&bnu"));
            } if (number == 33){
                entityLiving1.setCustomName(TextHelper.text("&bM&fen&bu"));
            } if (number == 29){
                entityLiving1.setCustomName(TextHelper.text("&bMe&fnu"));
            } if (number == 25){
                entityLiving1.setCustomName(TextHelper.text("&bMen&fu"));
            } if (number == 21){
                entityLiving1.setCustomName(TextHelper.text("&bMenu"));
            } if (number == 17){
                entityLiving1.setCustomName(TextHelper.text("&fMenu"));
            } if (number == 13){
                entityLiving1.setCustomName(TextHelper.text("&bMenu"));
            }if (number == 9){
                entityLiving1.setCustomName(TextHelper.text("&fMenu"));
            }if (number == 5){
                entityLiving1.setCustomName(TextHelper.text("&bMenu"));
            }if (number == 1){
                entityLiving1.setCustomName(TextHelper.text("&fMenu"));
            }if (number <= -3) {
                number=41;
                animation=false;
                ticks=40;
                entityLiving1.setCustomName(TextHelper.text(menu));
            }
            number--;
            for (Player player : viewers){
                entityLiving1.sendUpdatedmetadata(player);
            }

        } else if (ticks <= 0){
            animation=true;
        } else {
            ticks--;
        }
        currentTick++;
        int tickValue = currentTick < maxTick/2 ? currentTick : maxTick - currentTick;
        double currentA = a * tickValue;
        location = new Location(entityLiving2.getLocation().getWorld(), entityLiving2.getLocation().getX(), (isFall ? entityLiving2.getLocation().getY() - currentA : entityLiving2.getLocation().getY() + currentA), entityLiving2.getLocation().getZ(), (float) (entityLiving2.getLocation().getYaw() + 5D), 0); //Move setter
        entityLiving2.setLocation(location);
        for (Player player : viewers){
            entityLiving2.fakeTeleport(location, player); //Move setter
        }
        Location location2 = new Location(entityLiving2.getLocation().getWorld(),entityLiving2.getLocation().getX(),entityLiving2.getLocation().getY(),entityLiving2.getLocation().getZ());
        if (deAgree < 360) {
            deAgree = deAgree + 20;
            location2.add(0,1.4,0);
            double radian1 = Math.toRadians(deAgree);
            location2.add(Math.cos(radian1)*0.6, 0, Math.sin(radian1)*0.6);
        } else {
            deAgree = 20;
            location2.add(0,1.4,0);
            double radian1 = Math.toRadians(deAgree);
            location2.add(Math.cos(radian1)*0.6, 0, Math.sin(radian1)*0.6);
        }
        if (degree < 360) {
            degree = degree + 10;
            Location location = new Location(entityLiving2.getLocation().getWorld(),entityLiving2.getLocation().getX(),entityLiving2.getLocation().getY(),entityLiving2.getLocation().getZ());
            location.add(0,1.4,0);
            double radian1 = Math.toRadians(degree);
            location.add(Math.cos(radian1)*0.6, 0, Math.sin(radian1)*0.6);
            for (Player player : viewers) {
                ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.AQUA),location,player);
                ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.AQUA),location2,player);
                entityLiving2.sendUpdatedmetadata(player);
            }
        } else {
            degree = 10;
            Location location = new Location(entityLiving2.getLocation().getWorld(),entityLiving2.getLocation().getX(),entityLiving2.getLocation().getY(),entityLiving2.getLocation().getZ());
            location.add(0,1.4,0);
            double radian1 = Math.toRadians(degree);
            location.add(Math.cos(radian1)*0.6, 0, Math.sin(radian1)*0.6);
            for (Player player : viewers) {
                ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.AQUA),location,player);
                ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.AQUA),location2,player);
                entityLiving2.sendUpdatedmetadata(player);

            }
        }

        if (currentTick == maxTick) { //cycle change
            currentTick = 2;
            isFall = !isFall;
        }

    }
}
