package org.denizenmc.menus.io;

import org.denizenmc.menus.components.Menu;

import java.util.UUID;

public interface IOSource {
    void createMenu(Menu menu);
    void readMenu(UUID id);
    void updateMenu(Menu menu);
    void deleteMenu(UUID id);
}
