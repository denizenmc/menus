package org.denizenmc.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MenusUtils {
    public static ItemStack getHead(String username) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        OfflinePlayer player = Bukkit.getOfflinePlayer(username);
        if (player != null) {
            ((SkullMeta) meta).setOwningPlayer(player);
        }
        item.setItemMeta(meta);
        return item;
    }
}
