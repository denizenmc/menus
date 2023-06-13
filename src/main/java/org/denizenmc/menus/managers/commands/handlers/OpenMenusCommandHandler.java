package org.denizenmc.menus.managers.commands.handlers;

import org.bukkit.entity.Player;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Menu;

public class OpenMenusCommandHandler extends MenusCommandHandler {
    public OpenMenusCommandHandler(Player player) {
        super(player);
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2 || player == null) return;
        Menu menu = Menus.getAPI().getMenu(args[1]);
        if (menu == null) return;
        if (menu.getPermission() != null && !player.hasPermission(menu.getPermission())) return;
        Menus.getAPI().getSession(player, args[1]).open();
    }
}
