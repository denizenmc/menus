package org.denizenmc.menus.managers;

import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuManager implements Manager<Menu> {
    private List<Menu> menus = new ArrayList<>();

    @Override
    public void add(Menu component) {
        if (!menus.contains(component)) menus.add(component);
    }

    @Override
    public void remove(Menu component) {
        menus.remove(component);
    }

    @Override
    public Menu create() {
        Menu newMenu = new Menu();
        menus.add(newMenu);
        return newMenu;
    }

    @Override
    public void remove(UUID id) {
        menus.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public void init() {

    }

    @Override
    public Menu getById(UUID id) {
        for (Menu m : new ArrayList<>(menus)) {
            if (m.getId().equals(id)) return m;
        }
        return null;
    }

    @Override
    public Menu getByName(String name) {
        for (Menu m : new ArrayList<>(menus)) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    @Override
    public List<Menu> getList(Query query) {
        return null;
    }

    @Override
    public String getName() {
        return "Menus";
    }

    @Override
    public void saveToFiles() {

    }
}
