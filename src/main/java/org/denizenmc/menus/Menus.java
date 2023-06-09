package org.denizenmc.menus;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.io.IOSource;
import org.denizenmc.menus.io.files.FileIOSource;
import org.denizenmc.menus.listeners.MenusInventoryClickListener;
import org.denizenmc.menus.managers.MenuManager;
import org.denizenmc.menus.managers.SessionManager;

import java.util.*;

public final class Menus extends JavaPlugin {
    private static Menus instance;
    private static IMenusAPI api;
    private IOSource ioSource;
    private MenuManager menuManager = new MenuManager();
    private SessionManager sessionManager = new SessionManager();
    private Map<String, List<Action>> registeredActions = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        api = new MenusAPI();
        initIOSource();
        initListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initIOSource() {
        ioSource = new FileIOSource(); // temp
    }

    public void initListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MenusInventoryClickListener(), this);
    }

    public static Menus getInstance() { return instance; }
    public static IMenusAPI getApi() { return api; }

    public void register(Action action, String plugin) {
        if (registeredActions.containsKey(plugin)) registeredActions.get(plugin).add(action);
        else {
            registeredActions.put(plugin, new ArrayList<>(Arrays.asList(action)));
        }
    }

    public Action getFromName(String name) {
        for (String plugin : registeredActions.keySet()) {
            List<Action> actions = new ArrayList<>(registeredActions.get(plugin));
            for (Action a : actions) if (a.getName().equalsIgnoreCase(name)) return a.copy();
        }
        return null;
    }

    public IOSource getIOSource() { return ioSource; }
    public MenuManager getMenuManager() { return menuManager; }
    public SessionManager getSessionManager() { return sessionManager; }
}
