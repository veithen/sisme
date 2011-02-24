/*
 * Copyright 2011 Andreas Veithen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.sisme.derby.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.derby.DataSourceFactory;

public class DatabaseManager {
    private final BundleContext context;
    private final File rootDirectory;
    private final Map<String,DatabaseImpl> databases = new HashMap<String,DatabaseImpl>();

    public DatabaseManager(BundleContext context) {
        this.context = context;
        rootDirectory = context.getDataFile("db");
    }
    
    public void start() {
        if (rootDirectory.exists()) {
            for (File directory : rootDirectory.listFiles()) {
                registerDatabase(new DatabaseImpl(directory.getName(), directory));
            }
        } else {
            rootDirectory.mkdir();
        }
    }
    
    public void stop() {
        for (DatabaseImpl database : databases.values()) {
            database.unregister();
        }
        databases.clear();
    }
    
    public DataSourceFactory acquireDatabase(String name) throws SQLException {
        DatabaseImpl database;
        synchronized (databases) {
            database = databases.get(name);
            if (database == null) {
                File directory = new File(rootDirectory, name);
                database = new DatabaseImpl(name, directory);
                registerDatabase(database);
            }
        }
        return database.acquire();
    }
    
    private void registerDatabase(DatabaseImpl database) {
        databases.put(database.getName(), database);
        database.register(context);
    }
}
