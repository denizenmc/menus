package org.denizenmc.menus.managers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.denizenmc.menus.Menus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenusTabHandler implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> options = new ArrayList<>();
        switch (args.length) {
            case 1:
                options.addAll(Arrays.asList("open"));
                break;
            case 2:
                if (args[0].equalsIgnoreCase("open")) {
                    options.addAll(Menus.getInstance().getMenuManager().getMenuNames());
                }
                break;
        }
        return options;
    }
}
