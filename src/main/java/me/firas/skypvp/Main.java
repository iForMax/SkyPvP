package me.firas.skypvp;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.mqzen.boards.BoardManager;
import dev.mqzen.boards.base.BoardAdapter;



import lombok.Getter;
import me.firas.skypvp.combat.CombatPlayer;
import me.firas.skypvp.commands.*;
import me.firas.skypvp.commands.add.*;
import me.firas.skypvp.enchant.ClickEnchant;
import me.firas.skypvp.enderchest.EnderChests;
import me.firas.skypvp.enderchest.EventChest;
import me.firas.skypvp.enums.KitType;
import me.firas.skypvp.events.Boss;
import me.firas.skypvp.events.Event;

import me.firas.skypvp.generators.emeraldGen.EmeraldManager;
import me.firas.skypvp.generators.enderchest.EnderchestManager;
import me.firas.skypvp.generators.goldGen.GoldManager;
import me.firas.skypvp.generators.ironGen.IronManager;
import me.firas.skypvp.generators.koth.KothManager;
import me.firas.skypvp.generators.menu.MenuManager;
import me.firas.skypvp.generators.xpGen.XpManager;
import me.firas.skypvp.kits.Kit;
import me.firas.skypvp.listeners.AllEvents;
import me.firas.skypvp.listeners.JoinEvent;
import me.firas.skypvp.generators.*;
import me.firas.skypvp.listeners.PlayerKillEvent;
import me.firas.skypvp.menu.ClickEvent;
import me.firas.skypvp.menu.ItemStackAPI;
import me.firas.skypvp.packages.Package;
import me.firas.skypvp.quests.InventoryClickEvent;
import me.firas.skypvp.quests.Quest;
import me.firas.skypvp.regions.Region;
import me.firas.skypvp.regions.Regions;
import me.firas.skypvp.regions.listener.RDamage;
import me.firas.skypvp.regions.listener.Wand;
import me.firas.skypvp.repair.AnvilListener;
import me.firas.skypvp.scoreboard.Board;
import me.firas.skypvp.spectate.system.MenuListener;
import me.firas.skypvp.spectate.system.PlayerMenuUtility;
import me.firas.skypvp.storage.config.GeneratorsYAML;
import me.firas.skypvp.storage.config.LocationYAML;
import me.firas.skypvp.storage.config.RegionsYAML;
import me.firas.skypvp.storage.config.ScoreboardYML;
import me.firas.skypvp.storage.data.DataManager;
import me.firas.skypvp.storage.data.PlayerData;
import me.firas.skypvp.trails.InventoryClick;
import me.firas.skypvp.trails.TrailListener;
import me.firas.skypvp.util.TextHelper;
import me.firas.skypvp.util.Title;
import net.minecraft.server.v1_8_R3.EntityHorse;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

public final class Main extends JavaPlugin {



    public HashSet<Player> spectate = new HashSet<>();

    public HashMap<UUID, PlayerData> stats = new HashMap<>();

    public HashSet<Player> build = new HashSet<>();
    public HashSet<Player> GameMaster = new HashSet<>();


    public ArrayList<Player> HubCooldown = new ArrayList<>();
    public ArrayList<UUID> HubCanLeave = new ArrayList<>();


    public HashMap<String, Region> regionMap = new HashMap<>();
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();




    private LocationYAML locationHandler;

    private GeneratorsYAML generatorsHandler;

    private static Main instance;

    private ScoreboardYML scoreboardHandler;


    private ItemStackAPI itemStackAPI;

    private DataManager dataManager;

    private EnderChests enderchests;

    private CombatPlayer combatPlayer;

    private Kit kit;

    private Regions regions;

    private RegionsYAML regionsHandler;



    private Quest quest;

    private Event events;


    private Boss boss;

    private Package aPackage;


    public LocationYAML getLocationHandler() {
        return this.locationHandler;
    }

    public static Main getInstance() {
        return instance;
    }

    public ScoreboardYML getScoreboardHandler() {
        return this.scoreboardHandler;
    }



    public ItemStackAPI getItemStackAPI() {
        return this.itemStackAPI;
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public EnderChests getEnderchests() {
        return this.enderchests;
    }

    public CombatPlayer getCombatPlayer() {
        return this.combatPlayer;
    }

    public Kit getKit() {
        return this.kit;
    }

    public Regions getRegions() {
        return this.regions;
    }

    public RegionsYAML getRegionsHandler() {
        return this.regionsHandler;
    }



    public Quest getQuest() {
        return this.quest;
    }

    public Event getEvents() {
        return this.events;
    }


    public Boss getBoss() {
        return this.boss;
    }

    public Package getAPackage() {
        return this.aPackage;
    }



    public GeneratorsYAML getGeneratorsHandler() {
        return this.generatorsHandler;
    }

    public ArrayList<String> times = new ArrayList<>();

    public ArrayList<String> getTimes() {
        return this.times;
    }

    @Getter
    public ProtocolManager protocolManager;


    public void setTimes() {
        this.times.clear();
        this.times.add("Morning");
        this.times.add("Day");
        this.times.add("Sunset");
        this.times.add("Midnight");
        this.times.add("Dawn");
    }

    public void updateTime(Player player) {
        String time = getInstance().get(player).getTime();
        boolean current = false;
        if (time.equals("Day")) {
            player.setPlayerTime(6000L, current);
        } else if (time.equals("Morning")) {
            player.setPlayerTime(0L, current);
        } else if (time.equals("Sunset")) {
            player.setPlayerTime(12500L, current);
        } else if (time.equals("Midnight")) {
            player.setPlayerTime(18000L, current);
        } else if (time.equals("Dawn")) {
            player.setPlayerTime(23500L, current);
        }
    }

    private void init() {
        instance = this;
        this.scoreboardHandler = new ScoreboardYML();
        this.itemStackAPI = new ItemStackAPI();
        this.enderchests = new EnderChests();
        dataManager = new DataManager();
        this.locationHandler = new LocationYAML();
        this.combatPlayer = new CombatPlayer();
        this.generatorsHandler = new GeneratorsYAML();
        this.kit = new Kit();
        this.regions = new Regions();
        this.regionsHandler = new RegionsYAML();
        this.quest = new Quest();
        this.events = new Event();
        this.boss = new Boss();
        this.aPackage = new Package();
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public String World = "world";

    public void onEnable() {
        listeners();
        voidKill();
        Main.this.config();
        (new BukkitRunnable() {
            public void run() {
                Main.this.cmds();
                Main.this.init();
                Main.this.boardAdapter();
                Main.this.getRegions().loadRegions();
                Main.this.setTimes();
                IronManager.onStartUp();
                GoldManager.onStartUp();
                EmeraldManager.onStartUp();
                XpManager.onStartUp();
                KothManager.onStartUp();
                EnderchestManager.onStartUp();
                MenuManager.onStartUp();
                Main.getInstance().getEvents().startTime();

                packetRegister();
                if (!Main.this.getDataManager().connect((Plugin) Main.this))
                    System.out.println("error while connecting to sql database!");
                final World world = Bukkit.getServer().getWorld(Main.this.World);
                for (Entity entity : world.getEntities()){
                    if (entity instanceof Giant){
                        for (Entity entity1 : entity.getNearbyEntities(10,10,10)){
                            if (entity1 instanceof ArmorStand){
                                entity1.remove();
                            }
                        }
                    }
                }
                (Main.getInstance()).aPackage.setPackages();
                Main.getInstance().getEvents().startCooldown();
                Bukkit.getWorld(Main.this.World).setDifficulty(Difficulty.NORMAL);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    BoardManager.getInstance().setupNewBoard(players, (BoardAdapter) new Board());
                    Main.getInstance().get(players).save();
                }
                (new BukkitRunnable() {
                    int Times = 1801;

                    public void run() {
                        Bukkit.getWorld(Main.this.World).setTime(18000L);
                        this.Times--;
                        if (this.Times == 60) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e60 &7seconds."));
                            return;
                        }
                        if (this.Times == 30) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e30 &7seconds."));
                            return;
                        }
                        if (this.Times == 10) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e10 &7seconds."));
                            return;
                        }
                        if (this.Times == 5) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e5 &7seconds."));
                            return;
                        }
                        if (this.Times == 4) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e4 &7seconds."));
                            return;
                        }
                        if (this.Times == 3) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e3 &7seconds."));
                            return;
                        }
                        if (this.Times == 2) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e2 &7seconds."));
                            return;
                        }
                        if (this.Times == 1) {
                            Bukkit.broadcastMessage(TextHelper.format("&7Ground Items will be removed in &e1 &7seconds."));
                            return;
                        }
                        if (this.Times == 0) {
                            int size = 0;
                            for (Entity entity : world.getEntities()) {
                                if (entity instanceof org.bukkit.entity.Item) {
                                    size++;
                                    entity.remove();
                                }
                            }
                            Bukkit.broadcastMessage(TextHelper.text("&a"));
                            Bukkit.broadcastMessage(TextHelper.text("&r                      &e***  &6Items Cleared  &e***"));
                            Bukkit.broadcastMessage(TextHelper.text("&r                         &7(Removed " + size + " &7Items)"));
                            Bukkit.broadcastMessage(TextHelper.text("&a"));
                            MenuManager.reloadStartUp();
                            this.Times = 1801;
                            return;
                        }
                    }
                }).runTaskTimer((Plugin) Main.this, 0L, 20L);
            }
        }).runTaskLater((Plugin) this, 1L);
    }

    public void voidKill() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().getY() <= 55.0D && (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE))) {
                        if (player.getAllowFlight()) {
                            player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
                            return;
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                this.cancel();
                                Main.getInstance().get(player).setStreak(0);
                                Main.getInstance().getKit().spawnDrops(player.getInventory().getContents(), player.getInventory().getArmorContents());

                                player.closeInventory();
                                ClearEverything(player);
                                if (Main.getInstance().getCombatPlayer().isInCombat(player) &&
                                        (Main.getInstance().getCombatPlayer()).combatPlayer.containsKey(player)) {
                                    Player enemy = (Player) (Main.getInstance().getCombatPlayer()).combatPlayer.get(player);
                                    PlayerKillEvent killEvent = new PlayerKillEvent(enemy);
                                    Bukkit.getPluginManager().callEvent(killEvent);
                                    player.teleport(enemy.getLocation());
                                    DecimalFormat df = new DecimalFormat("#.0");
                                    String heartsLeft = df.format(enemy.getHealth()/2);
                                    if (enemy != null) {
                                        int coins;
                                        Random ran = new Random();
                                        if (Main.getInstance().get(player).getKills() >= 1000) {
                                            if (Main.getInstance().get(player).kdr() <= 1.0D) {
                                                coins = ran.nextInt(10) + 1;
                                            } else if (Main.getInstance().get(player).kdr() <= 2.0D) {
                                                coins = ran.nextInt(15) + 10;
                                            } else if (Main.getInstance().get(player).kdr() <= 3.0D) {
                                                coins = ran.nextInt(20) + 15;
                                            } else if (Main.getInstance().get(player).kdr() <= 4.0D) {
                                                coins = ran.nextInt(25) + 20;
                                            } else if (Main.getInstance().get(player).kdr() <= 5.0D) {
                                                coins = ran.nextInt(30) + 25;
                                            } else if (Main.getInstance().get(player).kdr() <= 8.0D) {
                                                coins = ran.nextInt(40) + 30;
                                            } else {
                                                coins = ran.nextInt(50) + 40;
                                            }
                                        } else {
                                            coins = ran.nextInt(10) + 1;
                                        }
                                        int points = Main.getInstance().get(player).getPoints()/20;
                                        Main.getInstance().get(enemy).addStats("coins", coins);
                                        Main.getInstance().get(player).addStats("deaths", 1);
                                        Main.getInstance().get(enemy).addStats("points",points);
                                        player.sendMessage(TextHelper.format("&cYour enemy &9" + enemy.getDisplayName() + " &cstill had &e" + heartsLeft + "&c❤"));
                                        player.sendMessage(TextHelper.format("&c&l- "+points+" Points"));
                                        enemy.sendMessage(TextHelper.format("&9" + player.getDisplayName() + " &chas been &lELIMINATED&c!"));
                                        enemy.sendMessage(TextHelper.format("&a&l+ "+points+" Points &6&l+ "+coins+" Coins"));
                                        Main.getInstance().get(player).addStats("points",-points);
                                        Main.getInstance().get(player).setStreak(0);
                                        Main.getInstance().get(enemy).addStats("kills", 1);
                                        if (Main.getInstance().get(player).getPoints() <= 0){
                                            Main.getInstance().get(player).setPoints(0);
                                        }
                                    }
                                }
                                if (!Main.getInstance().getCombatPlayer().isInCombat(player))
                                    player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
                                Main.getInstance().get(player).addStats("deaths", 1);
                                Main.getInstance().get(player).setStreak(0);
                                if (Main.getInstance().getCombatPlayer().isInCombat(player))
                                    Main.getInstance().getCombatPlayer().removePlayer(player);
                                Respawning(player);
                            }
                        }.runTask(Main.this);

                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 20L);
    }

    public void Respawning(final Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        (new BukkitRunnable() {
            int respawn = 1;

            int respawntick = 9;

            public void run() {
                if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                    cancel();
                    return;
                }
                this.respawntick--;
                if (this.respawn == 0 && this.respawntick == 0) {
                    cancel();
                    player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
                    player.setGameMode(GameMode.SURVIVAL);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
                    player.setHealth(20.0D);
                    Main.getInstance().getKit().grantKit(player, KitType.valueOf(Main.getInstance().get(player).getKit()));
                    (new Title("", "&e", 0, 1, 0)).send(player);
                    return;
                }
                if (this.respawntick == -1) {
                    this.respawn--;
                    this.respawntick = 9;
                }
                (new Title("", "&bRespawn in &f" + this.respawn + "." + this.respawntick, 0, 25, 0)).send(player);
            }
        }).runTaskTimer((Plugin) Main.getInstance(), 0L, 2L);
    }

    public void ClearEverything(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setItemOnCursor(null);
        player.getInventory().clear();
        player.updateInventory();

    }

    public static String getProgressBar ( int current, int max, int totalBars, char symbol, ChatColor
        completedColor,
                ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }




    private void packetRegister() {
        Main.getInstance().protocolManager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                if (packet == null){
                    return;
                }
                Player p = event.getPlayer();
                if (packet.getType() == PacketType.Play.Client.USE_ENTITY) {
                    if (packet.getEntityUseActions().read(0) != EnumWrappers.EntityUseAction.ATTACK) {
                        int entityID = packet.getIntegers().read(0);
                        if (EnderchestManager.pool.containsKey(entityID)){
                            Player player = Bukkit.getPlayer(p.getUniqueId());
                            if (Main.getInstance().spectate.contains(player)) return;
                            Main.getInstance().getEnderchests().openEnderChest(player,1);
                            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1F, 0.5F);
                            EventChest.UpgradeECCooldown.addCooldown(player.getUniqueId());
                        }
                        if (MenuManager.list.contains(entityID)) {
                            Player player = Bukkit.getPlayer(p.getUniqueId());
                            if (Main.getInstance().spectate.contains(player)) return;
                            try {
                                new me.firas.skypvp.menu.menus.Menu(player);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }

            }
        });
    }

    private void listeners() {
        getServer().getPluginManager().registerEvents(new AnvilListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new Pickup(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new AllEvents(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new ClickEnchant(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new EventChest(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new RDamage(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new Wand(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new TrailListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new MenuListener(), (Plugin)this);

    }

    private void cmds() {
        getCommand("generator").setExecutor((CommandExecutor)new Generators());
        getCommand("addcoins").setExecutor((CommandExecutor)new AddCoins());
        getCommand("addkills").setExecutor((CommandExecutor)new AddKDR());
        getCommand("adddeaths").setExecutor((CommandExecutor)new AddDeaths());
        getCommand("stats").setExecutor((CommandExecutor)new Stats());
        getCommand("ranks").setExecutor((CommandExecutor)new Ranks());
        getCommand("scramble").setExecutor((CommandExecutor)new Scramble());
        getCommand("setlocation").setExecutor((CommandExecutor)new SetLocation());
        getCommand("trash").setExecutor((CommandExecutor)new Trash());
        getCommand("spectate").setExecutor((CommandExecutor)new Spectate());
        getCommand("region").setExecutor((CommandExecutor)new me.firas.skypvp.commands.Region());
        getCommand("achiv").setExecutor((CommandExecutor)new AddAchievement());
        getCommand("hubcooldown").setExecutor((CommandExecutor)new Hub());
        getCommand("build").setExecutor(new Build());
        getCommand("Vote").setExecutor(new Vote());
        getCommand("settings").setExecutor(new Settings());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("addpoints").setExecutor(new AddPoints());
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("tpa").setExecutor(new Tpa());
        getCommand("resetstats").setExecutor(new ResetStats());
        getCommand("reloadmenu").setExecutor(new ReloadMenu());



    }

    public void onDisable() {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            getInstance().get(player).save();
        getDataManager().disconnect();
        Main.getInstance().getCombatPlayer().combatPlayer.clear();
        Main.getInstance().getCombatPlayer().combatLogged.clear();

    }

    private void boardAdapter() {
        BoardManager.load((Plugin)this);
        BoardManager.getInstance().setUpdateInterval(20L);
        BoardManager.getInstance().startBoardUpdaters();
    }

    public static ItemStack createSkull(String url, String name) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if (url.isEmpty())
            return head;
        SkullMeta headMeta = (SkullMeta)head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException|NoSuchFieldException|SecurityException|IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta((ItemMeta)headMeta);
        return head;
    }

    private void config() {
        Collections.<String>singletonList("config").forEach(this::checkNewOrDeletedConfig);
    }

    private void checkNewOrDeletedConfig(String config) {
        boolean exists = true;
        File cfg = new File(getDataFolder() + "/" + config + ".yml");
        try {
            if (!cfg.exists())
                exists = false;
        } catch (Exception exception) {}
        if (!exists)
            try {
                if (config.equals("config")) {
                    getConfig().options().copyDefaults(true);
                    saveConfig();
                } else {
                    System.out.println(
                            cfg.createNewFile() ? (config + " has been created.") : (config + " was not created."));
                }
            } catch (Exception e) {
                System.out.println("fucked");
            }
    }

    public PlayerData get(Player p) {
        return (p == null) ? null : this.stats.computeIfAbsent(p.getUniqueId(), k -> (new PlayerData(p.getUniqueId(), p.getName())).load());
    }
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them add add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
        }
    }
}
