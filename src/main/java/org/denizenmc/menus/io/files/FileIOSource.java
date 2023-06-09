package org.denizenmc.menus.io.files;
import org.bukkit.configuration.file.FileConfiguration;
import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Query;
import org.denizenmc.menus.io.EntityType;
import org.denizenmc.menus.io.IOSource;
import org.denizenmc.menus.io.files.proxy.FileProxyFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileIOSource implements IOSource {

    @Override
    public void create(ISerializable serializable) {
        // create record
        FileUtils.createDirectory(FileUtils.getFile(Arrays.asList(serializable.getDirectoryName())));
        File file = FileUtils.getFile(serializable.getPath());
        FileUtils.createFile(file);
        update(serializable);
    }

    @Override
    public List<ISerializable> read(Query query) {
        if (query == null || query.getEntity() == null) return new ArrayList<>();
        return FileProxyFactory.getProxy(query.getEntity()).deserialize(query);
    }

    @Override
    public void update(ISerializable serializable) {
        // check if record is created
        File file = FileUtils.getFile(serializable.getPath());
        if (!file.exists()) create(serializable);
        // update record
        FileProxyFactory.getProxy(serializable).serialize();
    }

    @Override
    public void delete(ISerializable serializable) {
        File file = FileUtils.getFile(serializable.getPath());
        if (file.exists()) file.delete();
    }

    @Override
    public boolean exists(ISerializable serializable) {
        return false;
    }
}
