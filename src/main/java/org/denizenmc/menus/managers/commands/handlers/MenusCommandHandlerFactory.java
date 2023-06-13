package org.denizenmc.menus.managers.commands.handlers;

import org.bukkit.entity.Player;

public class MenusCommandHandlerFactory {
    public MenusCommandHandler getHandler(String command, Player player) {
        switch (command) {
            case "open":
                return new OpenMenusCommandHandler(player);
        }
        return null;
    }
}
