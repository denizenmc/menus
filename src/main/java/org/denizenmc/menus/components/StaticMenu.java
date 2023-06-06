package org.denizenmc.menus.components;

import org.denizenmc.menus.elements.Element;

import java.util.Map;

public class StaticMenu implements IMenu {

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<Integer, Element> getContents() {
        return null;
    }
}
