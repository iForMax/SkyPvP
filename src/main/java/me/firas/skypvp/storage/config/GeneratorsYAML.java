package me.firas.skypvp.storage.config;

import me.firas.skypvp.Main;
import me.firas.skypvp.generators.GeneratorType;
import me.firas.skypvp.util.YMLFile;

public class GeneratorsYAML  extends YMLFile {
    public GeneratorsYAML() {
        super("Generators.yml", Main.getPlugin(Main.class).getDataFolder().getPath());
        createNew();
    }
    public void addLocation(String type, org.bukkit.Location location, GeneratorType generatorType){
        setObject(type.toLowerCase()+"-"+(getValue("Size of "+generatorType.name())), location);
    }
    public void addLocation(String name,org.bukkit.Location location){
        setObject(name.toLowerCase(), location);
    }
    public void removeLocation(String type,int number){
        setObject(type.toLowerCase()+"-"+number,null);
    }
    public void removeLocation(String type){
        setObject(type.toLowerCase(),null);
    }
    public org.bukkit.Location getLocation(String name){
        return (org.bukkit.Location) getObject(name.toLowerCase());
    }

    @Override
    public void setDefaults() {
        //

    }
}
