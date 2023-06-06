package org.denizenmc.menus.components;

import org.denizenmc.menus.elements.Element;

import java.util.Map;

public interface IMenu {
    int getSize();
    String getName();
    Map<Integer, Element> getContents();
}
