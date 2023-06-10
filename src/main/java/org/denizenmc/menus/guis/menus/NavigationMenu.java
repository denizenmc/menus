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

public class NavigationMenu {
    public void create() {
        Menu menu = Menus.getAPI().createMenuWithBackground(Constants.NAVIGATION_MENU, Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE), 3)
                .setTitle("Menus")
                .setRefreshRateSeconds(60);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        menu.getContent().put(11,
                new Element(MenusUtils.getHead("Hack"),
                        "&b&lWiki", Arrays.asList("&fServer Owner &7and", "&fDeveloper &7Guides", "", "&eClick Here"))
                        .addAction(new SendWikiLinkMenusAction()).addAction(new CloseMenuAction()));
        menu.getContent().put(13,
                new Element(MenusUtils.getHead("zasf"),
                        "&b&lMenus", Arrays.asList("&7Manage all &fMenus", "", "&eClick Here"))
                        .addAction(new LoadMenusAction()).addAction(new ChangeMenuAction().setProperty("menu-name", "menus-menus-list-menu")));
        menu.getContent().put(15,
                new Element(MenusUtils.getHead("MrSnowDK"),
                        "&b&lDonate", Arrays.asList("&7Enjoying the Plugin?", "&fConsider Supporting Me :)", "", "&eClick Here"))
                        .addAction(new SendDonateLinkMenusAction()).addAction(new CloseMenuAction()));
        Menus.getAPI().updateMenu(menu);
    }
}
