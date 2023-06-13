package org.denizenmc.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.denizenmc.menus.components.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
    public static List<Menu> getMenusFromList(List list) {
        List<Menu> elements = new ArrayList<>();
        if (list != null && !list.isEmpty() && list.get(0) instanceof Menu) {
            for (Object o : list) elements.add((Menu) o);
        }
        return elements;
    }
    public static int getIntFromString(String string) {
        int n = 0;
        try {
            n = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            //ProceduralLogger.log(Level.SEVERE, e.getMessage());
        }
        return n;
    }
}
