package org.denizenmc.menus.managers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.denizenmc.menus.managers.commands.handlers.NavigationMenusCommandHandler;
import org.jetbrains.annotations.NotNull;

public class MenusCommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            new NavigationMenusCommandHandler((Player) sender).execute(args);
           return true;
        }
        return true;
    }
}
