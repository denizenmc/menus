package org.denizenmc.menus.integrations;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Session;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenusPlaceholderExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "menus";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Denizen";
    }

    @Override
    public @NotNull String getVersion() {
        return Menus.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        switch (identifier) {
            case "page":
                return Menus.getInstance().getSessionManager().getSession(player) == null ?
                        "0" : Menus.getInstance().getSessionManager().getSession(player).getPage()+"";
            case "text":
                Session s = Menus.getInstance().getSessionManager().getSession(player);
                if (s != null) {
                    if (s.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String) {
                        return (String) s.getContext().getValue("menus-text-input", Menus.getInstance());
                    }
                }
                break;
        }
        return "";
    }

}
