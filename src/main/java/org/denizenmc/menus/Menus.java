package org.denizenmc.menus;

import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.elements.Element;
import org.denizenmc.menus.io.IOSource;
import org.denizenmc.menus.io.files.FileIOSource;
import org.denizenmc.menus.managers.MenuManager;

import java.util.ArrayList;
import java.util.List;

public final class Menus extends JavaPlugin {
    private static Menus instance;
    private static IMenusAPI api;
    private IOSource ioSource;
    private MenuManager menuManager = new MenuManager();
    private List<Action> registeredActions = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        initIOSource();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initIOSource() {
        ioSource = new FileIOSource(); // temp
    }

    public static Menus getInstance() { return instance; }

    public void register(Action action) {
        registeredActions.add(action);
    }

    public Action getFromName(String name) {
        for (Action a : new ArrayList<>(registeredActions)) if (a.getName().equalsIgnoreCase(name)) return a.copy();
        return null;
    }

    public IOSource getIOSource() { return ioSource; }
}
