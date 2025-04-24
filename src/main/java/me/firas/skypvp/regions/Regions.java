package me.firas.skypvp.regions;

import me.firas.skypvp.Main;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Regions {
    private final HashMap<String, Region> regions;

    public Regions() {
        regions = new HashMap<String, Region>();
    }

    public void setPos1(Player player, String name) {
        Region region = regions.get(name);
        if (region == null) {
            region = new Region(name);
            regions.put(name, region);
        }
        region.setPos1(player.getLocation());
    }

    public void setPos2(Player player, String name) {
        Region region = regions.get(name);
        if (region == null) {
            region = new Region(name);
            regions.put(name, region);
        }
        region.setPos2(player.getLocation());
    }

    public void createRegion(String name) {
        Region region = regions.get(name);
        if (region == null) return;
        Location pos1 = region.getPos1();
        Location pos2 = region.getPos2();
        Main.getInstance().getRegionsHandler().setObject("regions." + name + ".pos1", pos1);
        Main.getInstance().getRegionsHandler().setObject("regions." + name + ".pos2", pos2);
        Main.getInstance().getRegionsHandler().setObject("regions." + name + ".pvp", false);
        Main.getInstance().regionMap.put(name, region);
    }

    public Location getPos1(String name) {
        Region region = regions.get(name);
        if (region == null) {
            region = new Region(name);
            regions.put(name, region);
        }
        return region.getPos1();
    }

    public Location getPos2(String name) {
        Region region = regions.get(name);
        if (region == null) {
            region = new Region(name);
            regions.put(name, region);
        }
        return region.getPos2();
    }

    public void loadRegions() {
        FileConfiguration config = Main.getInstance().getRegionsHandler().getManager();
        if (!config.contains("regions")) return;
        ConfigurationSection regionSection = config.getConfigurationSection("regions");
        for (String regionName : regionSection.getKeys(false)) {
            ConfigurationSection regionConfig = regionSection.getConfigurationSection(regionName);
            Location pos1 = (Location) regionConfig.get("pos1");
            Location pos2 = (Location) regionConfig.get("pos2");
            Region region = new Region(regionName);
            region.setPos1(pos1);
            region.setPos2(pos2);
            region.setPvp(regionConfig.getBoolean("pvp"));
            Main.getInstance().regionMap.put(regionName, region);
        }
    }

}
