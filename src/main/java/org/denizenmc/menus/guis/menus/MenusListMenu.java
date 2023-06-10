package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.Constants;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.ChangeMenuAction;
import org.denizenmc.menus.components.actions.CloseMenuAction;
import org.denizenmc.menus.guis.actions.LoadMenusAction;
import org.denizenmc.menus.guis.actions.SendDonateLinkMenusAction;
import org.denizenmc.menus.guis.actions.SendWikiLinkMenusAction;

import java.util.Arrays;

public class MenusListMenu {
    public void create() {
        Menu menu = Menus.getAPI().createEmptyMenu(Constants.MENUS_LIST_MENU, 6)
                .setTitle("Manage Menus")
                .setRefreshRateSeconds(10);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        for (int i = menu.getRows()*9-9; i < menu.getRows()*9; i++) {
            menu.getContent().put(i,
                    new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE)));
        }
        // start here
        menu.getContent().put(11,
                new Element(MenusUtils.getHead("Hack"),
                        "&b&lWiki", Arrays.asList("&fServer Owner &7and", "&fDeveloper &7Guides", "", "&eClick Here"))
                        .addAction(new SendWikiLinkMenusAction()).addAction(new CloseMenuAction()));
        Menus.getAPI().updateMenu(menu);
    }
}
