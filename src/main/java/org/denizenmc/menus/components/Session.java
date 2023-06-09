package org.denizenmc.menus.components;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.denizenmc.menus.components.actions.Action;

import java.util.*;

public class Session {
    private final Player player;
    private Stack<Menu> navigator;
    private int page;
    private boolean isPaused;
    private final Map<String, Object> context;

    public Session(Player player, Menu menu) {
        this.player = player;
        navigator = new Stack<>();
        navigator.add(menu);
        page = 1;
        isPaused = false;
        context = new HashMap<>();
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

    public Map<String, Object> getContext() {
        return context;
    }

    public boolean isPaused() { return isPaused; }

    /**
     * Opens a new menu for the player.
     */
    public void open() {
        isPaused = false;
        Menu menu = getMenu();
        if (menu == null) return;
        Inventory view = Bukkit.createInventory(menu,
                menu.getRows() < 1 || menu.getRows() > 6 ? 45 : menu.getRows()*9,
                ChatColor.translateAlternateColorCodes('&', menu.getName()));
        populateInventory(view);
        player.openInventory(view);
    }

    /**
     * Refreshes the player's open menu. Useful for menu/page changes and item updates.
     */
    public void refresh() {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (!(inventory.getHolder() instanceof Menu)) return;
        populateInventory(inventory);
        player.openInventory(inventory);
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
