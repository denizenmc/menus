package org.denizenmc.menus.io.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.denizenmc.menus.Menus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class FileUtils {
    public static void createFile(File file) {
        if (file == null) {
            //ProceduralLogger.log(Level.INFO, "File is null");
            return;
        }
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            //ProceduralLogger.log(Level.SEVERE, "Could not create file");
        }
    }

    public static void createDirectory(File file) {
        if (file == null) {
            //ProceduralLogger.log(Level.INFO, "File is null");
            return;
        }
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static void saveYML(FileConfiguration cfg, File file) {
        try {
            cfg.save(file);
        } catch (IOException e) {
            //ProceduralLogger.log(Level.SEVERE, "Could not save YML");
        }
    }

    public static void clear(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            if (directory.listFiles().length == 0) directory.delete();
            else {
                for (File file : directory.listFiles()) clear(file);
                directory.delete();
            }
        } else { directory.delete(); }
    }

    public static void clearDeleted(File directory, List<String> possibleFiles) {
        if (directory != null && directory.isDirectory()) {
            List<File> toDelete = new ArrayList<>();
            for (File f : directory.listFiles()) {
                if (!possibleFiles.contains(f.getName().split(Pattern.quote("."))[0])) toDelete.add(f);
            }
            for (File f : toDelete) f.delete();
        }
    }

    public static FileConfiguration loadYML(File file) {
        if (file == null || !file.exists() || file.isDirectory()) return null;
        return YamlConfiguration.loadConfiguration(file);
    }

    public static File getPluginFolderPath() {
        return Menus.getInstance().getDataFolder();
    }

    public static File getFile(List<String> files) {
        File f = getPluginFolderPath();
        for (String file : files) {
            f = new File(f, file);
        }
        return f;
    }
}
