package org.denizenmc.menus;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import javax.annotation.Nullable;

public interface IMenusAPI {
    /**
     * Register a custom action that can live in a menu.
     * @param action Action to be registered.
     */
    void registerAction(Action action, JavaPlugin plugin);

    /**
     * Get a menu from its name.
     * @param name Name of the menu (without color codes).
     * @return Menu corresponding to the name, null if not found.
     */
    @Nullable
    Menu getMenu(String name);

    /**
     * Get a menu session for a player.
     * If player already has an active session, returns the active session.
     * Fails and returns null if the menu is not found.
     * @param player Player that the session should be activated for.
     * @param name Name of the first menu (no color codes).
     * @return
     */
    Session getSession(Player player, String name);
}
