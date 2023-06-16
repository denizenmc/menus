package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.BackAction;
import org.denizenmc.menus.guis.actions.EditMenuPropertyAction;

import java.util.Arrays;

public class EditMenuPropertiesMenu {
    public void create() {
        Menu menu = Menus.getAPI().createEmptyMenu(MenusConfiguration.MENU_PROPERTIES_EDIT_MENU, 1)
                .setTitle("Edit Menu Properties")
                .setCollection("Menus Dev")
                .setRefreshRateSeconds(10);
        menu.setCanOpenDirectly(false);
        menu.setHidden(true);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        menu.getContent().put(menu.getRows()*9-9,
                new Element(MenusUtils.getHead(MenusConfiguration.BACK_PLAYER_HEAD),
                        "&bGo Back", Arrays.asList("", "&eClick Here"))
                        .addAction(new BackAction()));
        for (int i = 1; i < menu.getRows()*9; i++) {
            menu.getContent().put(i,
                    new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                            .addAction(new EditMenuPropertyAction()));
        }
        Menus.getAPI().updateMenu(menu);
    }
}
