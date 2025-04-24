package me.firas.skypvp.enums;

import lombok.Getter;
import me.firas.skypvp.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public enum KitType {
    MEMBER, Iron, Diamond, Mars, Super;
    @Getter
    ItemStack[] memberKit, ironKit, diamondKit, marsKit, superKit;
    private static final HashMap<KitType, ItemStack[]> kit;

    KitType() {
        init();
    }
    public ItemStack[] getArmour() {
        ItemStack[] kits = kit.get(this);
        return Arrays.copyOfRange(kits, 0, 4);
    }
    public ItemStack[] getTools() {
        ItemStack[] kits = kit.get(this);
        return Arrays.copyOfRange(kits, 4, kits.length);
    }
    void init() {
        String colorm = "&9";
        memberKit = new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS,1,0).setDisplayname(colorm+"Boots").build(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS,1,0).setDisplayname(colorm+"Leggings").build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE,1,0).setDisplayname(colorm+"Chestplate").build(),
                new ItemBuilder(Material.CHAINMAIL_HELMET,1,0).setDisplayname(colorm+"Helmet").build(),
                new ItemBuilder(Material.STONE_SWORD,1,0).setDisplayname(colorm+"Sword").build(),
                new ItemBuilder(Material.BOW,1,0).setDisplayname(colorm+"Bow").build(),
                new ItemBuilder(Material.ARROW,8,0).setDisplayname(colorm+"Arrow").build(),
        };

        String colord = "&7";
        ironKit = new ItemStack[]{
                new ItemBuilder(Material.CHAINMAIL_BOOTS,1,0).setDisplayname(colord+"Boots").build(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS,1,0).setDisplayname(colord+"Leggings").build(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE,1,0).setDisplayname(colord+"Chestplate").build(),
                new ItemBuilder(Material.CHAINMAIL_HELMET,1,0).setDisplayname(colord+"Helmet").build(),
                new ItemBuilder(Material.STONE_SWORD,1,0).setDisplayname(colord+"Sword").build(),
                new ItemBuilder(Material.BOW,1,0).setDisplayname(colord+"Bow").build(),
                new ItemBuilder(Material.ARROW,12,0).setDisplayname(colord+"Arrow").build(),
        };
        String colorv = "&b";
        diamondKit = new ItemStack[]{
                new ItemBuilder(Material.CHAINMAIL_BOOTS,1,0).setDisplayname(colorv+"Boots").build(),
                new ItemBuilder(Material.IRON_LEGGINGS,1,0).setDisplayname(colorv+"Leggings").build(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE,1,0).setDisplayname(colorv+"Chestplate").build(),
                new ItemBuilder(Material.IRON_HELMET,1,0).setDisplayname(colorv+"Helmet").build(),
                new ItemBuilder(Material.IRON_SWORD,1,0).setDisplayname(colorv+"Sword").build(),
                new ItemBuilder(Material.BOW,1,0).setDisplayname(colorv+"Bow").build(),
                new ItemBuilder(Material.ARROW,16,0).setDisplayname(colorv+"Arrow").build(),
        };
        String colorp = "&a";
        marsKit = new ItemStack[]{
                new ItemBuilder(Material.IRON_BOOTS,1,0).setDisplayname(colorp+"Boots").build(),
                new ItemBuilder(Material.IRON_LEGGINGS,1,0).setDisplayname(colorp+"Leggings").build(),
                new ItemBuilder(Material.IRON_CHESTPLATE,1,0).setDisplayname(colorp+"Chestplate").build(),
                new ItemBuilder(Material.IRON_HELMET,1,0).setDisplayname(colorp+"Helmet").build(),
                new ItemBuilder(Material.IRON_SWORD,1,0).setDisplayname(colorp+"Sword").build(),
                new ItemBuilder(Material.BOW,1,0).setDisplayname(colorp+"Arrow").build(),
                new ItemBuilder(Material.ARROW,24,0).build(),
        };
        String colorr = "&5";
        superKit = new ItemStack[]{
                new ItemBuilder(Material.IRON_BOOTS,1,0).setDisplayname(colorr+"Boots").build(),
                new ItemBuilder(Material.IRON_LEGGINGS,1,0).setDisplayname(colorr+"Leggings").build(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE,1,0).setDisplayname(colorr+"Chestplate").build(),
                new ItemBuilder(Material.IRON_HELMET,1,0).setDisplayname(colorr+"Helmet").build(),
                new ItemBuilder(Material.IRON_SWORD,1,0).setDisplayname(colorr+"Sword").build(),
                new ItemBuilder(Material.BOW,1,0).setDisplayname(colorr+"Bow").build(),
                new ItemBuilder(Material.ARROW,32,0).setDisplayname(colorr+"Arrow").build(),
        };
    }
    static {
        HashMap<KitType, ItemStack[]> map = new HashMap<>();
        map.put(MEMBER, MEMBER.getMemberKit());
        map.put(Iron, Iron.getIronKit());
        map.put(Diamond, Diamond.getDiamondKit());
        map.put(Mars, Mars.getMarsKit());
        map.put(Super, Super.getSuperKit());
        kit = map;
    }

}
