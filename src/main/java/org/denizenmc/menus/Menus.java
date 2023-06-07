package org.denizenmc.menus;

import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.elements.Element;
import org.denizenmc.menus.managers.MenuManager;

import java.util.ArrayList;
import java.util.List;

public final class Menus extends JavaPlugin {
    private static Menus instance;
    private static IMenusAPI api;
    private MenuManager menuManager = new MenuManager();
    private List<Element> registeredElements = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        initManagers();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initManagers() {
        menuManager.init();
    }

    public static Menus getInstance() { return instance; }

    public void registerElement(Element element) {
        registeredElements.add(element);
    }
}
