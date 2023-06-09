package org.denizenmc.menus.managers;

import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MenuManager implements Manager<Menu> {

    @Override
    public void create(Menu component) {
        if (!Menus.getInstance().getIOSource().exists(component)) {
            Menus.getInstance().getIOSource().create(component);
        }
    }

    @Override
    public void remove(Menu component) {
        Menus.getInstance().getIOSource().delete(component);
    }

    @Override
    public void remove(UUID id) {
        if (id == null) return;
        List<ISerializable> menus = Menus.getInstance().getIOSource().read(new Query().setId(id.toString()));
        if (!menus.isEmpty()) {
            if (menus.get(0) instanceof Menu) {
                remove((Menu) menus.get(0));
            }
        }
    }

    @Override
    public Menu getById(UUID id) {
        List<ISerializable> menus = Menus.getInstance().getIOSource().read(new Query().setId(id.toString()));
        if (!menus.isEmpty() && menus.get(0) instanceof Menu) return ((Menu) menus.get(0));
        return null;
    }

    @Override
    public Menu getByName(String name) {
        List<ISerializable> menus = Menus.getInstance().getIOSource().read(new Query().setName(name));
        if (!menus.isEmpty() && menus.get(0) instanceof Menu) return ((Menu) menus.get(0));
        return null;
    }

    @Override
    public List<Menu> getList(Query query) {
        List<ISerializable> menus = Menus.getInstance().getIOSource().read(query);
        List<Menu> list = new ArrayList<>();
        for (ISerializable serializable : menus) {
            if (serializable instanceof Menu) list.add((Menu) serializable);
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public String getName() {
        return "Menus";
    }
}
