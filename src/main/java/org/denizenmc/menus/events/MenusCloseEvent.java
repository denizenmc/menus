package org.denizenmc.menus.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.denizenmc.menus.components.Session;
import org.jetbrains.annotations.NotNull;

public class MenusCloseEvent extends Event {
    private Session session;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public MenusCloseEvent(Session session) { this.session = session; }

    public Session getSession() { return session; }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
