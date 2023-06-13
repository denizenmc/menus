package org.denizenmc.menus.components.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

public class BackAction extends Action {

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getName() {
        return "menus-back";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fReturn to the previous menu."));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "MHF_ArrowLeft";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
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
    public void onBuild(Session session, int count) {

    }

    @Override
    public boolean isDynamicIcon() {
        return false;
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        session.pop();
        session.open();
    }
}
