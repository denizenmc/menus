package org.denizenmc.menus.managers.commands.handlers;

import org.bukkit.entity.Player;

public abstract class MenusCommandHandler {
    protected Player player;
    public MenusCommandHandler(Player player) { this.player = player; }
    public abstract void execute(String [] args);
}
