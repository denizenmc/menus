package org.denizenmc.menus.components.actions;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

public class CommandAction extends Action {

    @Override
    public String getName() {
        return "execute-commands";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fExecute commands.",
                "", "&eInstructions", "&7>> &fEnter commands separated", "&7>> &fby &6':p:'&f or &6':c:'",
                "&7>> &bPlaceholderAPI placeholders &esupported",
                "&7>> &f:p: = Player Executed", "&7>> &f:c: = Console Executed", "",
                "&eExample", "&fweather clear:c:eco give %player_name% 500:c:"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return null;
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
    public boolean isDynamicIcon() {
        return false;
    }

    @Override
    public void onLeftClick(Session session, int count) {

    }

    @Override
    public void onRightClick(Session session, int count) {

    }

    @Override
    public void onShiftLeftClick(Session session, int count) {

    }

    @Override
    public void onShiftRightClick(Session session, int count) {

    }
}
