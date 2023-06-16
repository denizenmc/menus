package org.denizenmc.menus.guis.menus;

import org.bukkit.Material;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.actions.BackAction;
import org.denizenmc.menus.guis.actions.EditElementActionClickAction;
import org.denizenmc.menus.guis.actions.RemoveActionAction;

import java.util.Arrays;

public class EditElementActionClicksMenu {
    public void create() {
        Menu menu = Menus.getAPI().createEmptyMenu(MenusConfiguration.ELEMENT_ACTION_CLICK_EDIT_MENU, 1)
                .setTitle("Edit Action Clicks")
                .setCollection("Menus Dev")
                .setRefreshRateSeconds(15);
        menu.setCanOpenDirectly(false);
        menu.setHidden(true);
        setContent(menu);
    }

    private void setContent(Menu menu) {
        for (int i = 0; i < menu.getRows()*9-9; i++) {
            menu.getContent().put(i, new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE)));
        }
        menu.getContent().put(0,
                new Element(MenusUtils.getHead(MenusConfiguration.BACK_PLAYER_HEAD),
                        "&bGo Back", Arrays.asList("", "&eClick Here"))
                        .addAction(new BackAction()).addAction(new RemoveActionAction()));
        menu.getContent().put(2,
                new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                        .addAction(new EditElementActionClickAction()));
        menu.getContent().put(4,
                new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                        .addAction(new EditElementActionClickAction()));
        menu.getContent().put(6,
                new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                        .addAction(new EditElementActionClickAction()));
        menu.getContent().put(8,
                new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE))
                        .addAction(new EditElementActionClickAction()));
        Menus.getAPI().updateMenu(menu);
    }
}
