package org.denizenmc.menus.components.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

public class CommandAction extends Action {

    @Override
    public String getName() {
        return "menus-execute-commands";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fExecute commands.",
                "", "&eInstructions", "&7>> &fEnter commands separated", "&7>> &fby &6':p:'&f or &6':c:'",
                "&7>> &bPlaceholderAPI placeholders &esupported",
                "&7>> &f:p: = Player Executed", "&7>> &f:c: = Console Executed", "",
                "&eExamples", "&fweather clear:c:eco give %player_name% 500:c:",
                "&fweather clear:c:"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "ExtrayeaMC";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("commands", "");
        return properties;
    }

    @Override
    public Action copy() {
        return new CommandAction();
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {

    }

    @Override
    public boolean isDynamicIcon() {
        return false;
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        String c = getProperties().get("commands");
        if (c == null) return;
        List<String> commandsPlayer = new ArrayList<>();
        List<String> commandsConsole = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < c.length()-2; i++) {
            if (c.charAt(i) == ':') {
                if (c.charAt(i+1) == 'p' && c.charAt(i+2) == ':') {
                    commandsPlayer.add(c.substring(start, i));
                    start = i+3;
                } else if (c.charAt(i+1) == 'c' && c.charAt(i+2) == ':') {
                    commandsConsole.add(c.substring(start, i));
                    start = i+3;
                }
            }
        }
        for (String command : commandsPlayer) {
            command = PlaceholderAPI.setPlaceholders(session.getPlayer(), command);
            Bukkit.dispatchCommand(session.getPlayer(), command);
        }
        for (String command : commandsConsole) {
            command = PlaceholderAPI.setPlaceholders(session.getPlayer(), command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
