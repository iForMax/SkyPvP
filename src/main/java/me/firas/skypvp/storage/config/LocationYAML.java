package me.firas.skypvp.storage.config;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.YMLFile;

public class LocationYAML extends YMLFile {
    public LocationYAML() {
        super("Locations.yml", Main.getPlugin(Main.class).getDataFolder().getPath());
        createNew();
    }
    public void setLocation(String name, org.bukkit.Location location){
        setObject(name.toLowerCase(), location);
    }
    public org.bukkit.Location getLocation(String name){
        return (org.bukkit.Location) getObject(name);
    }

    @Override
    public void setDefaults() {
        //

    }
}
