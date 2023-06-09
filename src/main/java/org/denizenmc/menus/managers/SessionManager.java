package org.denizenmc.menus.managers;

import org.bukkit.entity.Player;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private List<Session> sessions;
    public SessionManager() {
        sessions = new ArrayList<>();
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
        return s;
    }

    public void stopSession(Player player) {
        sessions.removeIf(s -> s.getPlayer().getUniqueId().equals(player.getUniqueId()));
    }


}
