package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.denizenmc.menus.guis.actions.EditElementAction;

public class EditMenu {
    public Menu create(Session session) {
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return null;
        Menu reference = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        Menu menu = new Menu(MenusConfiguration.MENU_EDIT_MENU).setTitle("Editor - " + reference.getTitle())
                .setCollection("Menus Dev")
                .setRows(reference.getRows())
                .setRefreshRateSeconds(15);
        return setContent(menu);
    }
    private Menu setContent(Menu menu) {
        for (int i = 0; i < menu.getRows()*9; i++) {
            menu.getContent().put(i,
                    new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                            .addAction(new EditElementAction()));
        }
        return menu;
    }
}
