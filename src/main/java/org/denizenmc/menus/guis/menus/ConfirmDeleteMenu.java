package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.BackAction;
import org.denizenmc.menus.components.actions.ChangeMenuAction;
import org.denizenmc.menus.guis.actions.DeleteMenuAction;

import java.util.Arrays;

public class ConfirmDeleteMenu {
    public void create() {
        Menu menu = Menus.getAPI().createMenuWithBackground(MenusConfiguration.CONFIRM_DELETE_MENU, Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE), 3)
                .setTitle("Confirm Delete")
                .setCollection("Menus Dev")
                .setRefreshRateSeconds(60);
        menu.setCanOpenDirectly(false);
        menu.setHidden(true);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        menu.getContent().put(12,
                new Element(MenusUtils.getHead(MenusConfiguration.CLICK_TOGGLED_OFF_HEAD),
                        "&c&lCancel", Arrays.asList("&7Cancel Delete", "", "&eClick Here"))
                        .addAction(new BackAction()));
        menu.getContent().put(14,
                new Element(MenusUtils.getHead(MenusConfiguration.CLICK_TOGGLED_ON_HEAD),
                        "&a&lDelete", Arrays.asList("&7Delete Menu", "", "&eClick Here"))
                        .addAction(new DeleteMenuAction())
                        .addAction(new BackAction()));
        Menus.getAPI().updateMenu(menu);
    }
}
