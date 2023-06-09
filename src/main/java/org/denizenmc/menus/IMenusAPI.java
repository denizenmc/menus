package org.denizenmc.menus;

import org.denizenmc.menus.components.Element;

public interface IMenusAPI {
    /**
     * Register a custom element that can live in a menu.
     * @param element Element to be registered.
     */
    void registerElement(Element element);
}
