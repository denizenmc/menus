package org.denizenmc.menus.services;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import java.util.HashMap;
import java.util.Map;

public class OpenMenuService {
    public static void open(Session session) {
        Menu menu = session.getMenu();
        Inventory view = Bukkit.createInventory(menu,
                menu.getSize(),
                ChatColor.translateAlternateColorCodes('&', menu.getName()));
        Map<String, Integer> counts = new HashMap<>();
        for (Integer i : menu.getContent().keySet()) {
            if (i < view.getSize()) {
                if (menu.getContent().get(i) != null && menu.getContent().get(i).getAction() != null) {
                    String key = menu.getContent().get(i).getAction().getClass().getName();
                    if (!counts.containsKey(key)) counts.put(key, 1);
                    else counts.put(key, counts.get(key)+1);
                    view.setItem(i, menu.getContent().get(i).build(session, counts.get(key)));
                } else {
                    view.setItem(i, menu.getContent().get(i).build(session, 1));
                }
            }
        }
        session.getPlayer().openInventory(view);
    }
}
