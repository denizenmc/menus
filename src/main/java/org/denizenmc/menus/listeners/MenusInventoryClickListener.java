package org.denizenmc.menus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import java.util.ArrayList;

public class MenusInventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Session s = Menus.getInstance().getSessionManager().getSession((Player) event.getWhoClicked());
        if (s == null) return;
        if (event.getClickedInventory() == null || !(event.getClickedInventory().getHolder() instanceof Menu)) return;
        Menu menu = (Menu) event.getClickedInventory().getHolder();
        if (menu.getContent() == null || !menu.getContent().containsKey(event.getSlot())) return;
        if (menu.getContent().get(event.getSlot()) == null) return;
        if (menu.getContent().get(event.getSlot()).getActions().isEmpty()) {
            event.setCancelled(true);
            return;
        }
        for (Action action : new ArrayList<>(menu.getContent().get(event.getSlot()).getActions())) {
            if (action == null) continue;
            action.onClick(s, menu.getCount(event.getSlot(), action), event);
        }
    }
}
