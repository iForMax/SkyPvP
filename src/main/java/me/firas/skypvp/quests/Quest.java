package me.firas.skypvp.quests;

import com.google.common.base.Strings;
import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;


public class Quest {
    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public ItemStack AddAchievementItem(Player player, int min, int max, Material material, String achievement,String achievementName, String... rewards) {
        int getAchiv = Main.getInstance().get(player).getStats("Achiv" + achievement);

        if (getAchiv < min) {
            ItemStack itemStack = new ItemStack(Material.STAINED_CLAY,1,(short) 14);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TextHelper.text("&e&l"+achievement+" "+achievementName));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(TextHelper.text("&c&lLOCKED"));
            lore.add("");

            lore.add(TextHelper.text("&3&lRewards:"));
            for (String s : rewards){
                if (s == "") continue;
                lore.add(TextHelper.text("&3◆ "+s));
            }
            lore.add("");
            lore.add(TextHelper.text("&cYou need &e"+(min-getAchiv)+" &cmore"));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        if (getAchiv >= max) {
            ItemStack itemStack = new ItemStack(Material.STAINED_CLAY,1,(short) 5);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TextHelper.text("&e&l"+achievement+" "+achievementName));
            ArrayList<String> lore = new ArrayList<>();
            lore.add(TextHelper.text(""));
            lore.add(TextHelper.text("&7["+getProgressBar(max,max,20,'┃',ChatColor.GREEN,ChatColor.RED)+"&7] &8100%"));
            lore.add(TextHelper.text("&a"+max+"&7\\&c"+max));
            lore.add("");
            lore.add(TextHelper.text("&3&lRewards:"));
            for (String s : rewards){
                if (s == "") continue;
                lore.add(TextHelper.text("&3◆ "+s));
            }
            lore.add("");
            lore.add(TextHelper.text("&aCompleted"));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        int percent = getAchiv * 100 / max;
        double b = Math.round(percent * 10.0) / 10.0;
        int finals = (int) b;
        ItemStack itemStack = new ItemStack(Material.STAINED_CLAY,1,(short) 4);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&e&l"+achievement+" "+achievementName));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(TextHelper.text(""));
        lore.add(TextHelper.text("&7["+getProgressBar(getAchiv,max,20,'┃',ChatColor.GREEN,ChatColor.RED)+"&7] &8"+finals+"%"));
        lore.add(TextHelper.text("&a"+getAchiv+"&7\\&c"+max));
        lore.add("");
        lore.add(TextHelper.text("&3&lRewards:"));
        for (String s : rewards){
            if (s == "") continue;
            lore.add(TextHelper.text("&3◆ "+s));
        }
        lore.add("");
        lore.add(TextHelper.text("&e&l&oCurrent"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);


        return itemStack;

    }

    public ItemStack returnPage(){
        return new ItemBuilder(Material.CHEST,1,0).setDisplayname("&6&lBack").build();
    }
    public ItemStack AddAchievementFullItem(Player player, int max, String achievement) {
        int getAchiv = Main.getInstance().get(player).getStats("Achiv" + achievement);
        int percent = getAchiv * 100 / max;
        double b = Math.round(percent * 10.0) / 10.0;
        int finals = (int) b;
        if (achievement.equalsIgnoreCase("kills")) {
            if (getAchiv >= 10000){
                return new ItemBuilder(Material.IRON_SWORD, 1, 0).setDisplayname("&a&lKill").setLore("",TextHelper.text("&7Kill players to earn rewards"),"",TextHelper.text("&aCompleted"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            } else {
                return new ItemBuilder(Material.IRON_SWORD, 1, 0).setDisplayname("&a&lKill").setLore("",TextHelper.text("&7Kill players to earn rewards"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            }
        }
        if (achievement.equalsIgnoreCase("time")) {
            if (getAchiv >= 10000){
                return new ItemBuilder(Material.WATCH, 1, 0).setDisplayname("&a&lTime").setLore("",TextHelper.text("&7Stay in skypvp to earn rewards"),"",TextHelper.text("&aCompleted"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            } else {
                return new ItemBuilder(Material.WATCH, 1, 0).setDisplayname("&a&lTime").setLore("",TextHelper.text("&7Stay in skypvp to earn rewards"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            }
        }
        if (achievement.equalsIgnoreCase("death")) {
            if (getAchiv >= 10000){
                return new ItemBuilder(Material.REDSTONE, 1, 0).setDisplayname("&a&lDeaths").setLore("",ChatColor.GRAY + "Hmmm....","",TextHelper.text("&aCompleted"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            } else {
                return new ItemBuilder(Material.REDSTONE, 1, 0).setDisplayname("&a&lDeaths").setLore("",ChatColor.GRAY + "Hmmm....","",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            }
        }
        if (achievement.equalsIgnoreCase("pickup")) {
            if (getAchiv >= 10000){
                return new ItemBuilder(Material.CHEST, 1, 0).setDisplayname("&a&lPick Up").setLore("",ChatColor.GRAY+"Pick up items to earn rewards","",TextHelper.text("&aCompleted"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            } else {
                return new ItemBuilder(Material.CHEST, 1, 0).setDisplayname("&a&lPick Up").setLore("",ChatColor.GRAY+"Pick up items to earn rewards","",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            }
        }
        if (achievement.equalsIgnoreCase("bow")) {
            if (getAchiv >= 10000){
                return new ItemBuilder(Material.BOW, 1, 0).setDisplayname("&a&lBow").setLore("",ChatColor.GRAY+"Shoot a players to earn rewards","",TextHelper.text("&aCompleted"),"",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            } else {
                return new ItemBuilder(Material.BOW, 1, 0).setDisplayname("&a&lBow").setLore("",ChatColor.GRAY+"Shoot a players to earn rewards","",TextHelper.text("&a&lLeft-Click &7open achievement")).build();
            }
        }

        return null;
    }
    public void getMessage(Player player,String achievement, String nextAchievement,String... Rewards){
        sendCenteredMessage(player,"&b&l&m-----&r &a&lAchievement Completed &b&l&m-----");
        sendCenteredMessage(player,"");
        sendCenteredMessage(player,"&e&l"+achievement+" &7-> &e&l" + nextAchievement);
        sendCenteredMessage(player,"");
        sendCenteredMessage(player,"&3&lRewards");
        sendCenteredMessage(player,"");
        for (String rewards : Rewards){
            sendCenteredMessage(player,"&3"+rewards);
        }
        sendCenteredMessage(player,"");

        sendCenteredMessage(player,"&b&l&m-----&r &a&lAchievement Completed &b&l&m-----");
        player.playSound(player.getLocation(), Sound.LEVEL_UP,1,1);
    }


    public void updateAchievementKills(Player player){
        int getAchievement = Main.getInstance().get(player).getStats("AchivKills");
        if (getAchievement == 10){
            Main.getInstance().getQuest().getMessage(player,"Kills I","Kills II","&cNothing ):");
        }
        if (getAchievement == 50) {
            Main.getInstance().getQuest().getMessage(player, "Kills II", "Kills III", "&520 XP");
        }
        if (getAchievement == 100) {
            Main.getInstance().getQuest().getMessage(player, "Kills III", "Kills IV", "&520 XP");
        }

        if (getAchievement == 500) {
            Main.getInstance().getQuest().getMessage(player, "Kills IV", "Kills V", "&520 XP");
        }

        if (getAchievement == 1000) {
            Main.getInstance().getQuest().getMessage(player, "Kills V", "Kills VI", "&520 XP");
        }

        if (getAchievement == 2000) {
            Main.getInstance().getQuest().getMessage(player, "Kills VI", "Kills VII", "&520 XP");
        }

        if (getAchievement == 3000) {
            Main.getInstance().getQuest().getMessage(player, "Kills VII", "Kills IX", "&520 XP");
        }

        if (getAchievement == 4000) {
            Main.getInstance().getQuest().getMessage(player, "Kills IX", "Kills X", "&520 XP");
        }
        if (getAchievement == 10000) {
            Main.getInstance().getQuest().getMessage(player, "Kills X", "&c&lFINISHED", "&520 XP");
        }







    }
    public static void sendCenteredMessage(Player player, String message) {
        int chatWidth = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH;
        int messageWidth = StringUtils.strip(message).length();

        int padding = (chatWidth - messageWidth) / 2;
        String centeredMessage = StringUtils.repeat(" ", padding) + message;

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',centeredMessage));
    }
}
