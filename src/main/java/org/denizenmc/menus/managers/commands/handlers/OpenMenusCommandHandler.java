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
        if (args.length < 2 || player == null) return;
        Menu menu = Menus.getAPI().getMenu(getMenuName(args));
        if (menu == null || !menu.isCanOpenDirectly()) return;
        if (menu.getPermission() != null && !player.hasPermission(menu.getPermission())) return;
        Menus.getAPI().getSession(player, args[1]).open();
    }
    private String getMenuName(String [] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i < args.length-1) {
                builder.append(args[i] + " ");
            } else {
                builder.append(args[i]);
            }
        }
        return builder.toString();
    }
}
