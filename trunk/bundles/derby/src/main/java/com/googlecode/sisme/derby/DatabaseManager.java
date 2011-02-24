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
package com.googlecode.sisme.derby;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.osgi.framework.BundleContext;

public class DatabaseManager {
    private final File rootDirectory;
    private final Map<String,Database> databases = new HashMap<String,Database>();

    public DatabaseManager(BundleContext context) {
        rootDirectory = context.getDataFile("db");
        if (rootDirectory.exists()) {
            for (File directory : rootDirectory.listFiles()) {
                databases.put(directory.getName(), new Database(directory, Database.STATE_OFFLINE));
            }
        } else {
            rootDirectory.mkdir();
        }
    }
    
    public synchronized Database getDatabase(String name) throws SQLException {
        Database database = databases.get(name);
        if (database == null) {
            File directory = new File(rootDirectory, name);
            EmbeddedDataSource ds = new EmbeddedDataSource();
            ds.setDatabaseName(directory.getAbsolutePath());
            ds.setUser(Database.ADMIN_USER);
            ds.setCreateDatabase("create");
            // Open a connection to create the database
            ds.getConnection().close();
            database = new Database(directory, Database.STATE_NEW);
            databases.put(name, database);
        }
        return database;
    }
}
