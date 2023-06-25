package org.denizenmc.menus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.actions.*;
import org.denizenmc.menus.guis.actions.*;
import org.denizenmc.menus.guis.menus.*;
import org.denizenmc.menus.integrations.MenusPlaceholderExpansion;
import org.denizenmc.menus.io.IOSource;
import org.denizenmc.menus.io.files.FileIOSource;
import org.denizenmc.menus.listeners.MenusInventoryClickListener;
import org.denizenmc.menus.listeners.MenusInventoryCloseListener;
import org.denizenmc.menus.managers.MenuManager;
import org.denizenmc.menus.managers.SessionManager;
import org.denizenmc.menus.managers.commands.MenusCommandManager;
import org.denizenmc.menus.managers.commands.MenusTabHandler;
import org.denizenmc.menus.services.SynchronousMenusTaskService;

import java.util.*;

public final class Menus extends JavaPlugin {
    private static Menus instance;
    private static IMenusAPI api;
    private IOSource ioSource;
    private MenuManager menuManager = new MenuManager();
    private SessionManager sessionManager = new SessionManager();
    private SynchronousMenusTaskService taskService = new SynchronousMenusTaskService();
    private Map<String, List<Action>> registeredActions = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        api = new MenusAPI();
        sessionManager.runTaskTimer(this, 0, 20);
        initCommands();
        initIOSource();
        initListeners();
        initActions();
        registerPlaceholders();
        initMenus();
        taskService.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        sessionManager.cancel();
    }

    private void initCommands() {
        getCommand("menus").setExecutor(new MenusCommandManager());
        getCommand("menus").setTabCompleter(new MenusTabHandler());
    }

    private void initIOSource() {
        ioSource = new FileIOSource(); // temp
    }

    private void initListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MenusInventoryClickListener(), this);
        pm.registerEvents(new MenusInventoryCloseListener(), this);
    }

    private void initActions() {
        api.registerAction(new BackAction(), this);
        api.registerAction(new ChangeMenuAction(), this);
        api.registerAction(new CloseMenuAction(), this);
        api.registerAction(new CommandAction(), this);
        api.registerAction(new NextPageAction(), this);
        api.registerAction(new PreviousPageAction(), this);
        api.registerAction(new TextInputAction(), this);
    }

    private void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MenusPlaceholderExpansion().register();
        }
    }

    private void initMenus() {
        // init custom actions
        getAPI().registerAction(new EditMenuAction(), Menus.getInstance());
        getAPI().registerAction(new SendDonateLinkMenusAction(), Menus.getInstance());
        getAPI().registerAction(new SendWikiLinkMenusAction(), Menus.getInstance());
        getAPI().registerAction(new EditElementAction(), Menus.getInstance());
        getAPI().registerAction(new EditElementDescriptionLineAction(), Menus.getInstance());
        getAPI().registerAction(new AddElementDescriptionLineAction(), Menus.getInstance());
        getAPI().registerAction(new SendWikiLinkMenusAction(), Menus.getInstance());
        getAPI().registerAction(new RemoveElementAction(), Menus.getInstance());
        getAPI().registerAction(new EditElementActionsAction(), Menus.getInstance());
        getAPI().registerAction(new EditElementActionPropertyAction(), Menus.getInstance());
        getAPI().registerAction(new AddElementActionAction(), Menus.getInstance());
        getAPI().registerAction(new SelectElementActionAction(), Menus.getInstance());
        getAPI().registerAction(new EditElementActionClickAction(), Menus.getInstance());
        getAPI().registerAction(new RemoveActionAction(), Menus.getInstance());
        getAPI().registerAction(new CreateMenuAction(), Menus.getInstance());
        getAPI().registerAction(new EditMenuPropertyAction(), Menus.getInstance());
        getAPI().registerAction(new DeleteMenuAction(), Menus.getInstance());
        if (getAPI().getMenu(MenusConfiguration.NAVIGATION_MENU) == null) new NavigationMenu().create();
        if (getAPI().getMenu(MenusConfiguration.MENUS_LIST_MENU) == null) new MenusListMenu().create();
        if (getAPI().getMenu(MenusConfiguration.ELEMENT_DESCRIPTION_EDIT_MENU) == null) new EditElementDescriptionMenu().create();
        if (getAPI().getMenu(MenusConfiguration.ELEMENT_ACTIONS_EDIT_MENU) == null) new EditElementActionsMenu().create();
        if (getAPI().getMenu(MenusConfiguration.ELEMENT_ACTION_PROPERTY_EDIT_MENU) == null) new EditElementActionPropertiesMenu().create();
        if (getAPI().getMenu(MenusConfiguration.ELEMENT_ACTION_SELECT_MENU) == null) new SelectElementActionMenu().create();
        if (getAPI().getMenu(MenusConfiguration.ELEMENT_ACTION_CLICK_EDIT_MENU) == null) new EditElementActionClicksMenu().create();
        if (getAPI().getMenu(MenusConfiguration.MENU_PROPERTIES_EDIT_MENU) == null) new EditMenuPropertiesMenu().create();
        if (getAPI().getMenu(MenusConfiguration.CONFIRM_DELETE_MENU) == null) new ConfirmDeleteMenu().create();
    }

    public static Menus getInstance() { return instance; }
    public static IMenusAPI getAPI() { return api; }

    public void register(Action action, String plugin) {
        if (registeredActions.containsKey(plugin)) registeredActions.get(plugin).add(action);
        else {
            registeredActions.put(plugin, new ArrayList<>(Arrays.asList(action)));
        }
    }

    public List<Action> getRegisteredActions(String filter) {
        List<Action> allActions = new ArrayList<>();
        for (String plugin : registeredActions.keySet()) {
            if (filter == null) {
                for (Action a : registeredActions.get(plugin)) {
                    if (!a.isHidden()) allActions.add(a);
                }
            } else {
                if (filter.contains("plugin=") && filter.indexOf("plugin=")+7 < filter.length()) {
                    if (plugin.equalsIgnoreCase(filter.substring(filter.indexOf("plugin=")+7))) {
                        for (Action a : registeredActions.get(plugin)) {
                            if (!a.isHidden()) allActions.add(a);
                        }
                    }
                } else if (filter.contains("name=") && filter.indexOf("name=")+5 < filter.length()) {
                    for (Action a : registeredActions.get(plugin)) {
                        if (!a.isHidden() && a.getName().equals(filter.substring(filter.indexOf("name=")+5))) allActions.add(a);
                    }
                } else {
                    for (Action a : registeredActions.get(plugin)) {
                        if (!a.isHidden()) allActions.add(a);
                    }
                }
            }
        }
        Collections.sort(allActions);
        return allActions;
    }

    public String getPluginFromAction(Action a) {
        for (String plugin : registeredActions.keySet()) {
            if (registeredActions.get(plugin).contains(a)) return plugin;
        }
        return null;
    }

    public Action getFromName(String name) {
        for (String plugin : registeredActions.keySet()) {
            List<Action> actions = new ArrayList<>(registeredActions.get(plugin));
            for (Action a : actions) if (a.getName().equalsIgnoreCase(name)) return a.copy();
        }
        return null;
    }

    public IOSource getIOSource() { return ioSource; }
    public MenuManager getMenuManager() { return menuManager; }
    public SessionManager getSessionManager() { return sessionManager; }
    public SynchronousMenusTaskService getTaskService() { return taskService; }
}
