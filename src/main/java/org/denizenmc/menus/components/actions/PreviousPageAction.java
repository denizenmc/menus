package org.denizenmc.menus.components.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

public class PreviousPageAction extends Action {

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getName() {
        return "menus-previous-page";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fDecrease the page count by 1."));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "MHF_ArrowDown";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
    }

    @Override
    public Action copy() {
        return new PreviousPageAction();
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
        if (session.getPage() > 1) {
            session.setPage(session.getPage()-1);
            session.refresh();
        }
    }
}
