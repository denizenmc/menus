package org.denizenmc.menus;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import javax.annotation.Nullable;

public class MenusAPI implements IMenusAPI {
    @Override
    public void registerAction(Action action, JavaPlugin plugin) {
        Menus.getInstance().register(action, plugin.getName());
    }

    @Nullable
    @Override
    public Menu getMenu(String name) {
        return Menus.getInstance().getMenuManager().getByName(name);
    }

    @Nullable
    @Override
    public Session getSession(Player player, String name) {
        if (Menus.getInstance().getSessionManager().getSession(player) != null) {
            return Menus.getInstance().getSessionManager().getSession(player);
        }
        Menu menu = getMenu(name);
        if (menu == null) return null;
        return Menus.getInstance().getSessionManager().startSession(player, menu);
    }
}
