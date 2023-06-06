package org.denizenmc.menus.elements;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

public class CommandElement extends Element {

    public CommandElement(int refreshRateTicks) {
        super(refreshRateTicks);
    }

    @Override
    ItemStack build(int count) {
        return null;
    }

    @Override
    ItemStack getIcon() {
        return null;
    }

    @Override
    void onLeftClick(Session session, int count) {

    }

    @Override
    void onRightClick(Session session, int count) {

    }

    @Override
    void onShiftLeftClick(Session session, int count) {

    }

    @Override
    void onShiftRightClick(Session session, int count) {

    }
}
