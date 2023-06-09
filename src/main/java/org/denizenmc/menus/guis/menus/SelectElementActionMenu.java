package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.BackAction;
import org.denizenmc.menus.components.actions.NextPageAction;
import org.denizenmc.menus.components.actions.PreviousPageAction;
import org.denizenmc.menus.components.actions.TextInputAction;
import org.denizenmc.menus.guis.actions.RemoveElementAction;
import org.denizenmc.menus.guis.actions.SelectElementActionAction;

import java.util.Arrays;

public class SelectElementActionMenu {
    public void create() {
        Menu menu = Menus.getAPI().createEmptyMenu(MenusConfiguration.ELEMENT_ACTION_SELECT_MENU, 3)
                .setTitle("Select Action")
                .setCollection("Menus Dev")
                .setRefreshRateSeconds(300);
        menu.setCanOpenDirectly(false);
        menu.setHidden(true);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        for (int i = 0; i < menu.getRows()*9-9; i++) {
            menu.getContent().put(i, new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                    .addAction(new SelectElementActionAction()));
        }
        for (int i = menu.getRows()*9-9; i < menu.getRows()*9; i++) {
            menu.getContent().put(i,
                    new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE)));
        }
        menu.getContent().put(18,
                new Element(MenusUtils.getHead(MenusConfiguration.BACK_PLAYER_HEAD),
                        "&bGo Back", Arrays.asList("", "&eClick Here"))
                        .addAction(new BackAction()).addAction(new RemoveElementAction()));
        menu.getContent().put(21,
                new Element(MenusUtils.getHead(MenusConfiguration.PREVIOUS_PAGE_PLAYER_HEAD),
                        "&bPrevious Page", Arrays.asList("&fCurrent Page: &7(%menus_page%)", "", "&eClick Here"))
                        .addAction(new PreviousPageAction()));
        menu.getContent().put(23,
                new Element(MenusUtils.getHead(MenusConfiguration.NEXT_PAGE_PLAYER_HEAD),
                        "&bNext Page", Arrays.asList("&fCurrent Page: &7(%menus_page%)", "", "&eClick Here"))
                        .addAction(new NextPageAction()));
        menu.getContent().put(26,
                new Element(MenusUtils.getHead(MenusConfiguration.TEXT_INPUT_PLAYER_HEAD),
                        "&bFilter", Arrays.asList("&fUse 'plugin=' or 'name='","","&eCurrent Filter", "&f%menus_text%",
                        "", "&eExample", "&fplugin=Menus", "", "&eLeft-Click: &fEdit", "&eShift-Right-Click: &cClear"))
                        .addAction(new TextInputAction().setProperty("placeholder-text", "Search...")
                                .setProperty("title-text", "Filter").setProperty("item-material", "PAPER")
                                .setProperty("item-display-name", "&bFilter").setProperty("item-description", "")));
        Menus.getAPI().updateMenu(menu);
    }
}
