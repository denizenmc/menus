package org.denizenmc.menus.components;

import org.bukkit.entity.Player;

public class Session {
    private final Player player;
    private final Menu menu, previousMenu;
    private int page;
    public Session(Player player, Menu menu, Menu previousMenu) {
        this.player = player;
        this.menu = menu;
        this.previousMenu = previousMenu;
        page = 1;
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
}
