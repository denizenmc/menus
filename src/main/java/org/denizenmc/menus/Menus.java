package org.denizenmc.menus;

import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.elements.Element;

import java.util.ArrayList;
import java.util.List;

public final class Menus extends JavaPlugin {
    private static Menus instance;
    private static IMenusAPI api;
    private List<Element> registeredElements = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerElement(Element element) {
        registeredElements.add(element);
    }
}
