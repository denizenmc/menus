package org.denizenmc.menus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.actions.*;
import org.denizenmc.menus.guis.menus.NavigationMenu;
import org.denizenmc.menus.integrations.MenusPlaceholderExpansion;
import org.denizenmc.menus.io.IOSource;
import org.denizenmc.menus.io.files.FileIOSource;
import org.denizenmc.menus.listeners.MenusInventoryClickListener;
import org.denizenmc.menus.managers.MenuManager;
import org.denizenmc.menus.managers.SessionManager;
import org.denizenmc.menus.managers.commands.MenusCommandManager;

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
        sessionManager.runTaskTimer(this, 0, 20);
        initCommands();
        initIOSource();
        initListeners();
        initActions();
        registerPlaceholders();
    }

    @Override
    public void onDisable() {
        sessionManager.cancel();
    }

    private void initCommands() {
        getCommand("menus").setExecutor(new MenusCommandManager());
    }

    private void initIOSource() {
        ioSource = new FileIOSource(); // temp
    }

    private void initListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MenusInventoryClickListener(), this);
    }

    private void initActions() {
        api.registerAction(new BackAction(), this);
        api.registerAction(new ChangeMenuAction(), this);
        api.registerAction(new CloseMenuAction(), this);
        api.registerAction(new CommandAction(), this);
        api.registerAction(new NextPageAction(), this);
        api.registerAction(new PreviousPageAction(), this);
        api.registerAction(new TextInputAction(), this);
    }

    private void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MenusPlaceholderExpansion().register();
        }
    }

    private void initMenus() {
        if (getAPI().getMenu(Constants.NAVIGATION_MENU) == null) new NavigationMenu().create();

    }

    public static Menus getInstance() { return instance; }
    public static IMenusAPI getAPI() { return api; }

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
