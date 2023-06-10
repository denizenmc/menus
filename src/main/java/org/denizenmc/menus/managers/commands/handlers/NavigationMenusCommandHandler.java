package org.denizenmc.menus.managers.commands.handlers;

import org.bukkit.entity.Player;
import org.denizenmc.menus.Constants;
import org.denizenmc.menus.Menus;

public class NavigationMenusCommandHandler extends MenusCommandHandler {

    public NavigationMenusCommandHandler(Player player) {
        super(player);
    }

    @Override
    public void execute(String[] args) {
        if (player != null && !player.hasPermission("menus.admin")) return;
        if (player != null) {
            Menus.getAPI().getSession(player, Constants.NAVIGATION_MENU).open();
        }
    }
}
