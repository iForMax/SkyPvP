package me.firas.skypvp.generators;

import org.bukkit.inventory.ItemStack;

public class help {
    public static ItemStack getHead(String name) {
        byte b;
        int i;
        Heads[] arrayOfHeads;
        for (i = (arrayOfHeads = Heads.values()).length, b = 0; b < i; ) {
            Heads head = arrayOfHeads[b];
            if (head.getName().equalsIgnoreCase(name))
                return head.getItemStack();
            b++;
        }
        return null;
    }
}
