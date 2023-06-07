package org.denizenmc.menus.components.actions;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.List;

public class CommandAction extends Action {

    @Override
    String getName() {
        return null;
    }

    @Override
    List<String> getDescription() {
        return null;
    }

    @Override
    String getIconPlayerHeadName() {
        return null;
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        return null;
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
