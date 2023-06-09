package org.denizenmc.menus.components.actions;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class BackAction extends Action {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getIconPlayerHeadName() {
        return null;
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return null;
    }

    @Override
    public Action copy() {
        return new BackAction();
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
