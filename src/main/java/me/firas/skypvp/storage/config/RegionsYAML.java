package me.firas.skypvp.storage.config;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.YMLFile;

public class RegionsYAML extends YMLFile {
    public RegionsYAML() {
        super("regions.yml", Main.getPlugin(Main.class).getDataFolder().getPath());
        createNew();
    }
    @Override
    public void setDefaults() {
        setString("regions","");
    }
}
