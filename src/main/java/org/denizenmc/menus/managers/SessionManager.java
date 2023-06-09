package org.denizenmc.menus.managers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionManager extends BukkitRunnable {
    private List<Session> sessions;
    private ConcurrentMap<Session, Integer> timeSinceRefresh;
    public SessionManager() {
        sessions = new ArrayList<>();
        timeSinceRefresh = new ConcurrentHashMap<>();
    }

    @Nullable
    public Session getSession(Player player) {
        for (Session s : new ArrayList<>(sessions)) {
            if (s != null && s.getPlayer().getUniqueId().equals(player.getUniqueId())) return s;
        }
        return null;
    }

    public Session startSession(Player player, Menu menu) {
        Session s = new Session(player, menu);
        sessions.add(s);
        timeSinceRefresh.put(s, 0);
        return s;
    }

    public void stopSession(Player player) {
        Session s = getSession(player);
        if (s != null) {
            timeSinceRefresh.remove(s);
            sessions.remove(s);
        }
    }

    @Override
    public void run() {
        for (Session s : timeSinceRefresh.keySet()) {
            if (s != null && !s.isPaused() && s.getMenu() != null && timeSinceRefresh.get(s) >= s.getMenu().getRefreshRateSeconds()) {
                s.refresh();
                timeSinceRefresh.put(s, 0);
            } else {
                timeSinceRefresh.put(s, timeSinceRefresh.get(s)+1);
            }
        }
    }
}
