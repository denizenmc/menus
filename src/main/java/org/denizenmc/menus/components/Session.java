package org.denizenmc.menus.components;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Player player;
    private final Menu menu, previousMenu;
    private int page;
    private Map<String, Object> cache;
    public Session(Player player, Menu menu, Menu previousMenu) {
        this.player = player;
        this.menu = menu;
        this.previousMenu = previousMenu;
        page = 1;
        cache = new HashMap<>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Menu getPreviousMenu() {
        return previousMenu;
    }

    public Menu getMenu() {
        return menu;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, Object> getCache() {
        return cache;
    }
}
