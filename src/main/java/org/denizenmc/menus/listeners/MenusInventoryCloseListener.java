package org.denizenmc.menus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.events.MenusCloseEvent;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.denizenmc.menus.services.DelayedMenuTask;

public class MenusInventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            if (event.getInventory().getHolder() instanceof Menu) {
                Menu menu = (Menu) event.getInventory().getHolder();
                Session s = Menus.getAPI().getSession((Player) event.getPlayer());
                if (menu.getName().equalsIgnoreCase(MenusConfiguration.MENU_EDIT_MENU)) {
                    if (s != null && s.getMenu().getName().equalsIgnoreCase(MenusConfiguration.MENU_EDIT_MENU)) {
                        // check if moving to edit element
                        if (s.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance()) instanceof Element) return;
                        s.getContext().remove(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
                        s.pop();
                        Menus.getInstance().getTaskService().add(new DelayedMenuTask(1, null, (Player) event.getPlayer()));
                    }
                } else {
                    if (s != null) Bukkit.getPluginManager().callEvent(
                            new MenusCloseEvent(s));
                }
            }
        }
    }
}
