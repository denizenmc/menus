package org.denizenmc.menus.guis.actions;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.actions.ChangeMenuAction;
import org.denizenmc.menus.components.actions.TextInputAction;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EditElementAction extends Action {

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-edit-element (DEV)";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit Element of a Menu.","", "&7* Dynamic"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "Hack";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
    }

    @Override
    public Action copy() {
        return new EditElementAction();
    }

    @Override
    public boolean isDynamicIcon() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return null;
        Menu reference = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        if (reference == null) return getNoElementIcon().build(session, new HashMap<>());
        if (count <= session.getMenu().getRows()*9) {
            if (!reference.getContent().containsKey(count-1)) return getNoElementIcon().build(session, new HashMap<>());
            else {
                Element e = reference.getContent().get(count-1);
                if (e == null) {
                    return getNoElementIcon().build(session, new HashMap<>());
                }
                if (!e.getActions().isEmpty()) {
                    for (Action a : new ArrayList<>(e.getActions())) {
                        if (a.isDynamicIcon()) {
                            ItemStack icon = a.getIcon();
                            return new Element(icon,
                                    "&b"+a.getName(), getEditElementDescription(e)).build(session, new HashMap<>());
                        }
                    }
                }
                return new Element(e.build(session, new HashMap<>()),
                        e.getName(), getEditElementDescription(e)).build(session, new HashMap<>());
            }
        }
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {
        if (count == 1) {
            if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
            if (!(session.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String)) return;
            Menu m = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
            if (m == null) return;
            if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance()) instanceof Element)) return;
            Element e = (Element) session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance());
            if (e == null) return;

            if (session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT_NAME, Menus.getInstance()) instanceof String) {
                String name = (String) session.getContext().getValue("menus-text-input", Menus.getInstance());
                if (name != null)  {
                    e.setName(name);
                    Menus.getAPI().updateMenu(m);
                }
                session.getContext().remove(MenusContextKeys.ELEMENT_TO_EDIT_NAME, Menus.getInstance());
                session.getContext().remove("menus-text-input", Menus.getInstance());
            }
            session.getContext().remove(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance());
        }
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
        Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        if (menu == null) return;

        // Check clicking with item
        if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
            if (menu.getContent().containsKey(event.getSlot())) {
                Element e = menu.getContent().get(event.getSlot());
                if (e != null) {
                    e.setItem(new ItemStack(event.getCursor()));
                }
            } else {
                menu.getContent().put(event.getSlot(), new Element(new ItemStack(event.getCursor())));
            }
            update(session, menu);
        }

        // Edit Actions
        if (event.getClick().equals(ClickType.LEFT)) {
            if (menu.getContent().containsKey(event.getSlot())) {
                session.getContext().setValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance(), menu.getContent().get(event.getSlot()));
                new ChangeMenuAction().setProperty("menu-name",
                        MenusConfiguration.ELEMENT_ACTIONS_EDIT_MENU).onClick(session, count, event);
            } else {
                menu.getContent().put(event.getSlot(), new Element(Menus.getAPI().getBackgroundItemFromMaterial(Material.GRAY_STAINED_GLASS_PANE)));
                update(session, menu);
            }
        } else if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
            menu.getContent().remove(event.getSlot());
            update(session, menu);
        } else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
            if (menu.getContent().containsKey(event.getSlot())) {
                session.getContext().setValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance(), menu.getContent().get(event.getSlot()));
                session.getContext().setValue(MenusContextKeys.ELEMENT_TO_EDIT_NAME, Menus.getInstance(), menu.getContent().get(event.getSlot()).getName());
                TextInputAction input = (TextInputAction) new TextInputAction()
                        .setProperty("placeholder-text",
                        menu.getContent().get(event.getSlot()).getName())
                        .setProperty("title-text", "Edit Element Name")
                        .setProperty("item-material", "NAME_TAG")
                        .setProperty("item-display-name", menu.getContent().get(event.getSlot()).getName())
                        .setProperty("item-description", "");
                input.openEditor(session);
            }
        } else if (event.getClick().equals(ClickType.RIGHT)) {
            session.getContext().setValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance(), menu.getContent().get(event.getSlot()));
            new ChangeMenuAction().setProperty("menu-name", MenusConfiguration.ELEMENT_DESCRIPTION_EDIT_MENU).onClick(session, count, event);
        }
        event.setCancelled(true);
    }

    private Element getNoElementIcon() {
        return new Element(new ItemStack(Material.BARRIER),
                "&cNo Element", Arrays.asList("&7Click to Add Element"));
    }

    private List<String> getEditElementDescription(Element e) {
        List<String> description = new ArrayList<>(Arrays.asList("", "&7-------------------------------", "&fActions", ""));
        for (Action ac : new ArrayList<>(e.getActions())) {
            description.add("&7>> &e"+ac.getName());
        }
        description.addAll(Arrays.asList("",
                "&eClick w/Item on Cursor: &fSet Item",
                "&eLeft-Click: &fEdit Actions",
                "&eShift-Left-Click: &fEdit Display Name",
                "&eRight-Click: &fEdit Description",
                "&eShift-Right-Click: &cRemove Element"));
        return description;
    }

    private void update(Session session, Menu menu) {
        Menus.getAPI().updateMenu(menu);
        session.refresh();
    }
}
