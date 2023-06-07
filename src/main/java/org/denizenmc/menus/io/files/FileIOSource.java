package org.denizenmc.menus.io.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.io.IOSource;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class FileIOSource implements IOSource {

    @Override
    public void createMenu(Menu menu) {
        FileUtils.createDirectory(FileUtils.getFile(Arrays.asList("menus")));
        File file = FileUtils.getFile(Arrays.asList("menus", menu.getId().toString()+".yml"));
        FileUtils.createFile(file);
        updateMenu(menu);
    }

    @Override
    public void readMenu(UUID id) {

    }

    @Override
    public void updateMenu(Menu menu) {
        File file = FileUtils.getFile(Arrays.asList("menus", menu.getId().toString()+".yml"));
        if (!file.exists()) createMenu(menu);
        FileConfiguration cfg = FileUtils.loadYML(file);

    }

    @Override
    public void deleteMenu(UUID id) {

    }
}
