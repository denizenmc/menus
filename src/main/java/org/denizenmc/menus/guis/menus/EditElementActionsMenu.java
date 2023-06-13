package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.BackAction;
import org.denizenmc.menus.components.actions.NextPageAction;
import org.denizenmc.menus.components.actions.PreviousPageAction;
import org.denizenmc.menus.guis.actions.AddElementDescriptionLineAction;
import org.denizenmc.menus.guis.actions.EditElementActionsAction;
import org.denizenmc.menus.guis.actions.RemoveElementAction;

import java.util.Arrays;

public class EditElementActionsMenu {
    public void create() {
        Menu menu = Menus.getAPI().createEmptyMenu(MenusConfiguration.ELEMENT_DESCRIPTION_EDIT_MENU, 3)
                .setTitle("Edit Element Actions")
                .setCollection("Menus Dev")
                .setRefreshRateSeconds(15);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        for (int i = 0; i < menu.getRows()*9-9; i++) {
            menu.getContent().put(i, new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                    .addAction(new EditElementActionsAction()));
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
        menu.getContent().put(22,
                new Element(new ItemStack(Material.PAPER),
                        "&b&lAdd Line", Arrays.asList("&7Add an &fAction", "", "&eClick Here"))
                        .addAction(new AddElementDescriptionLineAction()));
        menu.getContent().put(23,
                new Element(MenusUtils.getHead(MenusConfiguration.NEXT_PAGE_PLAYER_HEAD),
                        "&bNext Page", Arrays.asList("&fCurrent Page: &7(%menus_page%)", "", "&eClick Here"))
                        .addAction(new NextPageAction()));
        Menus.getAPI().updateMenu(menu);
    }
}
