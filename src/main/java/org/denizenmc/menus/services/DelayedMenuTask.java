package org.denizenmc.menus.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Session;

public class DelayedMenuTask implements IMenusTask {
    private final int ticks;
    private final String menu;
    private final Player player;
    public DelayedMenuTask(int ticks, String menu, Player player) {
        this.ticks = ticks; this.menu = menu; this.player = player;
    }
    @Override
    public void execute() {
        if (player == null || Bukkit.getPlayer(player.getUniqueId()) == null) return;
        Session s;
        if (menu == null) {
            s = Menus.getAPI().getSession(player);
            if (s != null) s.resume();
        } else {
            s = Menus.getAPI().getSession(player, menu);
            if (s != null) s.open();
        }
    }
    @Override
    public int getTicks() {
        return ticks;
    }
}
