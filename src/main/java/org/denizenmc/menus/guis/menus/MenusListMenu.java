package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.*;
import org.denizenmc.menus.guis.actions.EditMenuAction;
import org.denizenmc.menus.guis.actions.SendWikiLinkMenusAction;

import java.util.Arrays;

public class MenusListMenu {
    public void create() {
        Menu menu = Menus.getAPI().createEmptyMenu(MenusConfiguration.MENUS_LIST_MENU, 6)
                .setTitle("Manage Menus")
                .setCollection("Menus Dev")
                .setRefreshRateSeconds(20);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        for (int i = 0; i < menu.getRows()*9-9; i++) {
            menu.getContent().put(i,
                    new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                            .addAction(new EditMenuAction()));
        }
        for (int i = menu.getRows()*9-9; i < menu.getRows()*9; i++) {
            menu.getContent().put(i,
                    new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE)));
        }
        menu.getContent().put(menu.getRows()*9-5,
                new Element(MenusUtils.getHead(MenusConfiguration.MENUS_PLAYER_HEAD),
                        "&b&lCreate Menu", Arrays.asList("&fCreate a new menu", "", "&eClick Here"))
                        .addAction(new SendWikiLinkMenusAction()).addAction(new CloseMenuAction()));
        menu.getContent().put(menu.getRows()*9-6,
                new Element(MenusUtils.getHead(MenusConfiguration.PREVIOUS_PAGE_PLAYER_HEAD),
                        "&bPrevious Page", Arrays.asList("&fCurrent Page: &7(%menus_page%)", "", "&eClick Here"))
                        .addAction(new PreviousPageAction()));
        menu.getContent().put(menu.getRows()*9-4,
                new Element(MenusUtils.getHead(MenusConfiguration.NEXT_PAGE_PLAYER_HEAD),
                        "&bNext Page", Arrays.asList("&fCurrent Page: &7(%menus_page%)", "", "&eClick Here"))
                        .addAction(new NextPageAction()));
        menu.getContent().put(menu.getRows()*9-9,
                new Element(MenusUtils.getHead(MenusConfiguration.BACK_PLAYER_HEAD),
                        "&bGo Back", Arrays.asList("", "&eClick Here"))
                        .addAction(new BackAction()));
        menu.getContent().put(menu.getRows()*9-1,
                new Element(MenusUtils.getHead(MenusConfiguration.TEXT_INPUT_PLAYER_HEAD),
                        "&bFilter", Arrays.asList("&fUse 'collection=' or 'name='","","&eCurrent Filter", "&f%menus_text%",
                        "", "&eExample", "&fcollection=Menus", "", "&eLeft-Click: &fEdit", "&eShift-Right-Click: &cClear"))
                        .addAction(new TextInputAction().setProperty("placeholder-text", "Search...")
                                .setProperty("title-text", "Filter").setProperty("item-material", "PAPER")
                                .setProperty("item-display-name", "&bFilter").setProperty("item-description", "")));
        Menus.getAPI().updateMenu(menu);
    }
}
