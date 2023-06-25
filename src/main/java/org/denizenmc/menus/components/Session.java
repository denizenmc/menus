package org.denizenmc.menus.components;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.events.MenusOpenEvent;
import org.denizenmc.menus.events.MenusRefreshEvent;
import org.denizenmc.menus.guis.MenusContextKeys;

import java.util.*;

public class Session {
    private final Player player;
    private Stack<Menu> navigator;
    private int page;
    private boolean isPaused;
    private final SessionContext context;

    public Session(Player player, Menu menu) {
        this.player = player;
        navigator = new Stack<>();
        navigator.add(menu);
        page = 1;
        isPaused = false;
        context = new SessionContext();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the last menu in the navigator list.
     * @return Current menu.
     */
    public Menu getMenu() {
        return navigator.isEmpty() ? null : navigator.get(navigator.size()-1);
    }

    /**
     * Pushes a menu onto the navigator list, making it the current menu.
     * @param menu Menu to be shown.
     */
    public void push(Menu menu) {
        navigator.push(menu);
    }

    /**
     * Pops current menu from the navigator list, making the previous menu the current menu.
     */
    public void pop() {
        if (navigator.size() > 1) navigator.pop();
    }

    public void pause() { isPaused = true; }
    public void resume() {
        isPaused = false;
        open();
    }

    public Player getPlayer() {
        return player;
    }

    public SessionContext getContext() {
        return context;
    }

    public boolean isPaused() { return isPaused; }

    /**
     * Opens menu at the top of the navigator stack for the player.
     */
    public void open() {
        isPaused = false;
        page = 1;
        context.remove(MenusContextKeys.ELEMENT_TO_COPY, Menus.getInstance());
        Menu menu = getMenu();
        if (menu == null) return;
        Bukkit.getPluginManager().callEvent(
                new MenusOpenEvent(this));
        Inventory view = Bukkit.createInventory(menu,
                menu.getRows() < 1 || menu.getRows() > 6 ? 45 : menu.getRows()*9,
                ChatColor.translateAlternateColorCodes('&', menu.getTitle()));
        populateInventory(view);
        player.openInventory(view);
    }

    /**
     * Refreshes the player's open menu. Useful for menu data changes and item updates.
     * Useful for page changes.
     * DO NOT USE WHEN YOU HAVE CHANGED THE SESSION'S MENU!
     */
    public void refresh() {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (!(inventory.getHolder() instanceof Menu)) return;
        Bukkit.getPluginManager().callEvent(
                new MenusRefreshEvent(this));
        populateInventory(inventory);
    }

    private void populateInventory(Inventory view) {
        Map<String, Integer> counts = new HashMap<>();
        Menu menu = getMenu();
        if (menu == null) return;
        for (Integer i : menu.getContent().keySet()) {
            if (i < view.getSize()) {
                if (menu.getContent().get(i) != null && !menu.getContent().get(i).getActions().isEmpty()) {
                    for (Action a : menu.getContent().get(i).getActions()) {
                        String key = a.getName();
                        if (!counts.containsKey(key)) counts.put(key, 1);
                        else counts.put(key, counts.get(key)+1);
                    }
                    view.setItem(i, menu.getContent().get(i).build(this, new HashMap<>(counts)));
                } else {
                    view.setItem(i, menu.getContent().get(i).build(this, counts));
                }
            }
        }
    }

}
